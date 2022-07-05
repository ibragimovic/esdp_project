package com.esdp.demo_esdp.controller;

import com.esdp.demo_esdp.dto.CategoryDTO;
import com.esdp.demo_esdp.exception.CategoryNotFoundException;
import com.esdp.demo_esdp.exception.ProductNotFoundException;
import com.esdp.demo_esdp.service.CategoryService;
import com.esdp.demo_esdp.service.ProductService;
import com.esdp.demo_esdp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;


    @GetMapping()
    public String getAdmin(Model model) {
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("products", productService.getProductsAll());
        return "admin";
    }


    @GetMapping("/users")
    public String getAdminUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "admin_users";
    }


    @PostMapping("/user-blocking")
    public String deleteUserId(Long id) {
        userService.blockingUser(id);
        return "redirect:/admin/users";
    }


    @PostMapping("/product-update-status")
    public String updateStatusProduct(String status, Long id) {
        productService.updateProductStatusId(status, id);
        return "redirect:/admin";
    }

    @PostMapping("/product_add_top")
    public String addProductToTop(@RequestParam Long id, @RequestParam Integer hour) throws ProductNotFoundException {
        productService.addProductToTop(id, hour);
        return "redirect:/admin";

    }


    @GetMapping("/product/search-name")
    public String getProductName(@RequestParam @NotBlank String name, Model model, Pageable pageable) {
        var products = productService.getProductName(name, pageable);
        if (products.isEmpty()) {
            return "error404";
        } else {
            model.addAttribute("users", userService.getUsers());
            model.addAttribute("categories", categoryService.getCategories());
            model.addAttribute("products", products);
            return "admin";
        }
    }


    @GetMapping("/products/search-user")
    public String getProductUser(String userEmail, Model model, Pageable pageable) {
        var products = productService.getProductsUser(userEmail);
        if (products.isEmpty()) {
            return "error404";
        } else {
            model.addAttribute("users", userService.getUsers());
            model.addAttribute("categories", categoryService.getCategories());
            model.addAttribute("products", products);
            return "admin";
        }
    }


    @GetMapping("/products/search-status")
    public String getProductsStatus(String status, Model model) {
        var products = productService.getProductsStatus(status);
        if (products.isEmpty()) {
            return "error404";
        } else {
            model.addAttribute("users", userService.getUsers());
            model.addAttribute("categories", categoryService.getCategories());
            model.addAttribute("products", products);
            return "admin";
        }
    }


    @GetMapping("/products/search-category")
    public String getProductUser(Long categoryId, Model model) {
        var products = productService.getProductsCategory(categoryId);
        if (products.isEmpty()) {
            return "error404";
        } else {
            model.addAttribute("users", userService.getUsers());
            model.addAttribute("categories", categoryService.getCategories());
            model.addAttribute("products", products);
            return "admin";
        }
    }




    @GetMapping("/category")
    public String changeCategoryNamePage(Model model) {
        var categories = categoryService.getHierarchicalCategories();
        model.addAttribute("categoriesList", categories);
        return "admin_category";
    }

    @PostMapping("/create/category")
    public String createCategory(@RequestParam(name = "parent_id") Long categoryId, @Valid CategoryDTO dto) {
        categoryService.createCategory(categoryId, dto);
        return "redirect:/admin/category";
    }

    @PostMapping("/change/category/name")
    public String changeNameCategory(@RequestParam(name = "id") Long categoryId, @Param("newName") String newName) throws CategoryNotFoundException {
        categoryService.changeNameCategory(newName, categoryId);
        return "redirect:/admin/category";
    }


    @PostMapping("/delete/category")
    public String deleteOneCategory(@RequestParam(name = "id") Long categoryId) throws CategoryNotFoundException {
        categoryService.deleteCategory(categoryId);
        return "redirect:/admin/category";
    }

    @PostMapping("/change/parent/category")
    public String changeParentByCategory(@RequestParam(name = "category_id") Long category_id,@RequestParam(name = "parent_id") Long parent_id) throws CategoryNotFoundException {
        categoryService.changeSubcategory(category_id,parent_id);
        return "redirect:/admin/category";
    }

}
