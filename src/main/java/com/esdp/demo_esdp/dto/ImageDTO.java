package com.esdp.demo_esdp.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

public class ImageDTO {
    @NotBlank
    @NotNull
    private List<MultipartFile> images;

    @NotNull
    @Positive
    private Long productId;
}
