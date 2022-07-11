package com.esdp.demo_esdp.advice;

import com.esdp.demo_esdp.controller.CategoryController;
import com.esdp.demo_esdp.exception.CategoryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackageClasses = {
        CategoryController.class
})
public class CategoryControllerAdvice {

    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private String categoryNotFound(CategoryNotFoundException ex, Model model) {
        model.addAttribute("resource", ex.getResource());
        return "not_found";
    }
}