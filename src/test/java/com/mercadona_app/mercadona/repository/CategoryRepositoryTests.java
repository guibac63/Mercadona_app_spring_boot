package com.mercadona_app.mercadona.repository;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;

import com.mercadona_app.mercadona.dto.AuthResponseDTO;
import com.mercadona_app.mercadona.models.Category;

@ComponentScan(basePackages = "com.mercadona_app.mercadona.security")
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CategoryRepositoryTests {
  
  @Autowired
  private CategoryRepository categoryRepository;

 @Test
  @Transactional
  public void CategoryRepository_SaveAll_ReturnSavedCategory() {

  Category category = Category.builder().categoryName("Fruits").build();

  Category savedCategory = categoryRepository.save(category);

  Assertions.assertThat(savedCategory).isNotNull();
  Assertions.assertThat(savedCategory.getId()).isGreaterThan(0);

  }

  @Test
  @Transactional
  public void CategoryRepository_GetAll_ReturnMoreThenOneCategory() {
  Category category = Category.builder()
  .categoryName("Fruits").build();
  Category category2 = Category.builder()
  .categoryName("Légumes").build();

  categoryRepository.save(category);
  categoryRepository.save(category2);

  List<Category> categoryList = categoryRepository.findAll();

  Assertions.assertThat(categoryList).isNotNull();
  Assertions.assertThat(categoryList.size()).isEqualTo(2);
  }

  @Test
  @Transactional
  public void CategoryRepository_FindById_ReturnCategory() {
  Category category = Category.builder()
  .categoryName("Fruits").build();

  categoryRepository.save(category);

  Category categorySave = categoryRepository.findById(category.getId()).get();

  Assertions.assertThat(categorySave).isNotNull();
  }

  @Test
  @Transactional
  public void CategoryRepository_UpdateCategory_ReturnCategoryNotNullAndExpectedName() {
  Category category = Category.builder()
  .categoryName("Fruits").build();

  categoryRepository.save(category);

  Category categorySave = categoryRepository.findById(category.getId()).get();
  categorySave.setCategoryName("Viande");

  Category updatedCategory = categoryRepository.save(categorySave);

  Assertions.assertThat(updatedCategory.getCategoryName()).isNotNull();
  Assertions.assertThat(updatedCategory.getCategoryName()).isEqualTo("Viande");
  }

  @Test
  @Transactional
  public void CategoryRepository_CategoryDelete_ReturnCategoryIsEmpty() {
  Category category = Category.builder()
  .categoryName("Fruits").build();

  categoryRepository.save(category);

  categoryRepository.deleteById(category.getId());
  Optional<Category> categoryReturn = categoryRepository.findById(category.getId());

  Assertions.assertThat(categoryReturn).isEmpty();
  }

  @Test
  public void testDefaultConstructor() {
    AuthResponseDTO authResponseDTO = new AuthResponseDTO();

    assertNull(authResponseDTO.getAccessToken());
    assertEquals("Bearer ", authResponseDTO.getTokenType());
  }

  @Test
  public void testParameterizedConstructor() {
    String accessToken = "myAccessToken";
    AuthResponseDTO authResponseDTO = new AuthResponseDTO(accessToken);

    assertEquals(accessToken, authResponseDTO.getAccessToken());
    assertEquals("Bearer ", authResponseDTO.getTokenType());
  }
}
