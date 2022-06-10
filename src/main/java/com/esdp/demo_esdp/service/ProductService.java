package com.esdp.demo_esdp.service;

import com.esdp.demo_esdp.dto.ImageDTO;
import com.esdp.demo_esdp.dto.ProductAddForm;
import com.esdp.demo_esdp.dto.ProductDTO;
import com.esdp.demo_esdp.entity.ProductStatus;
import com.esdp.demo_esdp.entity.User;
import com.esdp.demo_esdp.exeption.ResourceNotFoundException;
import com.esdp.demo_esdp.repositories.ImagesRepository;
import com.esdp.demo_esdp.repositories.ProductRepository;
import com.esdp.demo_esdp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ImagesRepository imagesRepository;

    private final String uploadPath = "C:\\Vova\\java\\gitHub\\java_group_11_homework_55_smagin_vladimir/uploads\\";


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

        }
    }
}
