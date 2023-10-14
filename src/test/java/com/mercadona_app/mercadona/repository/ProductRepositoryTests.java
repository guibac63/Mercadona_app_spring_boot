package com.mercadona_app.mercadona.repository;

import java.util.Calendar;
import java.util.Date;
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

import com.mercadona_app.mercadona.models.Category;
import com.mercadona_app.mercadona.models.Product;
import com.mercadona_app.mercadona.models.Promotion;

@ComponentScan(basePackages = "com.mercadona_app.mercadona.security")
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ProductRepositoryTests {
   @Autowired
   private ProductRepository productRepository;

   @Autowired
   private CategoryRepository categoryRepository;

   @Autowired
   private PromotionRepository promotionRepository;

 @Test
  @Transactional
  public void ProductRepository_SaveAll_ReturnSavedProduct() {
  
    Date beginningDate = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.set(2023, 12, 30, 59, 59, 59);
    Date endingDate = calendar.getTime();

    Promotion promotion = Promotion.builder()
              .promotionName("Promotion one")
              .beginningDate(beginningDate)
              .endingDate(endingDate)
              .promotionPercentage(10)
              .build();

    Promotion savedPromotion = promotionRepository.save(promotion);

    Category category = Category.builder().categoryName("Fruits").build();

    Category savedCategory = categoryRepository.save(category);

    Product product = Product.builder()
                      .productLibelle("Produit one")
                      .productDescription("Ceci est un produit")
                      .category(savedCategory)
                      .promotion(savedPromotion)
                      .productPrice(100.0)
                      .build();

    product.setProductDiscountedPrice((product.getProductPrice() - (promotion.getPromotionPercentage()/100)*product.getProductPrice()));                  
    
    Product savedProduct = productRepository.save(product);

    Assertions.assertThat(savedProduct).isNotNull();
    Assertions.assertThat(savedProduct.getId()).isGreaterThan(0);
  }

  @Test
  @Transactional
  public void ProductRepository_GetAll_ReturnMoreThanOneProduct() {
    
    Date beginningDate = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.set(2023, 12, 30, 59, 59, 59);
    Date endingDate = calendar.getTime();

    Promotion promotion = Promotion.builder()
              .promotionName("Promotion one")
              .beginningDate(beginningDate)
              .endingDate(endingDate)
              .promotionPercentage(10)
              .build();

    Promotion savedPromotion = promotionRepository.save(promotion);

    Category category = Category.builder().categoryName("Fruits").build();

    Category savedCategory = categoryRepository.save(category);

    Product product = Product.builder()
                      .productLibelle("Produit one")
                      .productDescription("Ceci est un produit")
                      .category(savedCategory)
                      .promotion(savedPromotion)
                      .productPrice(100.0)
                      .build();
    
    product.setProductDiscountedPrice((product.getProductPrice() - (promotion.getPromotionPercentage()/100)*product.getProductPrice()));                  
    
    Product product2 = Product.builder()
                      .productLibelle("Produit two")
                      .productDescription("Ceci est un deuxième produit")
                      .category(savedCategory)
                      .promotion(savedPromotion)
                      .productPrice(200.0)
                      .build();

    product2.setProductDiscountedPrice((product.getProductPrice() - (promotion.getPromotionPercentage()/100)*product2.getProductPrice()));                  

    productRepository.save(product);
    productRepository.save(product2);

    List<Product> productList = productRepository.findAll();

    Assertions.assertThat(productList).isNotNull();
    Assertions.assertThat(productList.size()).isEqualTo(2);

  }

  @Test
  @Transactional
  public void ProductRepository_UpdateProduct_ReturnProductNotNullAndExpectedName() {
    
    Date beginningDate = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.set(2023, 12, 30, 59, 59, 59);
    Date endingDate = calendar.getTime();

    Promotion promotion = Promotion.builder()
              .promotionName("Promotion one")
              .beginningDate(beginningDate)
              .endingDate(endingDate)
              .promotionPercentage(10)
              .build();

    Promotion savedPromotion = promotionRepository.save(promotion);

    Category category = Category.builder().categoryName("Fruits").build();

    Category savedCategory = categoryRepository.save(category);

    Product product = Product.builder()
                      .productLibelle("Produit one")
                      .productDescription("Ceci est un produit")
                      .category(savedCategory)
                      .promotion(savedPromotion)
                      .productPrice(100.0)
                      .build();

    Product savedProduct = productRepository.save(product);

    Product productSave = productRepository.findById(savedProduct.getId()).get();
    
    productSave.setProductLibelle("Nouveau produit one");

    Product updatedProduct = productRepository.save(productSave);

    Assertions.assertThat(updatedProduct.getProductLibelle()).isNotNull();
    Assertions.assertThat(updatedProduct.getProductLibelle()).isEqualTo("Nouveau produit one");
  }

  @Test
  @Transactional
  public void ProductRepository_ProductFindByPromotionId_ReturnProduct() {
    
    Date beginningDate = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.set(2023, 12, 30, 59, 59, 59);
    Date endingDate = calendar.getTime();

    Promotion promotion = Promotion.builder()
              .id(1)
              .promotionName("Promotion one")
              .beginningDate(beginningDate)
              .endingDate(endingDate)
              .promotionPercentage(10)
              .build();

    Promotion savedPromotion = promotionRepository.save(promotion);

    Category category = Category.builder().categoryName("Fruits").build();

    Category savedCategory = categoryRepository.save(category);

    Product product = Product.builder()
                      .productLibelle("Produit one")
                      .productDescription("Ceci est un produit")
                      .category(savedCategory)
                      .promotion(savedPromotion)
                      .productPrice(100.0)
                      .build();
    
    Product savedProduct = productRepository.save(product);

    List<Product> productListReturn = productRepository.findByPromotionId(savedPromotion.getId());

    Assertions.assertThat(productListReturn.get(0).getProductLibelle()).isEqualTo("Produit one");
  }

  @Test
  @Transactional
  public void ProductRepository_ProductDelete_ReturnProductIsEmpty() {
    
    Date beginningDate = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.set(2023, 12, 30, 59, 59, 59);
    Date endingDate = calendar.getTime();

    Promotion promotion = Promotion.builder()
              .promotionName("Promotion one")
              .beginningDate(beginningDate)
              .endingDate(endingDate)
              .promotionPercentage(10)
              .build();

    Promotion savedPromotion = promotionRepository.save(promotion);

    Category category = Category.builder().categoryName("Fruits").build();

    Category savedCategory = categoryRepository.save(category);

    Product product = Product.builder()
                      .productLibelle("Produit one")
                      .productDescription("Ceci est un produit")
                      .category(savedCategory)
                      .promotion(savedPromotion)
                      .productPrice(100.0)
                      .build();
    
    Product savedProduct = productRepository.save(product);

    productRepository.deleteById(savedProduct.getId());

    Optional<Product> productReturn = productRepository.findById(savedProduct.getId());

    Assertions.assertThat(productReturn).isEmpty();
  }

}
