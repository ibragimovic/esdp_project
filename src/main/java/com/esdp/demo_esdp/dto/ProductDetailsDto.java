package com.esdp.demo_esdp.dto;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class ProductDetailsDto {
    private Long id;
    private String name;
    private String category;
    private String fullName;
    private String phoneNumber;
    private String description;
    private Integer price;
    private String localities;
    private List<String> imagePaths;
    private List<SimilarProductDto> similarProducts;
    private Long amountOfLikes;
}
