package com.esdp.demo_esdp.controller;

import com.esdp.demo_esdp.dto.FilterProductDto;
import com.esdp.demo_esdp.dto.ProductAddForm;
import com.esdp.demo_esdp.entity.User;
import com.esdp.demo_esdp.exception.ProductNotFoundException;
import com.esdp.demo_esdp.service.CategoryService;
import com.esdp.demo_esdp.service.ProductService;
import com.esdp.demo_esdp.service.PropertiesService;
import com.esdp.demo_esdp.service.UserService;
import com.esdp.demo_esdp.service.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final PropertiesService propertiesService;
    private final UserService userService;
    private final LocalitiesService localitiesService;
    private final CategoryService categoryService;


    @GetMapping("/product/create")
    public String createNewProductGET(Model model,Authentication authentication) throws ProductNotFoundException {
        User user = userService.getUserByEmail(userService.getEmailFromAuthentication(authentication));
        if (user.getTelNumber() == null || user.getTelNumber().isEmpty()) {
            return "redirect:/phone";
        }

        model.addAttribute("localities", localitiesService.getLocalitiesDTOs());
        model.addAttribute("select_categories", categoryService.getEndCategory());
        return "create_product";
    }

    @PostMapping("/product/create")
    public String createNewProductPOST(@ModelAttribute("newProductData") ProductAddForm newProduct,
                                       Model model, Authentication authentication) throws IOException, ProductNotFoundException {
        User user = userService.getUserByEmail(userService.getEmailFromAuthentication(authentication));
        productService.addNewProduct(newProduct,user);
        return "successfully_created";
    }

    @PostMapping("/product/delete")
    public String deleteProductById(@RequestParam("productId") Long productId,
                                    Authentication authentication) {
        return "redirect:/" + productService.deleteProductById(productId, userService.getEmailFromAuthentication(authentication));
    }

    @PostMapping("/product/search")
    public String getProductsSearch(Model model, @RequestParam String productSearch) {
        model.addAttribute("products",productService.getMainProductsListByName(productSearch));
        model.addAttribute("goBack",true);
        model.addAttribute("localities", localitiesService.getFilterLocalities());
        model.addAttribute("filteredCategories",categoryService.getFilterCategories());
        return "index";
    }

    @PostMapping("/product/filter")
    public String getProductsFilter(Model model, @ModelAttribute("filterProduct") FilterProductDto filters) {
        model.addAttribute("goBack",true);
        model.addAttribute("localities", localitiesService.getFilterLocalities());
        model.addAttribute("filteredCategories",categoryService.getFilterCategories());
        model.addAttribute("products",productService.handleFilter(filters));
        return "index";
    }

    @GetMapping("/")
    public String getTopProducts(Model model,@PageableDefault(sort = "endOfPayment", direction = Sort.Direction.DESC)Pageable page){
        model.addAttribute("filteredCategories",categoryService.getFilterCategories());
        model.addAttribute("products",productService.getMainProductsList());
        model.addAttribute("localities", localitiesService.getFilterLocalities());
        model.addAttribute("categories", categoryService.getCategory());
        return "index";

    }

    @PostMapping("/up/product")
    public String upProduct(@RequestParam(name = "id") Long productId) throws ProductNotFoundException {
        productService.upProduct(productId);
        return "redirect:/";
    }

    @GetMapping("/product/{id}")
    public String seeProductDetails(Model model, @PathVariable Long id, Authentication authentication) throws ProductNotFoundException {

        model.addAttribute("product", productService.getProductDetails(id,authentication));
        return "product_details";
    }

}