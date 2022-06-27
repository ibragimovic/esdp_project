package com.esdp.demo_esdp.dto;

import com.esdp.demo_esdp.entity.Category;
import lombok.*;

@Data
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class CategoryDTO {

    private Long id;
    private String name;
    private Category parent;

    public static CategoryDTO from(Category category){
        return builder()
                .id(category.getId())
                .name(category.getName())
                .parent(category.getParent())
                .build();
    }
}

