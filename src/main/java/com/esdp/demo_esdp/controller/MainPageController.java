package com.esdp.demo_esdp.controller;

import com.esdp.demo_esdp.dto.FilterCategoryDto;
import com.esdp.demo_esdp.dto.FilterProductDto;
import com.esdp.demo_esdp.dto.SimilarProductDto;
import com.esdp.demo_esdp.service.CategoryService;
import com.esdp.demo_esdp.service.LocalitiesService;
import com.esdp.demo_esdp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


@Controller
@RequiredArgsConstructor
public class MainPageController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final LocalitiesService localitiesService;

    @GetMapping("/main")
    public String getMainPage(Model model) {
        model.addAttribute("filteredCategories",categoryService.getFilterCategories());
        model.addAttribute("products",productService.getMainPageProducts(PageRequest.of(0,3)));
        model.addAttribute("localities", localitiesService.getLocalitiesDTOs());
        return "main_page";
    }

    @PostMapping("/product/search")
    public String getProductsSearch(Model model, @RequestParam String productSearch) {
        model.addAttribute("products",productService.getProductNameOrdered(productSearch,PageRequest.of(0,3)));
        model.addAttribute("goBack",true);
        return "main_page";
    }

    @PostMapping("/product/filter")
    public String getProductsFilter(Model model, @ModelAttribute("filterProduct") FilterProductDto filters) {
        FilterProductDto f=filters;
        return "main_page";
    }
}
