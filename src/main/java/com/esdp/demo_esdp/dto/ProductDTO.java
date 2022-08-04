package com.esdp.demo_esdp.dto;

import com.esdp.demo_esdp.entity.Product;
import com.esdp.demo_esdp.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    private String category;
    private User user;
    private String description;
    private Integer price;
    private String status;
    private LocalDateTime dateAdd;
    private LocalDateTime endOfPayment;
    private LocalDateTime up_to_top;
    private String localities;
    private List<String> imagePaths;

    public static ProductDTO from(Product product) {
        return builder()
                .id(product.getId())
                .name(product.getName())
                .category(product.getCategory().getName())
                .user(product.getUser())
                .description(product.getDescription())
                .price(product.getPrice())
                .status(String.valueOf(product.getStatus()))
                .dateAdd(product.getDateAdd())
                .endOfPayment(product.getEndOfPayment())
                .localities(product.getLocalities())
                .up_to_top(product.getUp())
                .build();
    }

    public static ProductDTO fromImage(Product product, List<String> image) {
        return builder()
                .id(product.getId())
                .name(product.getName())
                .category(product.getCategory().getName())
                .user(product.getUser())
                .description(product.getDescription())
                .price(product.getPrice())
                .status(String.valueOf(product.getStatus()))
                .dateAdd(product.getDateAdd())
                .endOfPayment(product.getEndOfPayment())
                .localities(product.getLocalities())
                .up_to_top(product.getUp())
                .imagePaths(image)
                .build();
    }
}
