package com.mercadona_app.mercadona.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mercadona_app.mercadona.models.Category;
import com.mercadona_app.mercadona.models.Product;
import com.mercadona_app.mercadona.models.Promotion;
import com.mercadona_app.mercadona.repository.CategoryRepository;
import com.mercadona_app.mercadona.repository.ProductRepository;
import com.mercadona_app.mercadona.repository.PromotionRepository;
import com.mercadona_app.mercadona.services.CategoryService;
import com.mercadona_app.mercadona.services.ProductService;
import com.mercadona_app.mercadona.services.PromotionService;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

  @Mock
  private PromotionRepository promotionRepository;

  @InjectMocks
  private PromotionService promotionService;

  @Mock
  private CategoryRepository categoryRepository;

  @InjectMocks
  private CategoryService categoryService;

  @Mock
  private ProductRepository productRepository;

  @InjectMocks
  private ProductService productService;
  
  @Test
  public void ProductService_CreatePromotion_ReturnsProduct() {
    
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
    
    
    when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

    Product savedProduct = productService.addNewProduct(product);

    Assertions.assertThat(savedProduct).isNotNull();

  }

  @Test
  public void ProductService_CreateProducts_ReturnsAllProducts() {

    List<Product> productList = new ArrayList<Product>();

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

    product.setProductDiscountedPrice(
        (product.getProductPrice() - (promotion.getPromotionPercentage() / 100) * product.getProductPrice()));

    Product product2 = Product.builder()
        .productLibelle("Produit two")
        .productDescription("Ceci est un deuxième produit")
        .category(savedCategory)
        .promotion(savedPromotion)
        .productPrice(200.0)
        .build();

    product2.setProductDiscountedPrice(
        (product.getProductPrice() - (promotion.getPromotionPercentage() / 100) * product2.getProductPrice()));

    productList.add(product);
    productList.add(product2);

    when(productRepository.findAll()).thenReturn(productList);

    List<Product> savedProductList = productService.getAllProducts();

    Assertions.assertThat(savedProductList).isNotNull();
    assertEquals(2, savedProductList.size());
    verify(productRepository, times(1)).findAll();

  }

  @Test
  public void ProductService_CreateProduct_ReturnsProductById() {

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
    

    productRepository.save(product);

    when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

    Product savedProduct = productService.getProductDetailsById(product.getId());

    Assertions.assertThat(savedProduct).isNotNull();
    Assertions.assertThat(savedProduct.getProductLibelle()).isEqualTo("Produit one");

  }

  @Test
  public void PromotionService_DeletePromotion_ReturnsDeleteById() {

    int productId = 1;
    
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
        .id(productId)
        .productLibelle("Produit one")
        .productDescription("Ceci est un produit")
        .category(savedCategory)
        .promotion(savedPromotion)
        .productPrice(100.0)
        .build();

    productRepository.save(product);

    assertAll(() -> productService.deleteProductDetails(productId));

  }

  @Test
  public void ProductService_SearchByPromotionId_ReturnsProduct() {

    List<Product> productList = new ArrayList<Product>();

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
    

    productRepository.save(product);

    productList.add(product);

    when(productRepository.findByPromotionId(promotion.getId())).thenReturn(productList);

    List<Product> productListReturn = productService.getProductsByPromotionId(promotion.getId());

    Assertions.assertThat(productListReturn.get(0).getProductLibelle()).isNotNull();
    Assertions.assertThat(productListReturn.get(0).getProductLibelle()).isEqualTo("Produit one");

  }
}
