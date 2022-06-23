package com.esdp.demo_esdp.controller;

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


    @GetMapping()
    public String getAdmin(Model model) {
        model.addAttribute("products",productService.getProductsAll());
        return "admin";
    }


    @GetMapping("/users")
    public String getAdminUsers(Model model){
        model.addAttribute("users",userService.getUsers());
        return "admin_users";
    }


    @PostMapping("/user-delete")
    public String deleteUserId(Long id){
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }


    @PostMapping("/product-delete")
    public String deleteProductId(Long id){
        productService.deleteProductId(id);
        return "redirect:/admin";
    }


    @PostMapping("/product-update-status")
    public String updateStatusProduct(String status ,Long id){
        productService.updateProductStatusId(status, id);
        return "redirect:/admin";
    }
}
