package com.esdp.demo_esdp.service;

import com.esdp.demo_esdp.entity.Category;
import com.esdp.demo_esdp.entity.Product;
import com.esdp.demo_esdp.entity.User;
import com.esdp.demo_esdp.enums.ProductStatus;
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
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    private final long ID = 1L;

    @Autowired
    ProductService productService;

    @Autowired
    ImagesService imagesService;

    @Autowired
    FavoritesService favoritesService;

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
        String name = "test";
        Pageable page = PageRequest.of(2, 10, Sort.unsorted());
        List<Product> products = new ArrayList<>();
        Page<Product> productsPageResponce = new PageImpl(products);
        when(productRepository.getProductName(name, ProductStatus.ACCEPTED, page)).thenReturn(productsPageResponce);
        var productList = productService.getProductName(name, page);
        assertNotNull(productList.getContent());
        for (int i = 0; i < productList.getContent().size(); i++) {
            assertTrue(name.toLowerCase().equalsIgnoreCase(productList.getContent().get(i).getName()));
        }
        verify(productRepository).getProductName(name, ProductStatus.ACCEPTED, page);

    }

    @Test
    void getMainProductsListByName() {
        String name = "test";
        Pageable page = PageRequest.of(2, 10, Sort.unsorted());
        List<Product> productsList = new ArrayList<>();
        Page<Product> productsPageResponce = new PageImpl(productsList);
        when(productRepository.getProductListByName(name, ProductStatus.ACCEPTED, page)).thenReturn(productsPageResponce);
        var products = productService.getMainProductsListByName(name, page);
        for (int i = 0; i < products.getContent().size(); i++) {
            assertTrue(name.equals(products.getContent().get(i).getName()) || name.equals(productsPageResponce.getContent().get(i).getDescription()));
        }
        assertNotNull(products);
        verify(productRepository).getProductListByName(name, ProductStatus.ACCEPTED, page);
    }

    @Test
    void getProducts() {
        Pageable page = PageRequest.of(2, 10, Sort.unsorted());
        List<Product> productsList = new ArrayList<>();
        Page<Product> productsPageResponce = new PageImpl(productsList);
        when(productRepository.getProducts(ProductStatus.ACCEPTED, page)).thenReturn(productsPageResponce);
        var products = productService.getProducts(page);
        assertNotNull(products);
        for (int i = 0; i < products.getContent().size(); i++) {
            assertTrue(productsPageResponce.getContent().get(i).getStatus().equals(ProductStatus.ACCEPTED));
        }
        verify(productRepository).getProducts(ProductStatus.ACCEPTED, page);

    }

    @Test
    void addNewProduct() throws ResourceNotFoundException {
    }

    @Test
    void deleteProductById() {
        var user = mock(User.class);
        when(userRepository.findByEmail("test@test.test")).thenReturn(Optional.of(user));
        when(user.getEmail()).thenReturn("test@test.test");
        when(user.getRole()).thenReturn("Admin");
        var product = mock(Product.class);
        when(product.getId()).thenReturn(ID);
        var isDeleted = productService.deleteProductById(product.getId(), user.getEmail());
        imagesService.deleteImagesFile(ID);
        favoritesService.deleteFavoritesByProductId(product.getId());
        productRepository.deleteById(product.getId());
        assertEquals("admin", isDeleted);
        verify(productRepository).getPublicationUserEmail(ID);
    }

}