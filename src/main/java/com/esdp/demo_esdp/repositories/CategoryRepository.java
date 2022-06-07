package com.esdp.demo_esdp.repositories;

import com.esdp.demo_esdp.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
