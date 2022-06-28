package com.esdp.demo_esdp.controller;

import com.esdp.demo_esdp.dto.ImageDTO;
import com.esdp.demo_esdp.dto.ProductAddForm;
import com.esdp.demo_esdp.entity.User;
import com.esdp.demo_esdp.service.ProductService;
import com.esdp.demo_esdp.service.PropertiesService;
import com.esdp.demo_esdp.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final PropertiesService propertiesService;
    private final UserService userService;

    @GetMapping("/product/add")
    public String getAddPage(Model model) {
        return "add-product";
    }

    @PostMapping("/product/add")
    public String addNewProduct(@Valid ProductAddForm productAddForm,
                                @Valid ImageDTO imageDTO,
                                BindingResult validationResult,
                                Authentication authentication,
                                RedirectAttributes attributes) {

        if (validationResult.hasFieldErrors()) {
            attributes.addFlashAttribute("errors", validationResult.getFieldErrors());
            return "redirect:/product/add";
        }
        User user = (User) authentication.getPrincipal();
        productService.addNewProduct(productAddForm, user);
        return "redirect:/profile";
    }

    @PostMapping("/product/delete")
    public String deleteProductById(@RequestParam("productId") Long productId,
                                    Principal principal) {
        return "redirect:/" + productService.deleteProductById(productId, principal.getName());
    }

    @GetMapping("/product/search-category")
    public String getProductCategory(@RequestParam("category") @NotBlank String category,
                                     Model model,
                                     Pageable pageable, HttpServletRequest uriBuilder) {
        var products = productService.getProductCategory(category, pageable);
        if (products.isEmpty()) {
            return "error404";
        } else {
            var uri = uriBuilder.getRequestURI();
            propertiesService.fillPaginationDataModel(products, propertiesService.getDefaultPageSize(), model, uri);
            return "index";
        }
    }

    @GetMapping("/product/search-name")
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

    @GetMapping("/product/search-price")
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


    @GetMapping("/")
    public String getTopProducts(Model model,@PageableDefault(sort = "endOfPayment", direction = Sort.Direction.DESC)Pageable page){
        var topProducts = productService.getTopProduct(page);
        var products = productService.getProductsToMainPage(page);
        model.addAttribute("top_products",topProducts.getContent());
        model.addAttribute("products",products.getContent());
        return "index";

    }

}