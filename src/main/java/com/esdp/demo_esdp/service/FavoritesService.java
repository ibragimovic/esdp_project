package com.esdp.demo_esdp.service;

import com.esdp.demo_esdp.dto.FavoritesDTO;
import com.esdp.demo_esdp.entity.Favorites;
import com.esdp.demo_esdp.entity.Product;
import com.esdp.demo_esdp.entity.User;
import com.esdp.demo_esdp.enums.ProductStatus;
import com.esdp.demo_esdp.exception.UserNotFoundException;
import com.esdp.demo_esdp.repositories.FavoritesRepository;
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
    private final ProductService productService;


    public List<FavoritesDTO> getFavoritesUser(String email) {
        return favoritesRepository.getFavoritesUser(email, ProductStatus.ACCEPTED.name())
                .stream().map(FavoritesDTO::from).collect(Collectors.toList());
    }


    public void addToFavorites(Long userId, Long productId) {
        User user = findUserById(userId);
        Product product = productService.findProductById(productId);

        Optional<Favorites> favoritesOpt = favoritesRepository.findByUserAndProduct(user, product);
        if (favoritesOpt.isEmpty()) {
            favoritesRepository.save(Favorites.builder()
                    .user(user)
                    .product(product)
                    .build());
        }
    }

    public void removeFromFavorites(Long userId, Long productId) {
        User user = findUserById(userId);
        Product product = productService.findProductById(productId);

        Optional<Favorites> favoritesOpt = favoritesRepository.findByUserAndProduct(user, product);
        if (favoritesOpt.isPresent()) {
            favoritesRepository.delete(favoritesOpt.get());
        }
    }


    //this method will be moved to UserService later
    protected User findUserById(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
    }

    public void deleteFavoritesByProductId(Long productId) {
        favoritesRepository.deleteAll(favoritesRepository.getFavoritesByProductId(productId));
    }

}

