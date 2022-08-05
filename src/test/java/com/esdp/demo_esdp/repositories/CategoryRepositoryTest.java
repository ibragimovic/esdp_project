package com.esdp.demo_esdp.repositories;

import com.esdp.demo_esdp.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class CategoryRepositoryTest {
    private final long ID = 2L;

    @MockBean
    private CategoryRepository categoryRepository;


    @Test
    void findCategoriesByParentNull() {
        List<Category> categoryByNullParent = categoryRepository.findCategoriesByParentNull();
        for (int i = 0; i < categoryByNullParent.size(); i++) {
            assertNull(categoryByNullParent.get(i).getParent());
        }
    }

    @Test
    void findCategoriesByParentId() {
        Category parent = mock(Category.class);
        when(parent.getId()).thenReturn(ID);

        List<Category> categories = categoryRepository.findCategoriesByParentId(parent.getId());
        assertNotNull(parent);
        assertNotNull(categories);
        for (int i = 0; i < categories.size(); i++) {
            assertEquals(categories.get(i).getParent().getId(), parent.getId());
        }
    }

    @Test
    void getCategory() {
        Optional<Category> category = categoryRepository.getCategory(ID);
        assertNotNull(category);
    }

    @Test
    void getCatParentId() {
        List<Long> categoryByNotNullParent = categoryRepository.getCatParentId();
        for (int i = 0; i < categoryByNotNullParent.size(); i++) {
            assertNotNull(categoryByNotNullParent.get(i));
        }
    }


    @Test
    void findCategoryByName() {
        Category test = mock(Category.class);
        when(test.getName()).thenReturn("Авто-мото");
        String name = "Авто-мото";
        Optional<Category> category = categoryRepository.findCategoryByName(name);
        assertNotNull(category);
    }

    @Test
    void deleteCategoriesByParentId() {
        Category testCategory = mock(Category.class);
        when(testCategory.getId()).thenReturn(ID);

        categoryRepository.deleteCategoriesByParentId(testCategory.getId());
        var categoryOfParent = categoryRepository.findCategoriesByParentId(testCategory.getId());
        for (int i = 0; i < categoryOfParent.size(); i++) {
            assertNull(categoryOfParent.get(i));
        }

    }
}