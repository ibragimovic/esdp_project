package com.esdp.demo_esdp.controller;

import com.esdp.demo_esdp.dto.HierarchicalCategoryDTO;
import com.esdp.demo_esdp.service.CategoryService;
import com.esdp.demo_esdp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@AllArgsConstructor
public class CategoryRestController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @GetMapping("/categories")
    public ResponseEntity<List<HierarchicalCategoryDTO>> getHierarchicalCategories() {
        List<HierarchicalCategoryDTO> hierarchicalCategories = categoryService.getHierarchicalCategories();
        return new ResponseEntity<>(hierarchicalCategories, HttpStatus.OK);
    }
}
