package com.esdp.demo_esdp.service;

import com.esdp.demo_esdp.dto.ProductAddForm;
import com.esdp.demo_esdp.dto.ProductDTO;
import com.esdp.demo_esdp.dto.ProductDetailsDto;
import com.esdp.demo_esdp.dto.SimilarProductDto;
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

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Collections;
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
        return productRepository.getProductName(name.toLowerCase(), ProductStatus.ACCEPTED, pageable)
                .map(ProductDTO::from);
    }

    public Page<ProductDTO> getProductCategory(String category, Pageable pageable) {
        return productRepository.getProductName(category.toLowerCase(), ProductStatus.ACCEPTED, pageable)
                .map(ProductDTO::from);
    }

    public Page<ProductDTO> getProductPrice(Integer from, Integer before, Pageable pageable) {
        return productRepository.getProductPrice(from, before, ProductStatus.ACCEPTED, pageable)
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


    public void addNewProduct(ProductAddForm productAddForm, User user) throws IOException, ProductNotFoundException {
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
    }


    public String deleteProductById(Long productId, String email) {
        var user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        if (user.getEmail().equals(productRepository.getPublicationUserEmail(productId))
                || user.getRole().equals("Admin")) {
            imagesService.deleteImagesByProductId(productId);
            favoritesService.deleteFavoritesByProductId(productId);
            productRepository.deleteById(productId);
        } else {
            throw new ResourceNotFoundException();
        }
        if (user.getRole().equals("Admin")) return "admin";
        return "profile";
    }

    protected Product findProductById(Long productId) throws ResourceNotFoundException {
        return productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("product with id %s was not found", productId))
        );
    }

    public List<ProductDTO> getProductsAll() {
        return productRepository.findAll()
                .stream().map(ProductDTO::from).collect(Collectors.toList());
    }


    public void updateProductStatusId(String status, Long id) {
        productRepository.updateProductStatus(status, id);
    }


    public List<ProductDTO> getProductsUser(String email) {
        return productRepository.getProductsUser(email)
                .stream().map(ProductDTO::from).collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsStatus(String status) {
        if (status.equals(ProductStatus.ACCEPTED.name())) {
            return productRepository.getProductsStatus(ProductStatus.ACCEPTED)
                    .stream().map(ProductDTO::from).collect(Collectors.toList());
        } else if (status.equals(ProductStatus.MODERNIZATION.name())) {
            return productRepository.getProductsStatus(ProductStatus.MODERNIZATION)
                    .stream().map(ProductDTO::from).collect(Collectors.toList());
        } else if (status.equals(ProductStatus.DECLINED.name())) {
            return productRepository.getProductsStatus(ProductStatus.DECLINED)
                    .stream().map(ProductDTO::from).collect(Collectors.toList());
        }
        throw new ResourceNotFoundException();
    }

    public List<ProductDTO> getProductsName(String name) {
        return productRepository.getProductsName(name.toLowerCase())
                .stream().map(ProductDTO::from).collect(Collectors.toList());
    }

    public void addProductToTop(Long productId,Integer hour) throws ProductNotFoundException {
        var product = productRepository.findById(productId).orElseThrow(()->new ProductNotFoundException("Не найден продукт с id "+ productId));
        if (product.getEndOfPayment().isAfter(LocalDateTime.now())){
            productRepository.updateProductEndOfPayment(product.getEndOfPayment().plusHours(hour),product.getId());
        }else if (product.getEndOfPayment().isBefore(LocalDateTime.now())) {
            productRepository.updateProductEndOfPayment(LocalDateTime.now().plusHours(hour),product.getId());
        }
    }

    public void upProduct(Long productId) throws ProductNotFoundException {
        var product = productRepository.findById(productId).orElseThrow(()->new ProductNotFoundException("Не найден продукт с id " + productId));
        if (product.getUp().getDayOfYear()!=LocalDateTime.now().getDayOfYear()) {
            productRepository.updateProductUpToTop(LocalDateTime.now(), product.getId());
        }else {
            throw new ProductNotFoundException("Вы достигли максимального количества возможности делать UP!");
        }
    }

    public Page<ProductDTO> getTopProduct(Pageable pageable){
        var products = productRepository.findTopProduct(ProductStatus.ACCEPTED,pageable);
        return products.map(ProductDTO::from);

    }

    public Page<ProductDTO> getProductsToMainPage(Pageable pageable) {
        return productRepository.getProductsToMainPage(ProductStatus.ACCEPTED, pageable)
                .map(ProductDTO::from);
    }

    public List<ProductDTO> getProductsCategory(Long id) {
        return productRepository.getProductsCategory(id)
                .stream().map(ProductDTO::from).collect(Collectors.toList());
    }

    public ProductDetailsDto getProductDetails(Long id, Authentication auth) throws ProductNotFoundException {
        Product p=productRepository.findById(id).orElseThrow( ()->new ProductNotFoundException(String.format("product with id %s was not found",id)) );
        return ProductDetailsDto.builder()
                .id(p.getId())
                .name(p.getName())
                .category(p.getCategory().getName())
                .fullName(String.format("%s %s",p.getUser().getName(),p.getUser().getLastname()))
                .phoneNumber(p.getUser().getTelNumber())
                .description(p.getDescription())
                .price(p.getPrice())
                .localities(p.getLocalities())
                .imagePaths(imagesService.getImagesPathsByProductId(p.getId()))
                .similarProducts(getSimilarProducts(p.getId()))
                .amountOfLikes(favoritesRepository.getAmountOfLikes(p.getId()))
                .liked(isLiked(p,auth))
                .build();

    }

    private boolean isLiked(Product product,Authentication auth){
        if(auth==null){
            return false;
        }
        User user=userRepository.findByEmail(auth.getName()).orElseThrow(UserNotFoundException::new);
        Optional<Favorites> fav=favoritesRepository.findByUserAndProduct(user,product);
        return fav.isPresent();

    }

    protected List<SimilarProductDto> getSimilarProducts(Long id) throws ProductNotFoundException {
        Product product=productRepository.findById(id).orElseThrow(()->new ProductNotFoundException(String.format("product with id %s was not found",id)));
        Category productCategory=product.getCategory();
        List<Product> similarProducts= new ArrayList<>();
        Category currentCategory=productCategory;

        while(true){
            similarProducts=productRepository.getSimilarProducts(currentCategory.getId(),id);
            if(similarProducts.isEmpty()){
                currentCategory=currentCategory.getParent();
                if(currentCategory==null){
                    return null;
                }
            }else{
                break;
            }
        }

        return getProductsWithMostLikes(similarProducts,3).stream()
                .map(p->SimilarProductDto.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .category(p.getCategory().getName())
                        .price(p.getPrice())
                        .imagePaths(imagesService.getImagesPathsByProductId(p.getId()))
                        .build()).collect(Collectors.toList());

    }

    private List<Product> getProductsWithMostLikes(List<Product> products, int productsQty){
        List<Long> amountOfProductLikes=products.stream().map(p->favoritesRepository.getAmountOfLikes(p.getId())).collect(Collectors.toList());

        if(productsQty>amountOfProductLikes.size()){
            productsQty= amountOfProductLikes.size();
        }

        List<Long> mostLikes=new ArrayList<>();
        for(int i=0;i<productsQty;i++){
            mostLikes.add(amountOfProductLikes.get(i));
        }

        return products.stream()
                .filter(p->mostLikes.contains(favoritesRepository.getAmountOfLikes(p.getId())))
                .limit(productsQty).collect(Collectors.toList());

    }



}
