package com.esdp.demo_esdp.dto;

import com.esdp.demo_esdp.entity.Category;
import com.esdp.demo_esdp.repositories.CategoryRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class HierarchicalCategoryDTO {
    private Long id;
    private String name;
    private List<HierarchicalCategoryDTO> subCategories;

    public static HierarchicalCategoryDTO from(Category category) {
        return builder()
                .id(category.getId())
                .name(category.getName())
                .subCategories(null)
                .build();
    }
}