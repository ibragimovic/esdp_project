package com.esdp.demo_esdp.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
@Getter
@Setter
public class CategoryNotFoundException extends Throwable {
    private String resource;
    private String name;

    public CategoryNotFoundException(String resource, String name) {
        this.resource = resource;
        this.name = name;
    }

    public CategoryNotFoundException(String resource) {
        this.resource = resource;
    }
}