package com.esdp.demo_esdp.service;

import com.esdp.demo_esdp.dto.CategoryDTO;
import com.esdp.demo_esdp.dto.HierarchicalCategoryDTO;
import com.esdp.demo_esdp.dto.ProductDTO;
import com.esdp.demo_esdp.dto.UserResponseDTO;
import com.esdp.demo_esdp.entity.Category;
import com.esdp.demo_esdp.repositories.CategoryRepository;
import com.esdp.demo_esdp.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Future;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class CategoryService {
    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final ProductRepository productRepository;

    public Page<CategoryDTO> getCategory(Pageable pageable) {
        var parent = categoryRepository.findCategoriesByParentNull(pageable);
        return parent.map(CategoryDTO::from);
    }

    public Page<CategoryDTO> geSecondCategory(Long parentId, Pageable pageable) {
        var category = categoryRepository.findCategoriesByParentId(parentId, pageable);
        return category.map(CategoryDTO::from);
    }

    public Page<ProductDTO> findProductsByCategoryId(Long categoryId, Pageable pageable) {
        var products = productRepository.findProductsByCategoryId(categoryId, pageable);
        return products.map(ProductDTO::from);
    }

    public List<HierarchicalCategoryDTO> getHierarchicalCategories() {
        List<HierarchicalCategoryDTO> hierarchicalDTOList = categoryRepository.findCategoriesByParentNull()
                .stream().map(HierarchicalCategoryDTO::from).collect(Collectors.toList());
        for(HierarchicalCategoryDTO firstLevelCategory: hierarchicalDTOList) {
            List<HierarchicalCategoryDTO> secondLevelHierarchicalDTOList = categoryRepository
                    .findCategoriesByParentId(firstLevelCategory.getId())
                    .stream().map(HierarchicalCategoryDTO::from).collect(Collectors.toList());
            firstLevelCategory.setSubCategories(secondLevelHierarchicalDTOList);
            for(HierarchicalCategoryDTO secondLevelCategory: secondLevelHierarchicalDTOList) {
                List<HierarchicalCategoryDTO> thirdLevelHierarchicalDTOList = categoryRepository
                        .findCategoriesByParentId(secondLevelCategory.getId()).stream()
                        .map(HierarchicalCategoryDTO::from).collect(Collectors.toList());
                secondLevelCategory.setSubCategories(thirdLevelHierarchicalDTOList);
            }
        }
        return hierarchicalDTOList;
    }


    public List<CategoryDTO> getCategories() {
        return categoryRepository.findAll()
                .stream().map(CategoryDTO::from).collect(Collectors.toList());
    }
}
