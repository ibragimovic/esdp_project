package com.esdp.demo_esdp.repositories;

import com.esdp.demo_esdp.entity.Product;
import com.esdp.demo_esdp.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where lower(p.name) like  %:name% and p.status = :status ")
    Page<Product> getProductName(@Param("name") String name, @Param("status") ProductStatus status, Pageable pageable);

    @Query("select p from Product p where lower(p.category.name) like %:category% and p.status = :status")
    Page<Product> getProductCategory(@Param("category") String category, @Param("status") ProductStatus status, Pageable pageable);

    @Query("select p from Product p where p.price >= :from and p.price <= :before and p.status = :status")
    Page<Product> getProductPrice(@Param("from") Integer from, @Param("before") Integer before, @Param("status") ProductStatus status, Pageable pageable);

    @Query("select p from Product p where p.user.email = :email and p.status = :status")
    List<Product> getProductUser(@Param("email") String email, @Param("status") ProductStatus status);

    @Query("select p from Product p where p.status = :status order by p.dateAdd")
    Page<Product> getProducts(@Param("status") ProductStatus status, Pageable pageable);

    @Query("select p.user.email from Product p where p.id = :id ")
    String getPublicationUserEmail(@Param("id") Long id);

    @Query("select p from Product p where p.category = :categoryId and p.status = :status order by p.dateAdd")
    Page<Product> findProductsByCategoryId(Long categoryId,@Param("status") ProductStatus status, Pageable pageable);


    @Transactional
    @Modifying
    @Query(value = "update products  set status = :status where id = :id", nativeQuery = true)
    void updateProductStatus(@Param("status") String status, @Param("id") Long id);


    @Transactional
    @Modifying
    @Query(value = "update products  set category_id = :category where id = :id", nativeQuery = true)
    void updateProductCategory(@Param("category") Long category, @Param("id") Long id);

    @Query("select p from Product p where p.user.email = :email")
    List<Product> getProductsUser(@Param("email") String email);


    @Query("select p from Product p where p.status = :status")
    List<Product> getProductsStatus(@Param("status") ProductStatus status);

    @Query("select p from Product p where p.category.id = :id")
    List<Product> getProductsCategory(@Param("id") Long id);

    @Query("select p from Product p where lower(p.name) like  %:name%")
    List<Product> getProductsName(@Param("name") String name);

    @Query("select p from Product p where p.endOfPayment>=current_timestamp and p.status = :status")
    Page<Product> findTopProduct(@Param("status") ProductStatus status,Pageable pageable);

    @Query("select p from Product p where p.status = :status and p.endOfPayment <= current_timestamp order by p.up")
    Page<Product> getProductsToMainPage(@Param("status") ProductStatus status, Pageable pageable);


    @Query(value = "select p from Product p where p.category.id =:id or p.category.parent.id = :id")
    List<Product> findProductsByCategory(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "update products  set end_of_payment = :date where id = :id", nativeQuery = true)
    void updateProductEndOfPayment(@Param("date") LocalDateTime date, @Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "update products  set up_to_top = :date where id = :id", nativeQuery = true)
    void updateProductUpToTop(@Param("date") LocalDateTime date, @Param("id") Long id);

    @Query("select p from Product p where p.category.id = :catId and not (p.id= :productId)")
    List<Product> getSimilarProducts(Long catId, Long productId);

    @Query("select p from Product p where p.category.id = :catId")
    List<Product> getSimilarProducts(Long catId);

}
