package com.esdp.demo_esdp.controller;

import com.esdp.demo_esdp.dto.CategoryDTO;
import com.esdp.demo_esdp.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    AdminController adminController;

    @Mock
    CategoryDTO categoryDTO;

    @MockBean
    CategoryService categoryService;

    @Mock
    Model model;

    private MockMvc mockMvc;

    @Test
    void getAdmin() {
    }

    @Test
    void getAdminUsers() {
    }

    @Test
    void resendActivationCode() {
    }

    @Test
    void deleteUserId() {
    }

    @Test
    void updateStatusProduct() {
    }

    @Test
    void addProductToTop() {
    }

    @Test
    void getProductName() {
    }

    @Test
    void getProductUser() {
    }

    @Test
    void getProductsStatus() {
    }

    @Test
    void testGetProductUser() {
    }

    @Test
    void categoryPage() {
        var categories = adminController.categoryPage(model);
        assertNotNull(categories);
        Mockito.verify(categoryService).getHierarchicalCategories();
    }

    @Test
    void createCategory() {
        CategoryDTO categoryDTO = mock(CategoryDTO.class);
        adminController.createCategory(null, categoryDTO);
        assertNotNull(categoryDTO);

    }

    @Test
    void changeNameCategory() {
    }

    @Test
    void deleteOneCategory() {
    }

    @Test
    void changeParentByCategory() {
    }

    @Test
    void getUserName() {
    }

    @Test
    void getUserEmail() {
    }

    @Test
    void getUserLogin() {
    }

    @Test
    void getUserLastName() {
    }

    @Test
    void getUserTel() {
    }

    @Test
    void getUserStatus() {
    }
}