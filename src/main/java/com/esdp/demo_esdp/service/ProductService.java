package com.esdp.demo_esdp.service;

import com.esdp.demo_esdp.dto.ProductAddForm;
import com.esdp.demo_esdp.dto.ProductDTO;
import com.esdp.demo_esdp.entity.Product;
import com.esdp.demo_esdp.entity.User;
import com.esdp.demo_esdp.enums.ProductStatus;
import com.esdp.demo_esdp.exception.ResourceNotFoundException;
import com.esdp.demo_esdp.repositories.CategoryRepository;
import com.esdp.demo_esdp.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImagesService imagesService;
    private final FavoritesService favoritesService;

    @Value("${upload.path}")
    private String uploadPath;


    public Page<ProductDTO> getProductName(String name, Pageable pageable) {
        return productRepository.getProductName(name.toLowerCase(), ProductStatus.ACCEPTED.name(), pageable)
                .map(ProductDTO::from);
    }

    public Page<ProductDTO> getProductCategory(String category, Pageable pageable) {
        return productRepository.getProductName(category.toLowerCase(), ProductStatus.ACCEPTED.name(), pageable)
                .map(ProductDTO::from);
    }

    public Page<ProductDTO> getProductPrice(Integer from, Integer before, Pageable pageable) {
        return productRepository.getProductPrice(from, before, ProductStatus.ACCEPTED.name(), pageable)
                .map(ProductDTO::from);
    }

    public Page<ProductDTO> getProducts(Pageable pageable) {
        return productRepository.getProducts(ProductStatus.ACCEPTED, pageable)
                .map(ProductDTO::from);
    }

    public ProductDTO getProduct(Long id) throws ResourceNotFoundException {
        var product = productRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        if (product.getStatus().equals(ProductStatus.ACCEPTED)) {
            return ProductDTO.from(product);
        }
        throw new ResourceNotFoundException();
    }


    public void addNewProduct(ProductAddForm productAddForm, User user) {
        var category = categoryRepository.getCategory(productAddForm.getCategoryId())
                .orElseThrow(ResourceNotFoundException::new);
        var product = Product.builder()
                .name(productAddForm.getName())
                .category(category)
                .user(user)
                .description(productAddForm.getDescription())
                .price(productAddForm.getPrice())
                .status(ProductStatus.MODERNIZATION)
                .dateAdd(LocalDateTime.now())
                .build();
        productRepository.save(product);
        imagesService.saveImagesFile(productAddForm.getImages(),product);
    }


    public void deleteProductById(Long productId, User user) {
        if (user.getEmail().equals(productRepository.getPublicationUserEmail(productId))
                || user.getRole().equals("Admin")) {
            imagesService.deleteImagesFile(productId);
            favoritesService.deleteFavoritesByProductId(productId);
            productRepository.deleteById(productId);
        } else {
            throw new ResourceNotFoundException();
        }
    }

    protected Product findProductById(Long productId) throws ResourceNotFoundException {
        return productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("product with id %s was not found", productId))
        );
    }

    public List<ProductDTO> getProductsAll (){
        return productRepository.findAll()
                .stream().map(ProductDTO::from).collect(Collectors.toList());
    }

    public void deleteProductId(Long id){
        productRepository.deleteById(id);
    }


    public void updateProductStatusId(String status,Long id){
        productRepository.updateProductStatus(status,id);
    }
}
