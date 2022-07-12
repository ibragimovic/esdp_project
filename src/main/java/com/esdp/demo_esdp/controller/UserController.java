package com.esdp.demo_esdp.controller;

import com.esdp.demo_esdp.dto.UpdatePasswordDTO;
import com.esdp.demo_esdp.dto.UserRegisterForm;
import com.esdp.demo_esdp.dto.UserUpdateForm;
import com.esdp.demo_esdp.service.ProductService;
import com.esdp.demo_esdp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.security.Principal;

@Validated
@Controller
@RequestMapping("/")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final ProductService productService;

    @GetMapping("/profile")
    public String pageCustomerProfile(Model model, Principal principal) {
        String email;
        if (principal instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) principal;
            email = oAuth2AuthenticationToken.getPrincipal().getAttribute("email");
        } else {
            email = principal.getName();
        }

        var user = userService.getByEmail(email);
        model.addAttribute("dto", user);
        model.addAttribute("userPassword", new UpdatePasswordDTO());
        model.addAttribute("products", productService.getProductsUser(email));
        return "profile";
    }

    @PostMapping("/profile")
    public String updateUserProfile(@Valid UserUpdateForm userRequestDto,
                                    BindingResult validationResult,
                                    RedirectAttributes attributes) {
        attributes.addFlashAttribute("dto", userRequestDto);
        userService.update(userRequestDto);
        return "redirect:/profile";
    }

    @GetMapping("/register")
    public String pageRegisterCustomer(Model model) {
        if (!model.containsAttribute("dto")) {
            model.addAttribute("dto", new UserRegisterForm());
        }

        return "register";
    }

    @PostMapping("/register")
    public String registerPage(@Valid UserRegisterForm userRequestDto,
                               BindingResult validationResult,
                               RedirectAttributes attributes) {
        attributes.addFlashAttribute("dto", userRequestDto);
        attributes.addFlashAttribute("message", "Необходимо активировать пользователя, проверьте почту");
        if (validationResult.hasFieldErrors()) {
            attributes.addFlashAttribute("errors", validationResult.getFieldErrors());
            return "redirect:/register";
        }

        userService.register(userRequestDto);
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("message", "Пользователь успешно активирован");
        } else {
            model.addAttribute("message", "Код активации не найден");
        }

        return "login";
    }


    @PostMapping("update-password")
    public String updatePassword(Principal principal, @Valid UpdatePasswordDTO updatePasswordDTO,
                                 RedirectAttributes attributes) {
        attributes.addFlashAttribute("errorPassword", userService.updateUserPassword(principal.getName(),
                updatePasswordDTO));
        return "redirect:/profile";
    }


    @GetMapping("/password-recovery")
    public String getPasswordRecovery() {
        return "password_recovery";
    }

    @PostMapping("/password-recovery")
    public String getNewPair(@RequestParam String username, Model model) {
        userService.restorePassword(username);
        return "redirect:/";
    }

    @GetMapping("/password-recovery/{email}/{password}")
    public String showFormNewPassword(@PathVariable String email, @PathVariable String password, RedirectAttributes attributes) {
        if (userService.updatePassword(email, password)) {
            attributes.addAttribute("email", email);
            return "password_new";
        }
        return "redirect:/";
    }

    @PostMapping("/new-password")
    public String userNewPassword(@NotBlank String email, @Size(min = 8) String password, @Size(min = 8) String repeatPassword) {
        userService.userNewPassword(email, password, repeatPassword);
        return "redirect:/";
    }


    @ExceptionHandler(BindException.class)
    private String handlerBindEx(BindException exception, RedirectAttributes attributes) {
        attributes.addFlashAttribute("errorsPassword", exception.getFieldErrors());
        return "redirect:/profile";
    }


    @ExceptionHandler(ConstraintViolationException.class)
    private String handlerConstraintViolationException(ConstraintViolationException exception, RedirectAttributes attributes) {
        attributes.addFlashAttribute("errors", exception.getMessage());
        return "redirect:/profile";
    }
}
