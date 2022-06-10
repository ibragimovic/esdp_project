package com.esdp.demo_esdp.service;

import com.esdp.demo_esdp.entity.Favorites;
import com.esdp.demo_esdp.entity.Product;
import com.esdp.demo_esdp.entity.User;
import com.esdp.demo_esdp.exeption.ResourceNotFoundException;
import com.esdp.demo_esdp.exeption.UserNotFoundException;
import com.esdp.demo_esdp.repositories.FavoritesRepository;
import com.esdp.demo_esdp.repositories.ProductRepository;
import com.esdp.demo_esdp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoritesService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final FavoritesRepository favoritesRepository;


    public void addToFavorites(Long userId, Long productId){
        User user=findUserById(userId);
        Product product=findProductById(productId);

        Optional<Favorites> favoritesOpt=favoritesRepository.findByUserAndProduct(user,product);
        if(favoritesOpt.isEmpty()){
            favoritesRepository.save(Favorites.builder()
                    .user(user)
                    .product(product)
                    .build());
        }
    }

    public void removeFromFavorites(Long userId,Long productId){
        User user=findUserById(userId);
        Product product=findProductById(productId);

        Optional<Favorites> favoritesOpt=favoritesRepository.findByUserAndProduct(user,product);
        if(favoritesOpt.isPresent()){
            favoritesRepository.delete(favoritesOpt.get());
        }
    }


    //this method will be moved to UserService later
    protected User findUserById(Long userId) throws UserNotFoundException{
        return userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException());
    }

    //this method will be moved to ProductService later
    protected Product findProductById(Long productId) throws ResourceNotFoundException{
        return productRepository.findById(productId).orElseThrow( ()->new ResourceNotFoundException());
    }

}

