package com.esdp.demo_esdp.repositories;

import com.esdp.demo_esdp.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("select p from Product p where lower(p.name) like  %:name% ")
    Page<Product> getProductName(@Param("name") String name, Pageable pageable);

    @Query("select p from Product p where lower(p.category.name) like %:category%")
    Page<Product> getProductCategory(@Param("category") String category, Pageable pageable);

    @Query("select p from Product p where p.price >= :from and p.price <= :before")
    Page<Product> getProductPrice(@Param("from") Integer from, @Param("before") Integer before, Pageable pageable);

    @Query("select p from Product p where p.user.email = :email ")
    List<Product> getProductUser(@Param("email") String email);
}
