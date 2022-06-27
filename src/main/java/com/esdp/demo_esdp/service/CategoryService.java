package com.esdp.demo_esdp.service;

import com.esdp.demo_esdp.dto.CategoryDTO;
import com.esdp.demo_esdp.dto.ProductDTO;
import com.esdp.demo_esdp.entity.Category;
import com.esdp.demo_esdp.repositories.CategoryRepository;
import com.esdp.demo_esdp.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<CategoryDTO> getEndCategory(){
        List<Category> allCategories=categoryRepository.findAll();
        List<Long> catParentId=categoryRepository.getCatParentId();
        List<Category> endCategories=allCategories.stream().filter(c->!catParentId.contains(c.getId())).collect(Collectors.toList());
        return endCategories.stream().map(c->CategoryDTO.from(c)).collect(Collectors.toList());
    }
}
