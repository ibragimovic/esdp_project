package com.esdp.demo_esdp.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ProductAddForm {
    @NotNull
    @Size(min=4, message = "Length must be >= 4")
//    @Pattern(regexp = "^[^\\d\\s]+$", message = "Should contain only letters")
    private String name;

    @NotNull
    @Positive
    private Long categoryId;

    @NotNull
    @Size(min=4, message = "Length must be >= 4")
//    @Pattern(regexp = "^[^\\d\\s]+$", message = "Should contain only letters")
    private String description;

    @NotNull
    @Positive
    private Integer price;

    @NotBlank
    @NotNull
    private List<MultipartFile> images;

    private String locality;
}
