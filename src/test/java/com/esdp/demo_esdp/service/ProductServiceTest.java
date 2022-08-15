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

    @Test
    void getProductsAll() {
        Pageable page = PageRequest.of(2, 10, Sort.unsorted());
        List<Product> productsList = new ArrayList<>();
        Page<Product> productsPageResponce = new PageImpl(productsList);
        when(productRepository.findAll(page)).thenReturn(productsPageResponce);
        var products = productService.getProductsAll(page);
        assertNotNull(products);
        verify(productRepository).findAll(page);

    }

    @Test
    void getProductsUser() {
        Pageable page = PageRequest.of(2, 10, Sort.unsorted());
        List<Product> productsList = new ArrayList<>();
        Page<Product> productsPageResponce = new PageImpl(productsList);
        var user = mock(User.class);
        when(user.getEmail()).thenReturn("test@test.test");
        when(productRepository.getProductsUser(user.getEmail(), page)).thenReturn(productsPageResponce);
        var products = productService.getProductsUser(user.getEmail(), page);
        assertNotNull(products);
        for (int i = 0; i < products.getContent().size(); i++) {
            assertTrue(user.getEmail().equals(products.getContent().get(i).getUser().getEmail()));
        }
        verify(productRepository).getProductsUser(user.getEmail(), page);
    }

    @Test
    void getProductsStatus() {
        Pageable page = PageRequest.of(2, 10, Sort.unsorted());
        List<Product> productsList = new ArrayList<>();
        Page<Product> productsPageResponce = new PageImpl(productsList);
        when(productRepository.getProductsStatus(ProductStatus.DECLINED, page)).thenReturn(productsPageResponce);
        var products = productService.getProductsStatus(ProductStatus.DECLINED.name(), page);
        assertNotNull(products);
        for (int i = 0; i < products.getContent().size(); i++) {
            assertTrue(ProductStatus.ACCEPTED.name().equals(products.getContent().get(i).getStatus()));
        }
        verify(productRepository).getProductsStatus(ProductStatus.DECLINED, page);
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
//        assertTrue(product.getUp().getDayOfYear() != LocalDateTime.now().getDayOfYear());
        verify(productRepository).findById(product.getId());
        verify(productRepository).updateProductUpToTop(LocalDateTime.now(),product.getId());
    }

    @Test
    void getProductsCategory() {
        Pageable page = PageRequest.of(2, 10, Sort.unsorted());
        List<Product> productsList = new ArrayList<>();
        Page<Product> productsPageResponce = new PageImpl(productsList);
        var category = mock(Category.class);
        when(category.getId()).thenReturn(ID);
        when(productRepository.getProductsCategory(category.getId(),page)).thenReturn(productsPageResponce);
        var productsByCategory = productService.getProductsCategory(category.getId(),page);
        assertNotNull(productsByCategory);
        for (int i = 0; i < productsByCategory.getContent().size(); i++) {
            assertTrue(category.getId().equals(productsList.get(i).getCategory().getId()));
        }
        verify(productRepository).getProductsCategory(category.getId(),page);
    }

}