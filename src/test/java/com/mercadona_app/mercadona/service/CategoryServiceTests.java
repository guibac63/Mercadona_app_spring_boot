package com.mercadona_app.mercadona.service;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mercadona_app.mercadona.dto.AuthResponseDTO;
import com.mercadona_app.mercadona.models.Category;
import com.mercadona_app.mercadona.repository.CategoryRepository;
import com.mercadona_app.mercadona.services.CategoryService;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTests {

  @Mock
  private CategoryRepository categoryRepository;

  @InjectMocks
  private CategoryService categoryService;


  @Test
  public void CategoryService_CreateCategory_ReturnsCategory(){
    Category category = Category.builder()
                        .categoryName("Fruits")
                        .build();

    when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(category);

    categoryService.addNewCategory(category);

    Assertions.assertThat(category).isNotNull();

                  
  }

   @Test
  public void CategoryService_CreateCategory_ReturnsAllCategories(){

    List<Category> categoryList = new ArrayList<Category>();

    Category category = Category.builder()
                        .categoryName("Fruits")
                        .build();
    
    Category category1 = Category.builder()
                        .categoryName("Légumes")
                        .build();

    categoryList.add(category);
    categoryList.add(category1);

    when(categoryRepository.findAll()).thenReturn(categoryList);

    List<Category> savedCategoryList = categoryService.getAllCategories();

    Assertions.assertThat(savedCategoryList).isNotNull();
    assertEquals(2, savedCategoryList.size());
    verify(categoryRepository, times(1)).findAll();

                  
  }

  @Test
  public void CategoryService_CreateCategory_ReturnsCategoryById(){

    Category category = Category.builder()
                        .categoryName("Fruits")
                        .build();

    categoryRepository.save(category);

    when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));

    Category categoryServiceSaved = categoryService.getCategoryDetailsById(category.getId());

    Assertions.assertThat(categoryServiceSaved).isNotNull();
    Assertions.assertThat(categoryServiceSaved.getCategoryName()).isEqualTo("Fruits");
                  
  }

  @Test
  public void CategoryService_CreateCategory_ReturnsDeleteById(){

    int categoryId = 1;

    Category category = Category.builder()
                        .id(1)
                        .categoryName("Fruits")
                        .build();

    assertAll(() -> categoryService.deleteCategoryDetails(categoryId));
                  
  }

}
