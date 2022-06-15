package com.esdp.demo_esdp.service;

import com.esdp.demo_esdp.dto.ImageDTO;
import com.esdp.demo_esdp.dto.ProductAddForm;
import com.esdp.demo_esdp.dto.ProductDTO;
import com.esdp.demo_esdp.entity.Images;
import com.esdp.demo_esdp.entity.Product;
import com.esdp.demo_esdp.enums.ProductStatus;
import com.esdp.demo_esdp.entity.User;
import com.esdp.demo_esdp.exception.ResourceNotFoundException;
import com.esdp.demo_esdp.repositories.CategoryRepository;
import com.esdp.demo_esdp.repositories.ImagesRepository;
import com.esdp.demo_esdp.repositories.ProductRepository;
import com.esdp.demo_esdp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ImagesRepository imagesRepository;
    private final CategoryRepository categoryRepository;

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
        return productRepository.getProducts(ProductStatus.ACCEPTED.name(), pageable)
                .map(ProductDTO::from);
    }

    public ProductDTO getProduct(Long id) throws ResourceNotFoundException {
        var product = productRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        if (product.getStatus().equals(ProductStatus.ACCEPTED)) {
            return ProductDTO.from(product);
        }
        throw new ResourceNotFoundException();
    }


    public void addNewProduct(ProductAddForm productAddForm, ImageDTO imageDTO, User user) {
        try {
            var category = categoryRepository.getCategory(productAddForm.getCategoryId())
                    .orElseThrow(ResourceNotFoundException::new);
            if (imageDTO.getImages() != null) {
                File upload = new File(uploadPath);
                if (!upload.exists()) {
                    upload.mkdir();
                }
                var product = Product.builder()
                        .name(productAddForm.getName())
                        .category(category)
                        .user(user)
                        .description(productAddForm.getDescription())
                        .price(productAddForm.getPrice())
                        .status(ProductStatus.MODERNIZATION)
                        .dateAdd(LocalDateTime.now())
                        .build();
                for (int i = 0; i < imageDTO.getImages().size(); i++) {
                    String uuid = UUID.randomUUID().toString();
                    String resulFileName = uuid + "." + imageDTO.getImages().get(i).getOriginalFilename();
                    imageDTO.getImages().get(i).transferTo(new File(uploadPath + resulFileName));
                    Images image = Images.builder()
                            .path(resulFileName)
                            .product(product)
                            .build();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void deleteProductById(Long productId, User user) {
        if (user.getEmail().equals(productRepository.getPublicationUserEmail(productId))
                || user.getRole().equals("Admin")) {
            var imageProduct = imagesRepository.getImagesProduct(productId);
            if (!imageProduct.isEmpty()) {
                imageProduct.forEach(i -> imagesRepository.deleteById(i.getId()));
            }
            productRepository.deleteById(productId);
            var paths = imagesRepository.getProductImagePath(productId);
            paths.forEach(i -> {
                try {
                    Files.delete(Paths.get(uploadPath + i));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        } else {
            throw new ResourceNotFoundException();
        }
    }

    protected Product findProductById(Long productId) throws ResourceNotFoundException{
        return productRepository.findById(productId).orElseThrow(
                ()->new ResourceNotFoundException(String.format("product with id %s was not found",productId))
        );
    }
}
