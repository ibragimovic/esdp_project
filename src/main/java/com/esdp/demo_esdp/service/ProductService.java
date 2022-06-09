package com.esdp.demo_esdp.service;

import com.esdp.demo_esdp.dto.ProductDTO;
import com.esdp.demo_esdp.entity.ProductStatus;
import com.esdp.demo_esdp.exeption.ResourceNotFoundException;
import com.esdp.demo_esdp.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;


    public Page<ProductDTO> getProductName(String name, Pageable pageable) {
        return (Page<ProductDTO>) productRepository.getProductName(name.toLowerCase(), pageable)
                .stream().filter(e -> e.getStatus().equals(ProductStatus.ACCEPTED.toString()))
                .map(ProductDTO::from)
                .collect(Collectors.toList());
    }

    public Page<ProductDTO> getProductCategory(String category, Pageable pageable) {
        return (Page<ProductDTO>) productRepository.getProductCategory(category.toLowerCase(), pageable)
                .stream().filter(e -> e.getStatus().equals(ProductStatus.ACCEPTED.toString()))
                .map(ProductDTO::from)
                .collect(Collectors.toList());
    }

    public Page<ProductDTO> getProductPrice(Integer from, Integer before, Pageable pageable) {
        return (Page<ProductDTO>) productRepository.getProductPrice(from, before, pageable)
                .stream().filter(e -> e.getStatus().equals(ProductStatus.ACCEPTED.toString()))
                .map(ProductDTO::from)
                .collect(Collectors.toList());
    }

    public Page<ProductDTO> getProducts(Pageable pageable) {
        return (Page<ProductDTO>) productRepository.findAll(pageable)
                .stream().filter(e -> e.getStatus().equals(ProductStatus.ACCEPTED.toString()))
                .map(ProductDTO::from)
                .collect(Collectors.toList());
    }

    public ProductDTO getProduct(Long id) throws ResourceNotFoundException {
        var product = productRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        if (product.getStatus().equals(ProductStatus.ACCEPTED)) {
            return ProductDTO.from(product);
        }
        throw new ResourceNotFoundException();
    }

}
