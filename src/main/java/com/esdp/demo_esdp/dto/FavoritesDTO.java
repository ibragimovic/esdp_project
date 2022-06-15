package com.esdp.demo_esdp.dto;

import com.esdp.demo_esdp.entity.Favorites;
import com.esdp.demo_esdp.entity.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FavoritesDTO {
    private Long id;
    private Product product;

    public static FavoritesDTO from(Favorites favorites) {
        return builder()
                .id(favorites.getId())
                .product(favorites.getProduct())
                .build();
    }
}
