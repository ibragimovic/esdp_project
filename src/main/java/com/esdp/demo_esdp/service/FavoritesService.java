package com.esdp.demo_esdp.service;

import com.esdp.demo_esdp.dto.FavoritesDTO;
import com.esdp.demo_esdp.dto.FavoritesJson;
import com.esdp.demo_esdp.entity.Favorites;
import com.esdp.demo_esdp.entity.Product;
import com.esdp.demo_esdp.entity.User;
import com.esdp.demo_esdp.enums.ProductStatus;
import com.esdp.demo_esdp.exception.ProductNotFoundException;
import com.esdp.demo_esdp.exception.UserNotFoundException;
import com.esdp.demo_esdp.repositories.FavoritesRepository;
import com.esdp.demo_esdp.repositories.ProductRepository;
import com.esdp.demo_esdp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoritesService {
    private final UserRepository userRepository;
    private final FavoritesRepository favoritesRepository;
    private final ProductRepository productRepository;


    public List<FavoritesDTO> getFavoritesUser(String email) {
        return favoritesRepository.getFavoritesUser(email, ProductStatus.ACCEPTED.name())
                .stream().map(FavoritesDTO::from).collect(Collectors.toList());
    }


    public void addToFavorites(Long userId, Product product) {
        User user = findUserById(userId);
        Optional<Favorites> favoritesOpt = favoritesRepository.findByUserAndProduct(user, product);
        if (favoritesOpt.isEmpty()) {
            favoritesRepository.save(Favorites.builder()
                    .user(user)
                    .product(product)
                    .build());
        }
    }
    public void addToFavorites(String userEmail,Long productId) throws ProductNotFoundException {
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Product product=productRepository.findById(productId)
                .orElseThrow(()->new ProductNotFoundException(String.format(String.format("product with id %s was not found",productId))));
        Optional<Favorites> favoritesOpt = favoritesRepository.findByUserAndProduct(user, product);
        if (favoritesOpt.isEmpty()) {
            favoritesRepository.save(Favorites.builder()
                    .user(user)
                    .product(product)
                    .build());
        }
    }

    public void removeFromFavorites(Long userId, Product product) {
        User user = findUserById(userId);
        Optional<Favorites> favoritesOpt = favoritesRepository.findByUserAndProduct(user, product);
        if (favoritesOpt.isPresent()) {
            favoritesRepository.delete(favoritesOpt.get());
        }
    }

    public void removeFromFavorites(String userEmail,Long productId) throws ProductNotFoundException {
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Product product=productRepository.findById(productId)
                .orElseThrow(()->new ProductNotFoundException(String.format(String.format("product with id %s was not found",productId))));
        Optional<Favorites> favoritesOpt = favoritesRepository.findByUserAndProduct(user, product);
        if (favoritesOpt.isPresent()) {
            favoritesRepository.delete(favoritesOpt.get());
        }
    }


    protected User findUserById(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public void deleteFavoritesByProductId(Long productId) {
        favoritesRepository.deleteAll(favoritesRepository.getFavoritesByProductId(productId));
    }

    public void handleFavorites(FavoritesJson fav) throws ProductNotFoundException {
        String userEmail=fav.getUserEmail();
        Long productId=fav.getProductId();

        if(Boolean.parseBoolean(fav.getLiked())){
            addToFavorites(userEmail,productId);
        }else{
            removeFromFavorites(userEmail,productId);
        }
    }
}

