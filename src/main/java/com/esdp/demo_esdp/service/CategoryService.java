package com.esdp.demo_esdp.service;

import com.esdp.demo_esdp.dto.CategoryDTO;
import com.esdp.demo_esdp.repositories.CategoryRepository;
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

    public Page<CategoryDTO> getCategory(Pageable pageable) {
        var parrent = categoryRepository.findCategoriesByParentNull(pageable);
        return parrent.map(CategoryDTO::from);
    }

    public Page<CategoryDTO> geSecondCategory(Long parrentId, Pageable pageable) {
        var category = categoryRepository.findCategoriesByParentId(parrentId, pageable);
        return category.map(CategoryDTO::from);
    }
}
