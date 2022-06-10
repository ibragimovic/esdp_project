package com.esdp.demo_esdp.controller;

import com.esdp.demo_esdp.entity.Product;
import com.esdp.demo_esdp.entity.User;
import com.esdp.demo_esdp.service.PropertiesService;
import com.esdp.demo_esdp.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final PropertiesService propertiesService;

    @PostMapping("/product/delete")
    public String deleteProductById(@RequestParam("productId") Long productId,
                                    Authentication authentication,
                                    HttpSession httpSession) {
        User user = (User) authentication.getPrincipal();
        propertiesService.deleteProductById(productId, user, httpSession);
        return "redirect:/product";
    }

    @GetMapping("/product/search")
    public String getProductCategory(@RequestParam("category") @NotBlank String category,
                                     Model model,
                                     Pageable pageable, HttpServletRequest uriBuilder) {
        var products = productService.getProductType(category, pageable);
        if (products.isEmpty()) {
            return "error404";
        } else {
            var uri = uriBuilder.getRequestURI();
            propertiesService.fillPaginationDataModel(products, propertiesService.getDefaultPageSize(), model, uri);
            return "index";
        }
    }

    @GetMapping("/product/search")
    public String getProductName(@RequestParam("name") @NotBlank String name, Model model,
                                 Pageable pageable, HttpServletRequest uriBuilder) {
        var products = productService.getProductName(name, pageable);
        if (products.isEmpty()) {
            return "error404";
        } else {
            var uri = uriBuilder.getRequestURI();
            propertiesService.fillPaginationDataModel(products, propertiesService.getDefaultPageSize(), model, uri);
            return "index";
        }
    }

    @GetMapping("/product/search")
    public String getProductPrice(@RequestParam("from") @NonNull @Min(1) Integer from,
                                  @RequestParam("before") @NonNull @Min(1) Integer before,
                                  Model model,
                                  Pageable pageable, HttpServletRequest uriBuilder) {
        var products = productService.getProductPrice(from, before, pageable);
        if (products.isEmpty()) {
            return "error404";
        } else {
            var uri = uriBuilder.getRequestURI();
            propertiesService.fillPaginationDataModel(products, propertiesService.getDefaultPageSize(), model, uri);
            return "index";
        }
    }

}