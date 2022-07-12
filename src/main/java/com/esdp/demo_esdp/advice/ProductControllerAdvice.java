package com.esdp.demo_esdp.advice;

import com.esdp.demo_esdp.controller.AdminController;
import com.esdp.demo_esdp.controller.ProductController;
import com.esdp.demo_esdp.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackageClasses = {
        ProductController.class,
        AdminController.class
})
public class ProductControllerAdvice {

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String productNotFound(ProductNotFoundException ex, Model model) {
        model.addAttribute("resource", ex.getResource());
        return "not_found";
    }
}
