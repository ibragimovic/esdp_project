package com.esdp.demo_esdp.service;

import com.esdp.demo_esdp.dto.CategoryDTO;
import com.esdp.demo_esdp.entity.Category;
import com.esdp.demo_esdp.entity.Product;
import com.esdp.demo_esdp.exception.CategoryNotFoundException;
import com.esdp.demo_esdp.repositories.CategoryRepository;
import com.esdp.demo_esdp.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    private final long ID = 4L;
    private final long defaulthId = 6L;
    private final long subCategoryID = 35L;
    @Autowired
    CategoryService categoryService;

    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    ProductRepository productRepository;

    @Test
    void getCategory() {
        List<CategoryDTO> mainCategories = categoryService.getCategory();
        for (CategoryDTO mainCategory : mainCategories) {
            assertNull(mainCategory.getParent());
            assertNotNull(mainCategory);
        }
        verify(categoryRepository).findCategoriesByParentNull();
    }

    @Test
    void getSubCategories() {
        List<CategoryDTO> categories = categoryService.getSubCategories(ID);
        assertNotNull(categories);
        for (int i = 0; i < categories.size(); i++) {
            assertEquals(categories.get(i).getParent().getId(), ID);
        }
        verify(categoryRepository).findCategoriesByParentId(ID);
    }

    @Test
    void getEndCategory() {
        List<CategoryDTO> categoryDTO = categoryService.getEndCategory();
        for (int i = 0; i < categoryDTO.size(); i++) {
            assertNotNull(categoryDTO);
        }
        verify(categoryRepository).findAll();
    }

    @Test
    void getOneCategory() {
        final Category test = mock(Category.class);
        when(test.getId()).thenReturn(ID);
        CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class, () -> categoryService.getOneCategory(test.getId()));
        assertEquals("Не найдена категория!", exception.getResource());
        verify(categoryRepository).findById(ID);
    }

    @Test
    void createCategory() {
        Category parent = mock(Category.class);
        when(parent.getId()).thenReturn(ID);
        CategoryDTO category = new CategoryDTO();
        var isCreated = categoryService.createCategory(parent.getId(), category);
        assertNotNull(category);
        assertTrue(isCreated);
        verify(categoryRepository, times(1)).findById(ID);
    }

    @Test
    void changeNameCategory() throws CategoryNotFoundException {
        var category = mock(Category.class);
        when(categoryRepository.findById(ID)).thenReturn(Optional.of(category));
        when(category.getId()).thenReturn(ID);
        var defaulth = mock(Category.class);
        when(categoryRepository.findCategoryByName("Прочее")).thenReturn(Optional.of(defaulth));
        when(defaulth.getId()).thenReturn(defaulthId);

        String newName = "Test";
        if (category.getId().equals(defaulth.getId())) {
            CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class, () -> categoryService.changeNameCategory(newName, category.getId()));
            assertEquals("Нельзя изменять дефолтную категорию Прочее", exception.getResource());
        }

        var isChanged = categoryService.changeNameCategory(newName, category.getId());
        assertTrue(isChanged);
        verify(categoryRepository).findById(ID);
        verify(categoryRepository).findCategoryByName("Прочее");
        verify(categoryRepository).save(category);
    }

    @Test
    void deleteCategory() throws CategoryNotFoundException {
        var test_category = mock(Category.class);
        when(categoryRepository.findById(ID)).thenReturn(Optional.of(test_category));
        when(test_category.getId()).thenReturn(ID);

        var defaulth_category = mock(Category.class);
        when(categoryRepository.findCategoryByName("Прочее")).thenReturn(Optional.of(defaulth_category));
        when(defaulth_category.getId()).thenReturn(defaulthId);

        var products = mock(Product.class);
        when(productRepository.findProductsByCategory(test_category.getId())).thenReturn(List.of(products));
        when(products.getId()).thenReturn(ID);
        var child = mock(Category.class);
        when(categoryRepository.findCategoriesByParentId(test_category.getId())).thenReturn(List.of(child));

        if (test_category.equals(defaulth_category)) {
            CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class, () -> categoryService.deleteCategory(defaulth_category.getId()));
            assertEquals("Нельзя изменять дефолтную категорию Прочее", exception.getResource());
        }
        var isDeleted = categoryService.deleteCategory(test_category.getId());
        assertTrue(isDeleted);
        assertEquals(test_category.getParent(), null);
        assertNotEquals(test_category, defaulth_category);
        Assert.notEmpty(Collections.singleton((products)));
        verify(categoryRepository).findById(test_category.getId());
        verify(categoryRepository).findCategoryByName("Прочее");
        verify(categoryRepository).findCategoriesByParentId(test_category.getId());
        verify(productRepository).findProductsByCategory(test_category.getId());
        verify(productRepository).updateProductCategory(defaulth_category.getId(), products.getId());
        verify(categoryRepository).deleteCategoriesByParentId(test_category.getId());
        verify(categoryRepository).delete(test_category);
    }

    @Test
    void changeSubcategory() throws CategoryNotFoundException {
        var category = mock(Category.class);
        when(categoryRepository.findById(ID)).thenReturn(Optional.of(category));
        when(category.getId()).thenReturn(ID);

        var subcategory = mock(Category.class);
        when(categoryRepository.findById(subCategoryID)).thenReturn(Optional.of(subcategory));
        when(subcategory.getId()).thenReturn(subCategoryID);

        var defaulth_category = mock(Category.class);
        when(categoryRepository.findCategoryByName("Прочее")).thenReturn(Optional.of(defaulth_category));
        when(defaulth_category.getId()).thenReturn(defaulthId);

        var isChangedSubcategory = categoryService.changeSubcategory(subcategory.getId(), category.getId());
        CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class, () -> categoryService.changeSubcategory(defaulth_category.getId(), subcategory.getId()));
        assertEquals("Категория не найдена!", exception.getResource());
        assertNotEquals(subcategory, defaulth_category);
        assertTrue(isChangedSubcategory);
        verify(categoryRepository).findById(ID);
        verify(categoryRepository).findById(subCategoryID);
        verify(categoryRepository).findCategoryByName("Прочее");
        verify(categoryRepository).save(subcategory);
    }

    @Test
    void getFilterCategories() {
    }
}
