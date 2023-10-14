package com.mercadona_app.mercadona.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.tomcat.util.http.parser.MediaType;
import org.assertj.core.util.Lists;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadona_app.mercadona.controllers.ProductController;
import com.mercadona_app.mercadona.models.Category;
import com.mercadona_app.mercadona.models.Product;
import com.mercadona_app.mercadona.models.Promotion;
import com.mercadona_app.mercadona.repository.CategoryRepository;
import com.mercadona_app.mercadona.repository.ProductRepository;
import com.mercadona_app.mercadona.repository.PromotionRepository;
import com.mercadona_app.mercadona.services.CategoryService;
import com.mercadona_app.mercadona.services.ProductService;
import com.mercadona_app.mercadona.services.PromotionService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ProductControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PromotionService promotionService;

  @MockBean
  private ProductService productService;

  @MockBean
  private CategoryService categoryService;

  @MockBean
  private CategoryRepository categoryRepository;

  @MockBean
  private PromotionRepository promotionRepository;

  @MockBean
  private ProductRepository productRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void ProductController_CreateProduct_ReturnCreated() throws Exception {

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

    // Promotion savedPromotion = promotionRepository.save(promotion);

    Category category = Category.builder().categoryName("Fruits").build();

    // Category savedCategory = categoryRepository.save(category);

    Product product = Product.builder()
                      .productLibelle("Produit one")
                      .productDescription("Ceci est un produit")
                      .category(category)
                      .promotion(promotion)
                      .productPrice(100.0)
                      .build();

    product.setProductDiscountedPrice((product.getProductPrice() - (promotion.getPromotionPercentage()/100)*product.getProductPrice()));                  

    // Product savedProduct = productRepository.save(product);

    when(productService.addNewProduct(ArgumentMatchers.any())).thenAnswer((invocation -> invocation.getArgument(0)));

    MockMultipartFile imageFilePart = new MockMultipartFile(
        "imageFile",
        "test.jpg",
        org.springframework.http.MediaType.IMAGE_JPEG_VALUE,
        "content".getBytes());

    MockMultipartFile metadata = new MockMultipartFile(
        "product",
        "product",
        org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
        objectMapper.writeValueAsBytes(product));

    // MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    ResultActions response = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/product/add")
        .file(imageFilePart)
        .file(metadata)
        .accept(org.springframework.http.MediaType.APPLICATION_JSON));
        
    response.andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.productLibelle", CoreMatchers.is(product.getProductLibelle())))
        .andDo(MockMvcResultHandlers.print());

  }

  @Test
  public void ProductController_GetAllProducts_ReturnAll() throws Exception {

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

    when(productService.getAllProducts()).thenReturn(productList);

    ResultActions response = mockMvc.perform(get("/api/product/getAll")
        .contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE));

    List<String> verifList = Lists.newArrayList("Produit one", "Produit two");

    response.andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.[*].productLibelle", CoreMatchers.equalTo(verifList)));

  }

  @Test
  public void ProductController_GetProductById_ReturnProduct() throws Exception {

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

    when(productService.getProductDetailsById(productId)).thenReturn(product);

    ResultActions response = mockMvc.perform(get("/api/product/get/1")
        .contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE));

    response.andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.productLibelle", CoreMatchers.is(product.getProductLibelle())));

  }

  @Test
  public void ProductController_DeleteProductById() throws Exception {

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

    ResultActions response = mockMvc.perform(delete("/api/product/delete/1")
        .contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE));

    response.andExpect(MockMvcResultMatchers.status().isOk());

  }

  @Test
  public void ProductController_GetProductByPromotionId_ReturnProduct() throws Exception {

    List<Product> productList = new ArrayList<Product>();

    int promotionId = 1;

    Date beginningDate = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.set(2023, 12, 30, 59, 59, 59);
    Date endingDate = calendar.getTime();

    Promotion promotion = Promotion.builder()
        .id(promotionId)
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
        .promotion(promotion)
        .productPrice(100.0)
        .build();

    productRepository.save(product);

    productList.add(product);

    when(promotionService.getPromotionDetailsById(promotionId)).thenReturn(promotion);
    when(productService.getProductsByPromotionId(promotionId)).thenReturn(productList);

    ResultActions response = mockMvc.perform(get("/api/product/modifyProductDiscountedPrice/1")
        .contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE));

    response.andExpect(MockMvcResultMatchers.status().isOk());

  }





  
}
