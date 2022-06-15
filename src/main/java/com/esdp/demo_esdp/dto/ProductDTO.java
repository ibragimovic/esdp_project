package com.esdp.demo_esdp.dto;

import com.esdp.demo_esdp.entity.Category;
import com.esdp.demo_esdp.entity.Product;
import com.esdp.demo_esdp.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    private Category category;
    private User user;
    private String description;
    private Integer price;
    private String status;
    private LocalDateTime dateAdd;
    private LocalDateTime endOfPayment;
    private String localities;

    public static ProductDTO from(Product product) {
        return builder()
                .id(product.getId())
                .name(product.getName())
                .category(product.getCategory())
                .user(product.getUser())
                .description(product.getDescription())
                .price(product.getPrice())
                .status(String.valueOf(product.getStatus()))
                .dateAdd(product.getDateAdd())
                .endOfPayment(product.getEndOfPayment())
                .localities(product.getLocalities())
                .build();
    }
}
