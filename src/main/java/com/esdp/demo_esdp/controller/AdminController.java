package com.esdp.demo_esdp.controller;

import com.esdp.demo_esdp.repositories.UserRepository;
import com.esdp.demo_esdp.service.ProductService;
import com.esdp.demo_esdp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final ProductService productService;
    private final UserRepository userRepository;


    @GetMapping()
    public String getAdmin(Model model) {
        return "admin";
    }


    @GetMapping("/users")
    public String getAdminUsers(Model model){
        model.addAttribute("users",userRepository.findAll());
        return "admin_users";
    }


    @PostMapping("/user-delete")
    public String deleteUserId(Long id){
        userRepository.deleteById(id);
        return "redirect:/admin/users";
    }
}
