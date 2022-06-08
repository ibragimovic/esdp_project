package com.esdp.demo_esdp.service;

import com.esdp.demo_esdp.dto.ProductDTO;
import com.esdp.demo_esdp.exeption.ResourceNotFoundException;
import com.esdp.demo_esdp.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;


    public Page<ProductDTO> getProductName(String name, Pageable pageable) {
        return productRepository.getProductName(name.toLowerCase(), pageable)
                .map(ProductDTO::from);
    }

    public Page<ProductDTO> getProductCategory(String category, Pageable pageable) {
        return productRepository.getProductCategory(category.toLowerCase(), pageable)
                .map(ProductDTO::from);
    }

    public Page<ProductDTO> getProductPrice(Integer from, Integer before, Pageable pageable) {
        return productRepository.getProductPrice(from, before, pageable)
                .map(ProductDTO::from);
    }

    public Page<ProductDTO> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductDTO::from);
    }

    public ProductDTO getProduct(Long id) {
        var product = productRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return ProductDTO.from(product);
    }

}
