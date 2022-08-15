package com.esdp.demo_esdp.service;

import com.esdp.demo_esdp.dto.FavoritesDTO;
import com.esdp.demo_esdp.dto.FavoritesJson;
import com.esdp.demo_esdp.dto.ProductDTO;
import com.esdp.demo_esdp.dto.SimilarProductDto;
import com.esdp.demo_esdp.entity.Favorites;
import com.esdp.demo_esdp.entity.Product;
import com.esdp.demo_esdp.entity.User;
import com.esdp.demo_esdp.enums.ProductStatus;
import com.esdp.demo_esdp.exception.ProductNotFoundException;
import com.esdp.demo_esdp.exception.UserNotFoundException;
import com.esdp.demo_esdp.repositories.FavoritesRepository;
import com.esdp.demo_esdp.repositories.ImagesRepository;
import com.esdp.demo_esdp.repositories.ProductRepository;
import com.esdp.demo_esdp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoritesService {
    private final UserRepository userRepository;
    private final FavoritesRepository favoritesRepository;
    private final ProductRepository productRepository;
    private final ImagesRepository imagesRepository;
    private final ImagesService imagesService;


    public List<FavoritesDTO> getFavoritesUser(String email) throws ProductNotFoundException {
        List<FavoritesDTO> favoritesList = new ArrayList<>();
        var favorites = favoritesRepository.getFavoritesUser(email, ProductStatus.ACCEPTED);
        if (!favorites.isEmpty()) {
            for (int i = 0; i < favorites.size(); i++) {
                var product = productRepository.findById(favorites.get(i).getProduct().getId()).orElseThrow(() -> new ProductNotFoundException("Публикация не найдена!"));
                var image = imagesRepository.getProductImagePath(product.getId());
                favoritesList.add(FavoritesDTO.from(favorites.get(i), image));
            }
        }
        return favoritesList;
    }

    public List<ProductDTO> getUsersFav(String email) {
        return favoritesRepository.getFavProducts(email, ProductStatus.ACCEPTED).stream()
                .map(p -> ProductDTO.fromImage(p, imagesService.getImagesPathsByProductId(p.getId()))).collect(Collectors.toList());
    }


    public boolean addToFavorites(String userEmail, Long productId) throws ProductNotFoundException {
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(String.format(String.format("Не найдена публикация с id ", productId))));
        Optional<Favorites> favoritesOpt = favoritesRepository.findByUserAndProduct(user, product);
        if (favoritesOpt.isEmpty()) {
            favoritesRepository.save(Favorites.builder()
                    .user(user)
                    .product(product)
                    .build());
            return true;
        }
        return false;
    }


    public boolean removeFromFavorites(String userEmail, Long productId) throws ProductNotFoundException {
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(String.format(String.format("product with id %s was not found", productId))));
        Optional<Favorites> favoritesOpt = favoritesRepository.findByUserAndProduct(user, product);
        if (favoritesOpt.isPresent()) {
            favoritesRepository.delete(favoritesOpt.get());
            return true;
        }
        return false;
    }


    protected User findUserById(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public boolean deleteFavoritesByProductId(Long productId) {
        var products = favoritesRepository.getFavoritesByProductId(productId);
        if (!products.isEmpty()) {
            favoritesRepository.deleteAll(products);
            return true;
        }
        return false;
    }

    public void handleFavorites(FavoritesJson fav) throws ProductNotFoundException {
        String userEmail = fav.getUserEmail();
        Long productId = fav.getProductId();

        if (Boolean.parseBoolean(fav.getLiked())) {
            addToFavorites(userEmail, productId);
        } else {
            removeFromFavorites(userEmail, productId);
        }
    }
}

