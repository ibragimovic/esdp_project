package com.esdp.demo_esdp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FavoritesJson {
    private Long productId;
    private String userEmail;
    private String liked;
}
