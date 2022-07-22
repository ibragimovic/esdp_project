package com.esdp.demo_esdp.dto;

import com.esdp.demo_esdp.entity.Product;
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

    public static SimilarProductDto from(Product product,List<String> image){
        return SimilarProductDto.builder()
                .id(product.getId())
                .category(product.getCategory().getName())
                .name(product.getName())
                .price(product.getPrice())
                .imagePaths(image)
                .build();
    }
}
