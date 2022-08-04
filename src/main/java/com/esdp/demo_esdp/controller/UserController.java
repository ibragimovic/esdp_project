package com.esdp.demo_esdp.controller;

import com.esdp.demo_esdp.dto.UpdatePasswordDTO;
import com.esdp.demo_esdp.dto.UserRegisterForm;
import com.esdp.demo_esdp.dto.UserUpdateForm;
import com.esdp.demo_esdp.service.ProductService;
import com.esdp.demo_esdp.service.PropertiesService;
import com.esdp.demo_esdp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.security.Principal;

@Validated
@Controller
@RequestMapping("/")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final ProductService productService;
    private final PropertiesService propertiesService;


    @GetMapping("/phone")
    public String saveUserPhoneNumber() {
        return "phone";
    }

    @PostMapping("/phone")
    public String saveUserPhoneNumber(@Size(min = 13, max = 13, message = "Length must be = 13, format +996552902002")
                                      @NotBlank @Pattern(regexp = "^(\\+)[0-9]+$", message = "Should contain only numbers")
                                      @RequestParam String phone, Authentication authentication) {
        String email = userService.getEmailFromAuthentication(authentication);
        userService.userSaveTelephone(email, phone);
        return "redirect:/product/create";
    }

    @GetMapping("/profile")
    public String pageCustomerProfile(Model model, Authentication authentication,
                                      Pageable pageable, HttpServletRequest uriBuilder) {
        String email = userService.getEmailFromAuthentication(authentication);
        var user = userService.getByEmail(email);
        model.addAttribute("dto", user);
        model.addAttribute("userPassword", new UpdatePasswordDTO());
        var uri = uriBuilder.getRequestURI();
        var products = productService.getProductsUser(email, pageable);
        propertiesService.fillPaginationDataModel(products, "products",
                propertiesService.getDefaultPageSize(), model, uri);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateUserProfile(@Valid UserUpdateForm userRequestDto,
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
    public String getNewPair(@RequestParam String username) {
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
