package com.esdp.demo_esdp.dto;

import com.esdp.demo_esdp.entity.Favorites;
import com.esdp.demo_esdp.entity.Product;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class FavoritesDTO {
    private Long id;
    private SimilarProductDto product;

    public static FavoritesDTO from(Favorites favorites,List<String> image) {
        return builder()
                .id(favorites.getId())
                .product(SimilarProductDto.from(favorites.getProduct(),image))
                .build();
    }
}
