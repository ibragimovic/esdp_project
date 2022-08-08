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

}