package com.esdp.demo_esdp.service;

import com.esdp.demo_esdp.dto.*;
import com.esdp.demo_esdp.entity.User;
import com.esdp.demo_esdp.exception.UserAlreadyRegisteredException;
import com.esdp.demo_esdp.exception.UserNotFoundException;
import com.esdp.demo_esdp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final MailSender mailSender;

    public void registerOAuth2User(UserRegisterOAuth2Form form) {
        var user = User.builder()
                .email(form.getEmail())
                .name(form.getName())
                .lastname(form.getLastName())
                .login(form.getLogin())
                .build();
        userRepository.save(user);
    }

    public UserResponseDTO register(UserRegisterForm form) {
        if (userRepository.existsByEmail(form.getEmail())) {
            throw new UserAlreadyRegisteredException();
        }

        var user = User.builder()
                .email(form.getEmail())
                .name(form.getName())
                .lastname(form.getLastName())
                .telNumber(form.getTelNumber())
                .login(form.getLogin())
                .password(encoder.encode(form.getPassword()))
                .activationCode(UUID.randomUUID().toString())
                .build();

        userRepository.save(user);

        String message = String.format(
                "Здравствуйте %s! \n" +
                        "Добро пожаловать на сайт Arenda.kg \n" +
                        "Пожалуйста, для активации перейдите по следующей ссылке: http://localhost:8080/activate/%s",
                user.getName() + " " + user.getLastname(), user.getActivationCode()
        );

        mailSender.send(user.getEmail(), "Activation code", message);

        return UserResponseDTO.from(user);
    }

    public boolean isUserExistByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void update(UserUpdateForm form) {
        if (!userRepository.existsById(form.getId())) {
            throw new UserNotFoundException();
        }
        userRepository.updateUserData(form.getName(), form.getLastName(), form.getEmail(), form.getTelNumber(), form.getLogin(), form.getId());

    }

    public UserResponseDTO getByEmail(String email) {
        User user = getUserEmail(email);
        return UserResponseDTO.from(user);
    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        if (user == null) {
            return false;
        }
        user.setActivationCode(null);
        userRepository.updateUserData(user.getName(), user.getLastname(), user.getEmail(), user.getTelNumber(), user.getLogin(), user.getId());
        return true;
    }

    public List<UserResponseDTO> getUsers() {
        return userRepository.getUsers("Admin")
                .stream().map(UserResponseDTO::from).collect(Collectors.toList());
    }

    public Page<UserResponseDTO> getUsers(Pageable pageable) {
        return userRepository.getUsers("Admin",pageable)
                .map(UserResponseDTO::from);
    }

    public List<UserResponseDTO> getInactiveUsers() {
        var usrs = userRepository.findByActivationCodeNotNull()
                .stream().collect(Collectors.toList());

        for (User u : usrs) {
            u.setActivationCode(UUID.randomUUID().toString());
            userRepository.updateUserData(u.getName(), u.getLastname(), u.getEmail(), u.getTelNumber(), u.getLogin(), u.getId());

            String message = String.format(
                    "Здравствуйте %s! \n" +
                            "Добро пожаловать на сайт Arenda.kg \n" +
                            "Пожалуйста, для активации перейдите по следующей ссылке: http://localhost:8080/activate/%s",
                    u.getName() + " " + u.getLastname(), u.getActivationCode()
            );

            mailSender.send(u.getEmail(), "Activation code", message);

        }
        return usrs.stream().map(UserResponseDTO::from).collect(Collectors.toList());
    }


    public void blockingUser(Long id) {
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.updateEnabledUser(!user.isEnabled(), user.getId());
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }


    private Boolean doPasswordsMatch(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }


    public String updateUserPassword(String email, UpdatePasswordDTO updatePasswordDTO) {
        User user = getUserEmail(email);
        if (!doPasswordsMatch(updatePasswordDTO.getOldPassword(), user.getPassword())) {
            return "Неверно указан старый пароль";
        }
        if (!updatePasswordDTO.getNewPassword().equals(updatePasswordDTO.getRepeatingPassword())){
            return "Пароли не совпадают";
        }
        userRepository.updateUserPassword(encoder.encode(updatePasswordDTO.getNewPassword()), user.getId());
        return null;
    }

    public String getEmailFromAuthentication(Authentication authentication) {
        String email;
        if (authentication instanceof OAuth2AuthenticationToken) {
            var attributes = ((DefaultOAuth2User) authentication.getPrincipal()).getAttributes();
            email = attributes.get("email").toString();
        } else {
            email = authentication.getName();
        }
        return email;
    }

    public boolean isUserEnabled(String email) throws UserNotFoundException {
        User user = getUserEmail(email);
        return user.isEnabled();
    }


    public void restorePassword(String email) throws UserNotFoundException {
        var user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        String message = String.format(
                "Здравствуйте %s! \n" +
                        "Пожалуйста, для смены пароля перейдите по следующей ссылке: http://localhost:8080/password-recovery/%s/%s",
                user.getName() + " " + user.getLastname(), user.getEmail(), generateRandomLink(user)
        );

        mailSender.send(user.getEmail(), "Смена пароля", message);
    }

    private String generateRandomLink(User user) {
        String uuid = UUID.randomUUID().toString();
        userRepository.updateUserPassword(encoder.encode(uuid), user.getId());
        return uuid;
    }

    public Boolean updatePassword(String email, String password) {
        User user = getUserEmail(email);
        if (!doPasswordsMatch(password, user.getPassword())) return false;
        return true;
    }

    public void userNewPassword(String email, String password, String repeatPassword) {
        User user = getUserEmail(email);
        if (password.equals(repeatPassword)) {
            userRepository.updateUserPassword(encoder.encode(password), user.getId());
        }
    }

    public void userSaveTelephone(String email, String telephone) {
        User user = getUserEmail(email);
        userRepository.saveUserTel(telephone, user.getId());
    }

    private User getUserEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }


    public Page<UserResponseDTO> getUserName(String name, Pageable pageable) {
        return userRepository.getUserName(name.toLowerCase(), pageable)
                .map(UserResponseDTO::from);
    }

    public Page<UserResponseDTO> getUsersEmail(String name, Pageable pageable) {
        return userRepository.getUserEmail(name.toLowerCase(), pageable)
                .map(UserResponseDTO::from);
    }

    public Page<UserResponseDTO> getUserLogin(String name, Pageable pageable) {
        return userRepository.getUserLogin(name.toLowerCase(), pageable)
                .map(UserResponseDTO::from);
    }

    public Page<UserResponseDTO> getUserLastName(String name, Pageable pageable) {
        return userRepository.getUserLastName(name.toLowerCase(), pageable)
                .map(UserResponseDTO::from);
    }


    public Page<UserResponseDTO> getUserTel(String name, Pageable pageable) {
        return userRepository.getUserTel(name.toLowerCase(), pageable)
                .map(UserResponseDTO::from);
    }


    public Page<UserResponseDTO> getUserStatus(String status, Pageable pageable) {
        return userRepository.getUserStatus(Boolean.valueOf(status), pageable)
                .map(UserResponseDTO::from);
    }

}
