package com.online.store.repository;

import com.online.store.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long>, PagingAndSortingRepository<Products, Long> {
    Optional<Products> findProductsByProductId(Long productId);

    @Query(value = "SELECT * FROM Products WHERE product_name = '" + ":productName" + "'", nativeQuery = true)
    List<Products> findProductsByProductName(String productName);
}
