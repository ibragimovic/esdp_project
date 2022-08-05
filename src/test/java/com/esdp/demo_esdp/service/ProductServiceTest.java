package com.esdp.demo_esdp.service;

import com.esdp.demo_esdp.dto.ProductAddForm;
import com.esdp.demo_esdp.entity.Category;
import com.esdp.demo_esdp.entity.Images;
import com.esdp.demo_esdp.entity.Product;
import com.esdp.demo_esdp.entity.User;
import com.esdp.demo_esdp.enums.ProductStatus;
import com.esdp.demo_esdp.exception.CategoryNotFoundException;
import com.esdp.demo_esdp.exception.ProductNotFoundException;
import com.esdp.demo_esdp.exception.ResourceNotFoundException;
import com.esdp.demo_esdp.repositories.CategoryRepository;
import com.esdp.demo_esdp.repositories.ImagesRepository;
import com.esdp.demo_esdp.repositories.ProductRepository;
import com.esdp.demo_esdp.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

    @MockBean
    UserRepository userRepository;

    @MockBean
    ImagesRepository imagesRepository;

    @Test
    void getProductName() {
//        Pageable page = PageRequest.of(2,10);
//        String name = "test";
//        var product  = mock(Product.class);
//        when(product.getName()).thenReturn("Test");
//        when(productRepository.getProductName(name, ProductStatus.ACCEPTED,page)).getMock();
//        var products = productService.getProductName(name.toLowerCase(),page);
//            assertEquals(name.toLowerCase(),product.getName());

    }

    @Test
    void getMainProductsListByName() {
    }

    @Test
    void getProducts() {
    }

    @Test
    void addNewProduct() throws ResourceNotFoundException {
//        var category = mock(Category.class);
//        when(categoryRepository.getCategory(ID)).thenReturn(Optional.of(category));
//        when(category.getId()).thenReturn(ID);
//
//        var images = mock(MultipartFile.class);
//
//        var user = mock(User.class);
//        ProductAddForm product = new ProductAddForm("test",ID,"test publications",125, List.of(images),"Бишкек");
//
//
//        assertThrows(ResourceNotFoundException.class, () -> productService.addNewProduct(product, user));
//
//        var isCreated = productService.addNewProduct(product, user);
//        assertTrue(isCreated);

    }

    @Test
    void deleteProductById() {
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

//        var isUped = productService.upProduct(product.getId());
//        assertTrue(isUped);
        assertTrue(product.getUp().getDayOfYear() != LocalDateTime.now().getDayOfYear());
        verify(productRepository).findById(product.getId());
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