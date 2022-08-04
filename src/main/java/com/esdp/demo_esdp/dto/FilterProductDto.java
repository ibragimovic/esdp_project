package com.esdp.demo_esdp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterProductDto {
    private String locality;
    private Long categoryId;
    private String search;
    private Integer priceFrom;
    private Integer priceTo;
    private String sortProduct;
}
