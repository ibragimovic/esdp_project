package com.esdp.demo_esdp.controller;

import com.esdp.demo_esdp.dto.FilterProductDto;
import com.esdp.demo_esdp.dto.ProductAddForm;
import com.esdp.demo_esdp.entity.User;
import com.esdp.demo_esdp.exception.CategoryNotFoundException;
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

    //main page start

    @PostMapping("/search")
    public String getProductsSearch(Model model, @RequestParam String productSearch) {
        model.addAttribute("products",productService.getMainProductsListByName(productSearch));
        model.addAttribute("goBack",true);
        model.addAttribute("search",true);
        return "index";
    }

    @GetMapping("/search")
    public String redirectSearch(){
        return "redirect:/";
    }

    @PostMapping("/category/{id}")
    public String getProductsFilter(Model model, @ModelAttribute("filterProduct") FilterProductDto filters) throws CategoryNotFoundException {
        model.addAttribute("goBack",true);
        model.addAttribute("localities", localitiesService.getFilterLocalities());
        model.addAttribute("filteredCategories",categoryService.getFilterCategories());
        model.addAttribute("products",productService.handleFilter(filters));
        model.addAttribute("thisCategory",categoryService.getOneCategory(filters.getCategoryId()));
        return "index";
    }

    @GetMapping("/")
    public String getTopProducts(Model model,@RequestParam(required = false, defaultValue = "") String search,
                                 @PageableDefault(sort = "endOfPayment", direction = Sort.Direction.DESC)Pageable page){
        model.addAttribute("filteredCategories",categoryService.getFilterCategories());
        model.addAttribute("products",productService.getMainProductsListByName(search));
        return "index";

    }

    @GetMapping("/category/{id}")
    public String getCategoryProducts(Model model, @PathVariable("id") Long catId) throws CategoryNotFoundException {
        FilterProductDto f=new FilterProductDto();
        f.setCategoryId(catId);
        model.addAttribute("goBack",true);
        model.addAttribute("products",productService.handleFilter(f));
        model.addAttribute("localities", localitiesService.getFilterLocalities());
        model.addAttribute("thisCategory",categoryService.getOneCategory(catId));
        return "index";

    }

    //main page finish

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