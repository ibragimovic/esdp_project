package com.esdp.demo_esdp.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.List;

@Data
@Builder
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


    @NotBlank
    @NotNull
    private List<MultipartFile> images;
}
