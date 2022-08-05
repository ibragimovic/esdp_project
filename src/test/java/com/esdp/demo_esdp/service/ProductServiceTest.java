package com.esdp.demo_esdp.service;

import com.esdp.demo_esdp.entity.Product;
import com.esdp.demo_esdp.enums.ProductStatus;
import com.esdp.demo_esdp.exception.ProductNotFoundException;
import com.esdp.demo_esdp.repositories.CategoryRepository;
import com.esdp.demo_esdp.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    private final long ID = 1L;

    @Autowired
    ProductService productService;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    CategoryRepository categoryRepository;


    @Test
    void getProductName() {
    }

    @Test
    void getProductNameOrdered() {
    }

    @Test
    void getMainProductsListByName() {
    }

    @Test
    void getProductCategory() {
    }

    @Test
    void getProductPrice() {
    }

    @Test
    void getProducts() {
    }

    @Test
    void getMainPageProducts() {
    }

    @Test
    void getMainProductsList() {
    }

    @Test
    void getProduct() {
    }

    @Test
    void addNewProduct() {
    }

    @Test
    void deleteProductById() {
    }

    @Test
    void findProductById() {
    }

    @Test
    void getProductsAll() {
    }

    @Test
    void updateProductStatusId() {
    }

    @Test
    void getProductsUser() {
    }

    @Test
    void getProductsStatus() {
    }

    @Test
    void getProductsName() {
    }

    @Test
    void addProductToTop() throws ProductNotFoundException {
        int hours = 24;
        var product = mock(Product.class);
        when(productRepository.findById(ID)).thenReturn(Optional.of(product));
        when(product.getEndOfPayment()).thenReturn(LocalDateTime.now());
        when(product.getId()).thenReturn(ID);

        var isAddToTop = productService.addProductToTop(product.getId(), hours);

        assertTrue(isAddToTop);
        verify(productRepository).findById(product.getId());

    }

    @Test
    void upProduct() throws ProductNotFoundException {
        var product = mock(Product.class);
        when(productRepository.findById(ID)).thenReturn(Optional.of(product));
        when(product.getUp()).thenReturn(LocalDateTime.now().minusDays(1));
        when(product.getId()).thenReturn(ID);

        var isUped = productService.upProduct(product.getId());
        assertTrue(isUped);
        assertTrue(product.getUp().getDayOfYear() != LocalDateTime.now().getDayOfYear());
        verify(productRepository).findById(product.getId());
    }


    @Test
    void getProductsToMainPage() {
    }

    @Test
    void getProductsCategory() {
    }

    @Test
    void getProductDetails() {
    }

    @Test
    void getSimilarProducts() {
    }

    @Test
    void handleFilter() {
    }
}