package com.esdp.demo_esdp.repositories;

import com.esdp.demo_esdp.entity.Favorites;
import com.esdp.demo_esdp.entity.Product;
import com.esdp.demo_esdp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoritesRepository extends JpaRepository<Favorites,Long> {
    Optional<Favorites> findByUserAndProduct(User user, Product product);
}
