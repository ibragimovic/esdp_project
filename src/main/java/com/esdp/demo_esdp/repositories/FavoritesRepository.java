package com.esdp.demo_esdp.repositories;

import com.esdp.demo_esdp.entity.Favorites;
import com.esdp.demo_esdp.entity.Product;
import com.esdp.demo_esdp.entity.User;
import com.esdp.demo_esdp.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoritesRepository extends JpaRepository<Favorites, Long> {
    Optional<Favorites> findByUserAndProduct(User user, Product product);


    @Query("select f from Favorites f where f.user.email = :email and f.product.status = :status order by f.id desc")
    List<Favorites> getFavoritesUser(@Param("email") String email,@Param("status") ProductStatus status);

    @Query("select f.product from Favorites f where f.user.email = :email and f.product.status = :status order by f.id desc")
    List<Product> getFavProducts(String email,ProductStatus status);

    @Query("select f from Favorites f where f.product.id = :id")
    List<Favorites> getFavoritesByProductId(@Param("id") Long id);

    @Query("select count(f) as fav_count from Favorites f where f.product.id = :id order by fav_count asc")
    Long getAmountOfLikes(Long id);
}
