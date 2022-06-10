package com.esdp.demo_esdp.controller;

import com.esdp.demo_esdp.service.CategoryService;
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

    @GetMapping
    public String getMainCategory(Model model, @PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable page) {
        var category = categoryService.getCategory(page);
        model.addAttribute("categories", category.getContent());
        return "index";
    }

    @GetMapping("/category")
    public String getOneCategory(@RequestParam Long id, Model model, @PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable page) {
        var childCategory = categoryService.geSecondCategory(id, page);
        if (!childCategory.isEmpty()){
            model.addAttribute("child_categories", childCategory.getContent());}
        return "category";
    }
}