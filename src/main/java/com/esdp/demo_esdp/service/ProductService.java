package com.esdp.demo_esdp.service;

import com.esdp.demo_esdp.dto.*;
import com.esdp.demo_esdp.entity.Category;
import com.esdp.demo_esdp.entity.Favorites;
import com.esdp.demo_esdp.entity.Product;
import com.esdp.demo_esdp.entity.User;
import com.esdp.demo_esdp.enums.ProductStatus;
import com.esdp.demo_esdp.exception.ProductNotFoundException;
import com.esdp.demo_esdp.exception.ResourceNotFoundException;
import com.esdp.demo_esdp.exception.UserNotFoundException;
import com.esdp.demo_esdp.repositories.CategoryRepository;
import com.esdp.demo_esdp.repositories.FavoritesRepository;
import com.esdp.demo_esdp.repositories.ProductRepository;
import com.esdp.demo_esdp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImagesService imagesService;
    private final FavoritesService favoritesService;
    private final UserRepository userRepository;
    private final FavoritesRepository favoritesRepository;

    @Value("${upload.path}")
    private String uploadPath;


    public Page<ProductDTO> getProductName(String name, Pageable pageable) {
        return builderProductDTO(productRepository.getProductName(name.toLowerCase(), ProductStatus.ACCEPTED, pageable));
    }

    public Page<ProductDTO> getProductNameAll(String name, Pageable pageable) {
        return builderProductDTO(productRepository.getProductNameAll(name.toLowerCase(), pageable));
    }

    public Page<SimilarProductDto> getMainProductsListByName(String name, Pageable pageable) {
        var products = productRepository.getProductListByName(name.toLowerCase(), ProductStatus.ACCEPTED, pageable);
        return builderSimilarProductDto(products);
    }

    public Page<SimilarProductDto> getProducts(Pageable pageable) {
        return builderSimilarProductDto(productRepository.getProducts(ProductStatus.ACCEPTED, pageable));
    }

    public boolean addNewProduct(ProductAddForm productAddForm, User user) throws ResourceNotFoundException {
        Category category = categoryRepository.getCategory(productAddForm.getCategoryId())
                .orElseThrow(ResourceNotFoundException::new);

        Product product = Product.builder()
                .name(productAddForm.getName())
                .category(category)
                .user(user)
                .description(productAddForm.getDescription())
                .price(productAddForm.getPrice())
                .status(ProductStatus.MODERNIZATION)
                .dateAdd(LocalDateTime.now())
                .localities(productAddForm.getLocality())
                .endOfPayment(LocalDateTime.now().minusDays(30))
                .up(LocalDateTime.now())
                .build();
        productRepository.save(product);
        imagesService.saveImagesFile(productAddForm.getImages(), product);
        return true;
    }


    public String deleteProductById(Long productId, String email) throws ResourceNotFoundException {
        var user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        if (user.getEmail().equals(productRepository.getPublicationUserEmail(productId))
                || user.getRole().equals("Admin")) {
            imagesService.deleteImagesFile(productId);
            favoritesService.deleteFavoritesByProductId(productId);
            productRepository.deleteById(productId);
        } else {
            throw new ResourceNotFoundException();
        }
        if (user.getRole().equals("Admin")) return "admin";
        return "profile";
    }

    public void deleteProductById(Long productId) throws ProductNotFoundException {
        if (productRepository.findById(productId).isEmpty()) {
            throw new ProductNotFoundException(String.format("Не найдена публикация с id ", productId));
        }
        imagesService.deleteImagesFile(productId);
        favoritesService.deleteFavoritesByProductId(productId);
        productRepository.deleteById(productId);
    }

    public Page<ProductDTO> getProductsAll(Pageable pageable) {
        return builderProductDTO(productRepository.findAll(pageable));
    }


    public void updateProductStatusId(String status, Long id) {
        productRepository.updateProductStatus(status, id);
    }


    public Page<ProductDTO> getProductsUser(String email, Pageable pageable) {
        return builderProductDTO(productRepository.getProductsUser(email, pageable));
    }

    public Page<ProductDTO> getProductsStatus(String status, Pageable pageable) {
        if (status.equals(ProductStatus.ACCEPTED.name())) {
            return builderProductDTO(productRepository.getProductsStatus(ProductStatus.ACCEPTED, pageable));
        } else if (status.equals(ProductStatus.MODERNIZATION.name())) {
            return builderProductDTO(productRepository.getProductsStatus(ProductStatus.MODERNIZATION, pageable));
        } else if (status.equals(ProductStatus.DECLINED.name())) {
            return builderProductDTO(productRepository.getProductsStatus(ProductStatus.DECLINED, pageable));
        }
        throw new ResourceNotFoundException();
    }

    public boolean addProductToTop(Long productId, Integer hour) throws ProductNotFoundException {
        var product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Не найдена публикация с id " + productId));
        if (product.getEndOfPayment().isAfter(LocalDateTime.now()) || product.getEndOfPayment().isEqual(LocalDateTime.now())) {
            productRepository.updateProductEndOfPayment(product.getEndOfPayment().plusHours(hour), product.getId());
            return true;
        } else if (product.getEndOfPayment().isBefore(LocalDateTime.now())) {
            productRepository.updateProductEndOfPayment(LocalDateTime.now().plusHours(hour), product.getId());
            return true;
        }
        return false;
    }

    public void upProduct(Long productId) throws ProductNotFoundException {
        var product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Не найдена публикация с id " + productId));
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime productUpDate = product.getUp();
        if ((productUpDate.getDayOfYear() != today.getDayOfYear()) && (productUpDate.getYear() != today.getYear())) {
            productRepository.updateProductUpToTop(LocalDateTime.now(), product.getId());
        }
    }

    public Page<ProductDTO> getProductsCategory(Long id, Pageable pageable) {
        return builderProductDTO(productRepository.getProductsCategory(id, pageable));
    }

    public ProductDetailsDto getProductDetails(Long id, Authentication auth) throws ProductNotFoundException {
        Product p = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(String.format("Не найдена публикация с id ", id)));
        return ProductDetailsDto.builder()
                .id(p.getId())
                .name(p.getName())
                .category(p.getCategory().getName())
                .fullName(String.format("%s %s", p.getUser().getName(), p.getUser().getLastname()))
                .phoneNumber(p.getUser().getTelNumber())
                .description(p.getDescription())
                .price(p.getPrice())
                .localities(p.getLocalities())
                .imagePaths(imagesService.getImagesPathsByProductId(p.getId()))
                .similarProducts(getSimilarProducts(p.getId()))
                .amountOfLikes(favoritesRepository.getAmountOfLikes(p.getId()))
                .liked(isLiked(p, auth))
                .build();

    }

    private boolean isLiked(Product product, Authentication auth) {
        if (auth == null) {
            return false;
        }
        User user = userRepository.findByEmail(auth.getName()).orElseThrow(UserNotFoundException::new);
        Optional<Favorites> fav = favoritesRepository.findByUserAndProduct(user, product);
        return fav.isPresent();

    }

    protected List<SimilarProductDto> getSimilarProducts(Long id) throws ProductNotFoundException {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(String.format("product with id %s was not found", id)));
        Category productCategory = product.getCategory();
        List<Product> similarProducts = new ArrayList<>();
        Category currentCategory = productCategory;

        while (true) {
            similarProducts = productRepository.getSimilarProducts(currentCategory.getId(), id, ProductStatus.ACCEPTED);
            if (similarProducts.isEmpty()) {
                currentCategory = currentCategory.getParent();
                if (currentCategory == null) {
                    return null;
                }
            } else {
                break;
            }
        }

        return getProductsWithMostLikes(similarProducts, 3).stream()
                .map(p -> SimilarProductDto.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .category(p.getCategory().getName())
                        .price(p.getPrice())
                        .imagePaths(imagesService.getImagesPathsByProductId(p.getId()))
                        .build()).collect(Collectors.toList());

    }

    private List<Product> getProductsWithMostLikes(List<Product> products, int productsQty) {
        List<Long> amountOfProductLikes = products.stream().map(p -> favoritesRepository.getAmountOfLikes(p.getId())).collect(Collectors.toList());

        if (productsQty > amountOfProductLikes.size()) {
            productsQty = amountOfProductLikes.size();
        }

        List<Long> mostLikes = new ArrayList<>();
        for (int i = 0; i < productsQty; i++) {
            mostLikes.add(amountOfProductLikes.get(i));
        }

        return products.stream()
                .filter(p -> mostLikes.contains(favoritesRepository.getAmountOfLikes(p.getId())))
                .limit(productsQty).collect(Collectors.toList());

    }

    public Page<SimilarProductDto> handleFilter(FilterProductDto filters, Long categoryId, Pageable pageable) {
        filters = formatFilter(filters);

        switch (filters.getSortProduct()) {
            case "cheap":
                return getFilteredAndCheapProductsFromCategory(filters, categoryId, pageable);
            case "expensive":
                return getFilteredAndExpensiveProductsFromCategory(filters, categoryId, pageable);
            case "new":
                return getFilteredAndNewProductsFromCategory(filters, categoryId, pageable);
            default:
                return getFilteredAndFamousProductsFromCategory(filters, categoryId, pageable);
        }
    }

    private Page<SimilarProductDto> getFilteredAndNewProductsFromCategory(FilterProductDto filters, Long categoryId, Pageable pageable) {
        var newProducts = productRepository.getNewProducts(ProductStatus.ACCEPTED, filters.getSearch().toLowerCase(),
                filters.getPriceFrom(), filters.getPriceTo(), filters.getLocality().toLowerCase(), categoryId, pageable);
        return builderSimilarProductDto(newProducts);
    }

    private Page<SimilarProductDto> getFilteredAndExpensiveProductsFromCategory(FilterProductDto filters, Long categoryId, Pageable pageable) {
        var expensiveProducts = productRepository.getExpensiveProducts(ProductStatus.ACCEPTED, filters.getSearch().toLowerCase(),
                filters.getPriceFrom(), filters.getPriceTo(), filters.getLocality().toLowerCase(), categoryId, pageable);
        return builderSimilarProductDto(expensiveProducts);
    }

    private Page<SimilarProductDto> getFilteredAndFamousProductsFromCategory(FilterProductDto filters, Long categoryId, Pageable pageable) {
        var famousProducts = productRepository.getFamousProducts(ProductStatus.ACCEPTED, filters.getSearch().toLowerCase(),
                filters.getPriceFrom(), filters.getPriceTo(), filters.getLocality().toLowerCase(), categoryId, pageable);
        return builderSimilarProductDto(famousProducts);
    }

    private Page<SimilarProductDto> getFilteredAndCheapProductsFromCategory(FilterProductDto filters, Long categoryId, Pageable pageable) {
        var cheapProducts = productRepository.getCheapProducts(ProductStatus.ACCEPTED, filters.getSearch().toLowerCase(),
                filters.getPriceFrom(), filters.getPriceTo(), filters.getLocality().toLowerCase(), categoryId, pageable);
        return builderSimilarProductDto(cheapProducts);
    }

    private FilterProductDto formatFilter(FilterProductDto filter) {
        if (filter.getLocality() == null) {
            filter.setLocality("Бишкек");
        }
        if (filter.getSearch() == null) {
            filter.setSearch("");
        }

        if (filter.getPriceFrom() == null) {
            filter.setPriceFrom(0);
        }

        if (filter.getPriceTo() == null) {
            filter.setPriceTo(Integer.MAX_VALUE);
        }

        if (filter.getSortProduct() == null) {
            filter.setSortProduct("popular");
        }

        return filter;
    }

    private Page<ProductDTO> builderProductDTO(Page<Product> products) {
        return products
                .map(p -> ProductDTO.builder()
                        .id(p.getId())
                        .category(p.getCategory().getName())
                        .name(p.getName())
                        .user(p.getUser())
                        .status(p.getStatus().name())
                        .price(p.getPrice())
                        .imagePaths(imagesService.getImagesPathsByProductId(p.getId()))
                        .build());
    }


    private Page<SimilarProductDto> builderSimilarProductDto(Page<Product> products) {
        return products
                .map(p -> SimilarProductDto.builder()
                        .id(p.getId())
                        .category(p.getCategory().getName())
                        .name(p.getName())
                        .price(p.getPrice())
                        .imagePaths(imagesService.getImagesPathsByProductId(p.getId()))
                        .build());
    }

    public List<ProductDTO> getUserProductsByStatus(String userEmail, ProductStatus status) {
        return productRepository.getUserProductsByStatus(userEmail, status).stream()
                .map(p -> ProductDTO.fromImage(p, imagesService.getImagesPathsByProductId(p.getId()))).collect(Collectors.toList());

    }
}
