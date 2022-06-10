package com.esdp.demo_esdp.repositories;

import com.esdp.demo_esdp.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Page<Category> findCategoriesByParentNull(Pageable pageable);

    Page<Category> findCategoriesByParentId(Long id, Pageable pageable);
}
