package com.esdp.demo_esdp.controller;

import com.esdp.demo_esdp.dto.CategoryDTO;
import com.esdp.demo_esdp.exception.CategoryNotFoundException;
import com.esdp.demo_esdp.exception.ProductNotFoundException;
import com.esdp.demo_esdp.service.CategoryService;
import com.esdp.demo_esdp.service.ProductService;
import com.esdp.demo_esdp.service.PropertiesService;
import com.esdp.demo_esdp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final PropertiesService propertiesService;


    @GetMapping()
    public String getAdmin(Model model, Pageable pageable, HttpServletRequest uriBuilder) {
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("categories", categoryService.getCategories());
        var uri = uriBuilder.getRequestURI();
        propertiesService.fillPaginationDataModel(productService.getProductsAll(pageable),
                "products", propertiesService.getDefaultPageSize(), model, uri);
        return "admin";
    }


    @GetMapping("/users")
    public String getAdminUsers(Model model, Pageable pageable, HttpServletRequest uriBuilder) {
        var uri = uriBuilder.getRequestURI();
        propertiesService.fillPaginationDataModel(userService.getUsers(pageable), "users", propertiesService.getDefaultPageSize(), model, uri);
        return "admin_users";
    }

    @GetMapping("/resend")
    public String resendActivationCode(Model model) {

        model.addAttribute("users", userService.getInactiveUsers());
        return "users_resend";
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


    @GetMapping("/product/search-name/{name}")
    public String getProductName(@PathVariable String name, Model model, Pageable pageable, HttpServletRequest uriBuilder) {
        var products = productService.getProductNameAll(name, pageable);
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("categories", categoryService.getCategories());
        if (products.isEmpty()) {
            model.addAttribute("empty", "Ничего не найдено!");
            return "admin";
        }
        var uri = uriBuilder.getRequestURI();
        propertiesService.fillPaginationDataModel(products, "products", propertiesService.getDefaultPageSize(), model, uri);
        return "admin";

    }

    @PostMapping("/product/search-name")
    public String postProductName(@RequestParam @NotBlank String name) {
        return "redirect:/admin/product/search-name/" + name;
    }

    @GetMapping("/products/search-user/{userEmail}")
    public String getProductUser(@PathVariable String userEmail, Model model, Pageable pageable, HttpServletRequest uriBuilder) {
        var products = productService.getProductsUser(userEmail, pageable);
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("categories", categoryService.getCategories());
        if (products.isEmpty()) {
            model.addAttribute("empty", "Ничего не найдено!");
            return "admin";
        }
        var uri = uriBuilder.getRequestURI();
        propertiesService.fillPaginationDataModel(products, "products", propertiesService.getDefaultPageSize(), model, uri);
        return "admin";
    }

    @PostMapping("/products/search-user")
    public String postProductUser(@RequestParam @NotBlank String userEmail) {
        return "redirect:/admin/products/search-user/" + userEmail;
    }

    @GetMapping("/products/search-status/{status}")
    public String getProductsStatus(@PathVariable String status, Model model, HttpServletRequest uriBuilder, Pageable pageable) {
        var products = productService.getProductsStatus(status, pageable);
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("categories", categoryService.getCategories());
        if (products.isEmpty()) {
            model.addAttribute("empty", "Ничего не найдено!");
            return "admin";
        }
        var uri = uriBuilder.getRequestURI();
        propertiesService.fillPaginationDataModel(products, "products", propertiesService.getDefaultPageSize(), model, uri);
        return "admin";

    }

    @PostMapping("/products/search-status")
    public String postProductStatus(@RequestParam @NotBlank String status) {
        return "redirect:/admin/products/search-status/" + status;
    }


    @GetMapping("/products/search-category/{categoryId}")
    public String getProductCategory(@PathVariable Long categoryId, Model model, HttpServletRequest uriBuilder, Pageable pageable) {
        var products = productService.getProductsCategory(categoryId, pageable);
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("categories", categoryService.getCategories());
        if (products.isEmpty()) {
            model.addAttribute("empty", "Ничего не найдено!");
            return "admin";
        }
        var uri = uriBuilder.getRequestURI();
        propertiesService.fillPaginationDataModel(products, "products", propertiesService.getDefaultPageSize(), model, uri);
        return "admin";

    }

    @PostMapping("/products/search-category")
    public String postProductCategory(@RequestParam Long categoryId) {
        return "redirect:/admin/products/search-category/" + categoryId;
    }

    @GetMapping("/category")
    public String categoryPage(Model model) {
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
    public String changeParentByCategory(@RequestParam(name = "category_id") Long category_id, @RequestParam(name = "parent_id") Long parent_id) throws CategoryNotFoundException {
        categoryService.changeSubcategory(category_id, parent_id);
        return "redirect:/admin/category";
    }

    @GetMapping("/user/search-name/{name}")
    public String getUserName(@PathVariable String name, Model model, Pageable pageable, HttpServletRequest uriBuilder) {
        var users = userService.getUserName(name, pageable);
        if (users.isEmpty()) {
            model.addAttribute("empty", "Ничего не найдено!");
            return "admin_users";
        }
        var uri = uriBuilder.getRequestURI();
        propertiesService.fillPaginationDataModel(users, "users", propertiesService.getDefaultPageSize(), model, uri);
        return "admin_users";

    }

    @PostMapping("/user/search-name")
    public String postUserName(@RequestParam @NotBlank String name) {
        return "redirect:/admin/user/search-name/" + name;
    }

    @GetMapping("/user/search-email/{email}")
    public String getUserEmail(@PathVariable String email, Model model, Pageable pageable, HttpServletRequest uriBuilder) {
        var users = userService.getUsersEmail(email, pageable);
        if (users.isEmpty()) {
            model.addAttribute("empty", "Ничего не найдено!");
            return "admin_users";
        }
        var uri = uriBuilder.getRequestURI();
        propertiesService.fillPaginationDataModel(users, "users", propertiesService.getDefaultPageSize(), model, uri);
        return "admin_users";

    }

    @PostMapping("/user/search-email")
    public String postUserEmail(@RequestParam @NotBlank String name) {
        return "redirect:/admin/user/search-email/" + name;
    }

    @GetMapping("/user/search-login/{login}")
    public String getUserLogin(@PathVariable String login, Model model, Pageable pageable, HttpServletRequest uriBuilder) {
        var users = userService.getUserLogin(login, pageable);
        if (users.isEmpty()) {
            model.addAttribute("empty", "Ничего не найдено!");
            return "admin_users";
        }
        var uri = uriBuilder.getRequestURI();
        propertiesService.fillPaginationDataModel(users, "users", propertiesService.getDefaultPageSize(), model, uri);
        return "admin_users";

    }

    @PostMapping("/user/search-login")
    public String postUserLogin(@RequestParam @NotBlank String name) {
        return "redirect:/admin/user/search-login/" + name;
    }

    @GetMapping("/user/search-lastname/{lastname}")
    public String getUserLastName(@PathVariable String lastname, Model model, Pageable pageable, HttpServletRequest uriBuilder) {
        var users = userService.getUserLastName(lastname, pageable);
        if (users.isEmpty()) {
            model.addAttribute("empty", "Ничего не найдено!");
            return "admin_users";
        }
        var uri = uriBuilder.getRequestURI();
        propertiesService.fillPaginationDataModel(users, "users", propertiesService.getDefaultPageSize(), model, uri);
        return "admin_users";

    }


    @PostMapping("/user/search-lastname")
    public String postUserLastname(@RequestParam @NotBlank String name) {
        return "redirect:/admin/user/search-lastname/" + name;
    }


    @GetMapping("/user/search-tel/{tel}")
    public String getUserTel(@PathVariable String tel, Model model, Pageable pageable, HttpServletRequest uriBuilder) {
        var users = userService.getUserTel(tel, pageable);
        if (users.isEmpty()) {
            model.addAttribute("empty", "Ничего не найдено!");
            return "admin_users";
        }
        var uri = uriBuilder.getRequestURI();
        propertiesService.fillPaginationDataModel(users, "users", propertiesService.getDefaultPageSize(), model, uri);
        return "admin_users";

    }


    @PostMapping("/user/search-tel")
    public String postUserTel(@RequestParam @NotBlank String name) {
        return "redirect:/admin/user/search-tel/" + name;
    }


    @GetMapping("/user/search/{status}")
    public String getUserStatus(@PathVariable String status, Model model, Pageable pageable, HttpServletRequest uriBuilder) {
        var users = userService.getUserStatus(status, pageable);
        if (users.isEmpty()) {
            model.addAttribute("empty", "Ничего не найдено!");
            return "admin_users";
        }
        var uri = uriBuilder.getRequestURI();
        propertiesService.fillPaginationDataModel(users, "users", propertiesService.getDefaultPageSize(), model, uri);
        return "admin_users";

    }


    @PostMapping("/user/search-status")
    public String postUserStatus(@RequestParam @NotBlank String status) {
        return "redirect:/admin/user/search/" + status;
    }
}
