package com.esdp.demo_esdp.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SimilarProductDto {
    private Long id;
    private String category;
    private String name;
    private Integer price;
    private List<String> imagePaths;
}
