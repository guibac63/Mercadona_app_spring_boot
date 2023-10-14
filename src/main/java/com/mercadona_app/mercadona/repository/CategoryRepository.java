package com.mercadona_app.mercadona.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mercadona_app.mercadona.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> { 
}
