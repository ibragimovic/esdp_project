package com.esdp.demo_esdp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterCategoryDto {
    private Long id;
    private String name;
    private boolean hasChildren;
    private Long parent;
}
