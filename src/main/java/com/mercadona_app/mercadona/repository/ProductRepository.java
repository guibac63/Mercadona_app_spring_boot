package com.mercadona_app.mercadona.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mercadona_app.mercadona.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
   @Query("SELECT p FROM Product p WHERE p.promotion.id = :promotionId")
   List<Product> findByPromotionId(@Param("promotionId") int promotionId);
}
