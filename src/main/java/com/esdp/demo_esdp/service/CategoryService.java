package com.esdp.demo_esdp.service;

import com.esdp.demo_esdp.dto.CategoryDTO;
import com.esdp.demo_esdp.dto.HierarchicalCategoryDTO;
import com.esdp.demo_esdp.dto.ProductDTO;
import com.esdp.demo_esdp.entity.Category;
import com.esdp.demo_esdp.enums.ProductStatus;
import com.esdp.demo_esdp.exception.CategoryNotFoundException;
import com.esdp.demo_esdp.repositories.CategoryRepository;
import com.esdp.demo_esdp.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final ProductRepository productRepository;


    public List<CategoryDTO> getCategory() {
        return categoryRepository.findCategoriesByParentNull()
                .stream()
                .map(CategoryDTO::from)
                .collect(Collectors.toList());
    }

    public Page<CategoryDTO> geSecondCategory(Long parentId, Pageable pageable) {
        var category = categoryRepository.findCategoriesByParentId(parentId, pageable);
        return category.map(CategoryDTO::from);
    }


    public List<CategoryDTO> getSubCategories(Long parentId) {
        var category = categoryRepository.findCategoriesByParentId(parentId);
        return category.stream().map(CategoryDTO::from).collect(Collectors.toList());
    }

    public Page<ProductDTO> findProductsByCategoryId(Long categoryId, Pageable pageable) {
        var products = productRepository.findProductsByCategoryId(categoryId, ProductStatus.ACCEPTED, pageable);
        return products.map(ProductDTO::from);
    }

    public CategoryDTO getOneCategory(Long categoryId) throws CategoryNotFoundException {
        var category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException("Не найдена категория!"));
        return CategoryDTO.from(category);
    }

    public List<CategoryDTO> getEndCategory(){
        List<Category> allCategories=categoryRepository.findAll();
        List<Long> catParentId=categoryRepository.getCatParentId();
        List<Category> endCategories=allCategories.stream().filter(c->!catParentId.contains(c.getId())).collect(Collectors.toList());
        return endCategories.stream().map(c->CategoryDTO.from(c)).collect(Collectors.toList());
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


    public void createCategory(Long categoryId, CategoryDTO dto) {
        var parrent = categoryRepository.findById(categoryId);
        if (parrent.isPresent()) {
            categoryRepository.save(Category.builder()
                    .name(dto.getName())
                    .parent(parrent.get())
                    .build());
        } else {
            categoryRepository.save(Category.builder()
                    .name(dto.getName())
                    .parent(null)
                    .build());
        }
    }

    public void changeNameCategory(String newName, Long categoryId) throws CategoryNotFoundException {
        var category = categoryRepository.findById(categoryId);
        var defaultCategory = categoryRepository.findCategoryByName("Прочее");
        if (category.equals(defaultCategory)) {
            throw new CategoryNotFoundException("Нельзя изменять дефолтную категорию", defaultCategory.get().getName());
        }
        category.ifPresent(value -> value.setName(newName));
    }


    public void deleteCategory(Long categoryId) throws CategoryNotFoundException {
        var category = categoryRepository.findById(categoryId);
        var defaultCategory = categoryRepository.findCategoryByName("Прочее");
        if (!category.equals(defaultCategory)) {
            var product = productRepository.findProductsByCategory(category.get().getId());
            if (category.get().getParent() == null) {
                for (int i = 0; i < product.size(); i++) {
                    product.get(i).setCategory(defaultCategory.get());
                    productRepository.save(product.get(i));
                }
                categoryRepository.deleteCategoriesByParentId(category.get().getId());
                categoryRepository.delete(category.get());
            } else {
                for (int i = 0; i < product.size(); i++) {
                    product.get(i).setCategory(defaultCategory.get());
                    productRepository.save(product.get(i));
                }
                categoryRepository.deleteCategoriesByParentId(category.get().getId());
                categoryRepository.delete(category.get());
            }
        } else {
            throw new CategoryNotFoundException("Нельзя удалять категорию", defaultCategory.get().getName());
        }
    }

    public void changeSubcategory(Long subCategoryId, Long parentId) throws CategoryNotFoundException {
        var subCategory = categoryRepository.findById(subCategoryId);
        var category = categoryRepository.findById(parentId);
        var defaultCategory = categoryRepository.findCategoryByName("Прочее");
        if (!subCategory.equals(defaultCategory.get())) {
            if (category.isPresent()) {
                subCategory.get().setParent(category.get());
            } else {
                subCategory.get().setParent(null);
            }
            categoryRepository.save(subCategory.get());
        } else
            throw new CategoryNotFoundException("Нельзя изменять дефолтную категорию", defaultCategory.get().getName());
    }


    public List<CategoryDTO> getCategories() {
        return categoryRepository.findAll()
                .stream().map(CategoryDTO::from).collect(Collectors.toList());
    }


}
