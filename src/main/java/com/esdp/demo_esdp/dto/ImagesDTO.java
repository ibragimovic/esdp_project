package com.esdp.demo_esdp.dto;

import com.esdp.demo_esdp.entity.Images;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImagesDTO {
    public static ImagesDTO from(Images img){
        return ImagesDTO.builder()
                .id(img.getId())
                .path(img.getPath())
                .product(ProductDTO.from(img.getProduct()))
                .build();
    }

    private Long id;

    private String path;

    private ProductDTO product;
}
