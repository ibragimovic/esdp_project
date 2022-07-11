package com.esdp.demo_esdp.controller;

import com.esdp.demo_esdp.dto.ProductAddForm;
import com.esdp.demo_esdp.entity.User;
import com.esdp.demo_esdp.service.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final PropertiesService propertiesService;
    private final UserService userService;
    private final LocalitiesService localitiesService;
    private final CategoryService categoryService;

    @GetMapping("/product/create")
    public String createNewProductGET(Model model) {

        model.addAttribute("localities", localitiesService.getLocalitiesDTOs());
        model.addAttribute("select_categories", categoryService.getEndCategory());
        return "create_product";
    }

    @PostMapping("/product/create")
    public String createNewProductPOST(@ModelAttribute("newProductData") ProductAddForm newProduct,
                                       Model model, Authentication authentication) {
        User user = userService.getUserByEmail(userService.getEmailFromAuthentication(authentication));
        productService.addNewProduct(newProduct,user);
        model.addAttribute("lastStep",true);
        return "create_product";
    }

    @PostMapping("/product/delete")
    public String deleteProductById(@RequestParam("productId") Long productId,
                                    Authentication authentication) {
        return "redirect:/" + productService.deleteProductById(productId, userService.getEmailFromAuthentication(authentication));
    }

    @GetMapping("/product/search-category")
    public String getProductCategory(@RequestParam("category") @NotBlank String category,
                                     Model model, Pageable pageable, HttpServletRequest uriBuilder) {
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
        var category = categoryService.getCategory();
        model.addAttribute("categories", category);
        model.addAttribute("top_products",topProducts.getContent());
        model.addAttribute("products",products.getContent());
        return "index";

    }

}