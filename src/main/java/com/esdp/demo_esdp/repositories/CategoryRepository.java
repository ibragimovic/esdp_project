package com.esdp.demo_esdp.repositories;

import com.esdp.demo_esdp.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Page<Category> findCategoriesByParentNull(Pageable pageable);
    List<Category> findCategoriesByParentNull();

    Page<Category> findCategoriesByParentId(Long id, Pageable pageable);
    List<Category> findCategoriesByParentId(Long id);

    @Query("select c from Category c where c.id = :id ")
    Optional<Category> getCategory(@Param("id") Long id);

    @Query("select distinct cat.parent.id from Category cat where cat.parent.id is not null")
    List<Long> getCatParentId();

    Optional<Category> findCategoryByName(String name);

    void deleteCategoriesByParentId(Long parentId);
}
