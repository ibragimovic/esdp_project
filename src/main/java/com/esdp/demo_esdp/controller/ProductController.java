package com.esdp.demo_esdp.controller;

import com.esdp.demo_esdp.dto.FavoritesJson;
import com.esdp.demo_esdp.dto.FilterProductDto;
import com.esdp.demo_esdp.dto.ProductAddForm;
import com.esdp.demo_esdp.dto.ProductIdDto;
import com.esdp.demo_esdp.entity.User;
import com.esdp.demo_esdp.exception.CategoryNotFoundException;
import com.esdp.demo_esdp.exception.ProductNotFoundException;
import com.esdp.demo_esdp.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public String createNewProductGET(Model model,Authentication authentication) {
        String email = userService.getEmailFromAuthentication(authentication);
        User user = userService.getUserByEmail(email);
        if (user.getTelNumber() == null || user.getTelNumber().isEmpty()) {
            return "redirect:/phone";
        }
        model.addAttribute("localities", localitiesService.getLocalitiesDTOs());
        model.addAttribute("select_categories", categoryService.getEndCategory());
        return "create_product";
    }

    @PostMapping("/product/create")
    public String createNewProductPOST(@ModelAttribute("newProductData") ProductAddForm newProduct,
                                       Authentication authentication) throws IOException, ProductNotFoundException {
        String email = userService.getEmailFromAuthentication(authentication);
        User user = userService.getUserByEmail(email);
        productService.addNewProduct(newProduct,user);
        return "successfully_created";
    }

    @PostMapping("/product/delete")
    public String deleteProductById(@RequestParam("productId") Long productId,
                                    Authentication authentication) {
        String email = userService.getEmailFromAuthentication(authentication);
        var uri = productService.deleteProductById(productId, email);
        return "redirect:/" + uri;
    }

    @PostMapping("/profile/product/delete")
    @ResponseBody
    public ResponseEntity<Void>  deleteProfileProductById(@RequestBody ProductIdDto p) throws ProductNotFoundException {
        productService.deleteProductById(p.getProductId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/search")
    public String getProductsSearch(Model model, @RequestParam String productSearch,
                                    Pageable pageable, HttpServletRequest uriBuilder) {
        var products = productService.getMainProductsListByName(productSearch, pageable);
        var uri = uriBuilder.getRequestURI();

        propertiesService.fillPaginationDataModel(products, "products",
                propertiesService.getDefaultPageSize(), model, uri);

        model.addAttribute("goBack",true);
        model.addAttribute("search",true);
        return "index";
    }

    @GetMapping("/search")
    public String redirectSearch(){
        return "redirect:/";
    }

    @PostMapping("/category/{id}")
    public String getProductsFilter(@PathVariable("id") Long categoryId,
                                    Model model, @ModelAttribute("filterProduct") FilterProductDto filters,
                                    Pageable pageable, HttpServletRequest uriBuilder) throws CategoryNotFoundException {
        model.addAttribute("goBack",true);
        model.addAttribute("localities", localitiesService.getFilterLocalities());
        model.addAttribute("filteredCategories",categoryService.getFilterCategories());
        var products = productService.handleFilter(filters, categoryId, pageable);
        var uri = uriBuilder.getRequestURI();
        propertiesService.fillPaginationDataModel(products, "products",
                propertiesService.getDefaultPageSize(), model, uri);
        model.addAttribute("thisCategory",categoryService.getOneCategory(categoryId));
        return "index";
    }

    @GetMapping("/category/{id}")
    public String getCategoryProducts(Model model, @PathVariable("id") Long categoryId,
                                      Pageable pageable, HttpServletRequest uriBuilder) throws CategoryNotFoundException {
        model.addAttribute("goBack",true);
        model.addAttribute("localities", localitiesService.getFilterLocalities());
        model.addAttribute("thisCategory",categoryService.getOneCategory(categoryId));
        var products = productService.getProductsCategory(categoryId, pageable);
        var uri = uriBuilder.getRequestURI();
        propertiesService.fillPaginationDataModel(products, "products",
                propertiesService.getDefaultPageSize(), model, uri);
        return "index";
    }

    @GetMapping("/")
    public String getTopProducts(Model model,@RequestParam(required = false, defaultValue = "") String search,
                                 @PageableDefault Pageable page,
                                 HttpServletRequest httpServletRequest){
        model.addAttribute("filteredCategories",categoryService.getFilterCategories());
        var products = productService.getProducts(page);
        var uri = httpServletRequest.getRequestURI();
        propertiesService.fillPaginationDataModel(products, "products",
                propertiesService.getDefaultPageSize(), model, uri);
        return "index";
    }


    @PostMapping("/up/product")
    @ResponseBody
    public ResponseEntity<Void>  upProduct(@RequestBody ProductIdDto p) throws ProductNotFoundException {
        productService.upProduct(p.getProductId());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/product/{id}")
    public String seeProductDetails(Model model, @PathVariable Long id, Authentication authentication) throws ProductNotFoundException {

        model.addAttribute("product", productService.getProductDetails(id,authentication));
        return "product_details";
    }

}