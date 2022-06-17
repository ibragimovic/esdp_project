package com.esdp.demo_esdp.service;

import com.esdp.demo_esdp.dto.CategoryDTO;
import com.esdp.demo_esdp.dto.ProductDTO;
import com.esdp.demo_esdp.repositories.CategoryRepository;
import com.esdp.demo_esdp.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
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
}
