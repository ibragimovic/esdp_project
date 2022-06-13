package com.esdp.demo_esdp.repositories;

import com.esdp.demo_esdp.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Page<Category> findCategoriesByParentNull(Pageable pageable);

    Page<Category> findCategoriesByParentId(Long id, Pageable pageable);

    @Query("select c from Category c where c.id = :id ")
    Optional<Category> getCategory(@Param("id") Long id);
}
