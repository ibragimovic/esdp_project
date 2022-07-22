package com.esdp.demo_esdp.controller;

import com.esdp.demo_esdp.dto.FilterProductDto;
import com.esdp.demo_esdp.service.CategoryService;
import com.esdp.demo_esdp.service.LocalitiesService;
import com.esdp.demo_esdp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequiredArgsConstructor
public class MainPageController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final LocalitiesService localitiesService;

    @GetMapping("/main")
    public String getMainPage(Model model) {
        model.addAttribute("filteredCategories",categoryService.getFilterCategories());
        model.addAttribute("products",productService.getMainProductsList());
        model.addAttribute("localities", localitiesService.getFilterLocalities());
        return "main_page";
    }

    @PostMapping("/product/search")
    public String getProductsSearch(Model model, @RequestParam String productSearch) {
        model.addAttribute("products",productService.getMainProductsListByName(productSearch));
        model.addAttribute("goBack",true);
        model.addAttribute("localities", localitiesService.getFilterLocalities());
        model.addAttribute("filteredCategories",categoryService.getFilterCategories());
        return "main_page";
    }

    @PostMapping("/product/filter")
    public String getProductsFilter(Model model, @ModelAttribute("filterProduct") FilterProductDto filters) {
        model.addAttribute("goBack",true);
        model.addAttribute("localities", localitiesService.getFilterLocalities());
        model.addAttribute("filteredCategories",categoryService.getFilterCategories());
        model.addAttribute("products",productService.handleFilter(filters));
        return "main_page";
    }
}
