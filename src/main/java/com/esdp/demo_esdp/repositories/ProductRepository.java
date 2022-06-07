package com.esdp.demo_esdp.repositories;

import com.esdp.demo_esdp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
