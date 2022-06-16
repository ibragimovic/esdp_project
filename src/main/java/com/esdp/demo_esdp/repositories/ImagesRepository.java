package com.esdp.demo_esdp.repositories;

import com.esdp.demo_esdp.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ImagesRepository extends JpaRepository<Images,Long> {

    @Query("select i from Images i where i.product.id = :idProduct ")
    List<Images> getImagesProduct(@Param("idProduct") Long idProduct);


    @Query("select i.path from Images i where i.product.id = :id ")
    List<String> getProductImagePath(@Param("id") Long id);

    @Query("select i from Images i where i.id = :id ")
    Optional<Images> getImageId(@Param("id") Long id);
}
