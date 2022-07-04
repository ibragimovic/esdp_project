package com.esdp.demo_esdp.controller;

import com.esdp.demo_esdp.exception.CategoryNotFoundException;
import com.esdp.demo_esdp.service.CategoryService;
import com.esdp.demo_esdp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping
    public String getMainCategory(Model model, @PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable page) {
        var category = categoryService.getCategory();
        model.addAttribute("categories", category);
        return "index";
    }

    @GetMapping("/category")
    public String getOneCategory(@RequestParam Long id, Model model, @PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable page) throws CategoryNotFoundException {
        var childCategory = categoryService.getSubCategories(id);
        var category = categoryService.getOneCategory(id);
        if (!childCategory.isEmpty()){
            model.addAttribute("child_categories", childCategory);
            model.addAttribute("category",category.getName());
        }
        return "category";
    }



}
