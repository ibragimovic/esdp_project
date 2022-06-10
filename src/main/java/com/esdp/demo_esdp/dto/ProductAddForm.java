package com.esdp.demo_esdp.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class ProductAddForm {
    @NotNull
    @Size(min=4, max=35, message = "Length must be >= 4 and <= 24")
    @Pattern(regexp = "^[^\\d\\s]+$", message = "Should contain only letters")
    private String name;

    @NotNull
    @Positive
    private Long categoryId;


    @NotNull
    @Positive
    private Long userId;


    @NotNull
    @Size(min=4, max=128, message = "Length must be >= 4 and <= 24")
    @Pattern(regexp = "^[^\\d\\s]+$", message = "Should contain only letters")
    private String description;

    @NotNull
    @Positive
    private Integer price;
}
