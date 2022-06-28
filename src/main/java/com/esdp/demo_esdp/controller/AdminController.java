package com.esdp.demo_esdp.controller;

import com.esdp.demo_esdp.service.ProductService;
import com.esdp.demo_esdp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final ProductService productService;


    @GetMapping()
    public String getAdmin(Model model) {
        model.addAttribute("products", productService.getProductsAll());
        return "admin";
    }


    @GetMapping("/users")
    public String getAdminUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "admin_users";
    }


    @PostMapping("/user-blocking")
    public String deleteUserId(Long id) {
        userService.blockingUser(id);
        return "redirect:/admin/users";
    }


    @PostMapping("/product-update-status")
    public String updateStatusProduct(String status, Long id) {
        productService.updateProductStatusId(status, id);
        return "redirect:/admin";
    }

    @PostMapping("/product_add_top")
    public String addProductToTop(@RequestParam Long id,@RequestParam Integer hour) throws ProductNotFoundException {
        productService.addProductToTop(id,hour);
        return "redirect:/admin";

    }


    @GetMapping("/product/search-name")
    public String getProductName(@RequestParam @NotBlank String name, Model model, Pageable pageable) {
        var products = productService.getProductName(name, pageable);
        if (products.isEmpty()) {
            return "error404";
        } else {
            model.addAttribute("products", products);
            return "admin";
        }
    }


    @GetMapping("/products/search-user")
    public String getProductUser(@RequestParam @NotBlank String name, Model model, Pageable pageable) {
        var products = productService.getProductsUser(name);
        if (products.isEmpty()) {
            return "error404";
        } else {
            model.addAttribute("products", products);
            return "admin";
        }
    }


    @GetMapping("/products/search-status")
    public String getProductsStatus(@RequestParam @NotBlank String status, Model model) {
        var products = productService.getProductsStatus(status);
        if (products.isEmpty()) {
            return "error404";
        } else {
            model.addAttribute("products", products);
            return "admin";
        }
    }

}
