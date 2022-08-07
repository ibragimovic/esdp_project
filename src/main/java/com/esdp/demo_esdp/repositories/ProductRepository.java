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

    @Query("select p from Product p where lower(p.name) like  %:name% or lower(p.description) like %:name% and p.status = :status order by p.dateAdd desc")
    Page<Product> getProductListByName(@Param("name") String name, @Param("status") ProductStatus status, Pageable pageable);

    @Query("select p from Product p where p.price >= :from and p.price <= :before and p.status = :status")
    Page<Product> getProductPrice(@Param("from") Integer from, @Param("before") Integer before, @Param("status") ProductStatus status, Pageable pageable);

    @Query("select p from Product p where p.status = :status order by p.dateAdd desc")
    Page<Product> getProducts(@Param("status") ProductStatus status, Pageable pageable);

    @Query("select p from Product p where p.status = :status order by p.dateAdd desc")
    List<Product> getProductsList(@Param("status") ProductStatus status);

    @Query("select p.user.email from Product p where p.id = :id ")
    String getPublicationUserEmail(@Param("id") Long id);

    @Query("select p from Product p where p.category = :categoryId and p.status = :status order by p.dateAdd")
    Page<Product> findProductsByCategoryId(Long categoryId, @Param("status") ProductStatus status, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "update products  set status = :status where id = :id", nativeQuery = true)
    void updateProductStatus(@Param("status") String status, @Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "update products  set category_id = :category where id = :id", nativeQuery = true)
    void updateProductCategory(@Param("category") Long category, @Param("id") Long id);

    @Query("select p from Product p where p.user.email = :email")
    Page<Product> getProductsUser(@Param("email") String email, Pageable pageable);


    @Query("select p from Product p where p.status = :status")
    Page<Product> getProductsStatus(@Param("status") ProductStatus status, Pageable pageable);

    @Query("select p from Product p where p.category.id = :id")
    Page<Product> getProductsCategory(@Param("id") Long id, Pageable pageable);

    @Query("select p from Product p where lower(p.name) like  %:name%")
    Page<Product> getProductsName(@Param("name") String name, Pageable pageable);

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

    List<Product> findProductsByCategoryIdAndStatus(Long id, ProductStatus status);

    @Query("select p from Product p where p.category.id = :catId and not (p.id= :productId) and p.status=:productStatus ")
    List<Product> getSimilarProducts(Long catId, Long productId, ProductStatus productStatus);

    @Query("select p from Product p " +
            "where upper(p.status) = :status and p.category.id = :categoryId and " +
            "(lower(p.name) like %:search% or lower(p.description) like %:search%) and " +
            "(p.price between :priceFrom  and :priceTo) and " +
            "lower(p.localities) like %:locality% " +
            "order by p.price asc ")
    Page<Product> getCheapProducts(@Param("status") ProductStatus status, @Param("search") String search,
                                   @Param("priceFrom") Integer priceFrom, @Param("priceTo") Integer priceTo,
                                   @Param("locality") String locality, @Param("categoryId") Long categoryId,
                                   Pageable pageable);

    @Query("select p from Product p " +
            "where upper(p.status) = :status and p.category.id = :categoryId and " +
            "(lower(p.name) like %:search% or lower(p.description) like %:search%) and " +
            "(p.price between :priceFrom  and :priceTo) and " +
            "lower(p.localities) like %:locality% " +
            "order by p.price desc ")
    Page<Product> getExpensiveProducts(@Param("status") ProductStatus status, @Param("search") String search,
                                       @Param("priceFrom") Integer priceFrom, @Param("priceTo") Integer priceTo,
                                       @Param("locality") String locality, @Param("categoryId") Long categoryId,
                                       Pageable pageable);


    @Query("select p from Product p " +
            "where upper(p.status) = :status and p.category.id = :categoryId and " +
            "(lower(p.name) like %:search% or lower(p.description) like %:search%) and " +
            "(p.price between :priceFrom  and :priceTo) and " +
            "lower(p.localities) like %:locality% " +
            "order by p.dateAdd desc ")
    Page<Product> getNewProducts(@Param("status") ProductStatus status, @Param("search") String search,
                                 @Param("priceFrom") Integer priceFrom, @Param("priceTo") Integer priceTo,
                                 @Param("locality") String locality, @Param("categoryId") Long categoryId,
                                 Pageable pageable);

    @Query("select p from Product p " +
            "left join Favorites f on p.id = f.product.id " +
            "where p.status = :status and p.category.id = :categoryId and " +
            "(lower(p.name) like %:search% or lower(p.description) like %:search%) and " +
            "(p.price between :priceFrom and :priceTo) and " +
            "lower(p.localities) like %:locality% " +
            "group by p.id order by count(f.product.id) desc")
    Page<Product> getFamousProducts(@Param("status") ProductStatus status, @Param("search") String search,
                                    @Param("priceFrom") Integer priceFrom, @Param("priceTo") Integer priceTo,
                                    @Param("locality") String locality, @Param("categoryId") Long categoryId,
                                    Pageable pageable);

    @Query("select p from Product p where lower(p.user.email) =  :userEmail and p.status = :status order by p.dateAdd desc")
    List<Product> getUserProductsByStatus(String userEmail, ProductStatus status);

}
