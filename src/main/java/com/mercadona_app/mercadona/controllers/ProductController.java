package com.mercadona_app.mercadona.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mercadona_app.mercadona.models.Image;
import com.mercadona_app.mercadona.models.Product;
import com.mercadona_app.mercadona.models.Promotion;
import com.mercadona_app.mercadona.response.ResponseHandler;
import com.mercadona_app.mercadona.services.CloudinaryImageService;
import com.mercadona_app.mercadona.services.ProductService;
import com.mercadona_app.mercadona.services.PromotionService;

// import java.util.Map;

@RestController
@RequestMapping("api/product")
public class ProductController {

  private static final Logger logInfo = LoggerFactory.getLogger(ProductController.class);

  @Autowired
  private ProductService productService;

  @Autowired
  private PromotionService promotionService;

  @Autowired
  private CloudinaryImageService cloudinaryImageService;


  @PostMapping(value = {"add"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<Object> addNewProduct(@RequestPart("product") Product product, @RequestPart(name = "imageFile", required = false) MultipartFile file) {
    logInfo.info("add new Product"); 
    try {
      if(file != null){
        logInfo.info("Uploading image"); 
        Image image = uploadImage(file);
        product.setImage(image);
        logInfo.info("image successfully uploaded");
      }
      Product result = productService.addNewProduct(product);
      logInfo.info("new Product added successfully"); 
      return ResponseHandler.generateResponse("Successfully added data!", HttpStatus.OK, result);
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
    }
  }

  public Image uploadImage(MultipartFile file) throws IOException {
      if(file != null){
        Map result = this.cloudinaryImageService.upload(file);       
        Image image = new Image();
        image.setImageName((String)result.get("original_filename"));
        image.setImageUrl((String)result.get("secure_url"));
        image.setImageId((String)result.get("public_id"));
        return image;
      }else{
        return null;
      }
  }

  @GetMapping({"getAll"})
  public ResponseEntity<Object> getAllProducts(){
    try{
      logInfo.info("get all Products");
      List<Product> result = productService.getAllProducts();
      logInfo.info("retrieve all Products successfully");
      return ResponseHandler.generateResponse("Successfully retrieved data!", HttpStatus.OK, result);
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
    }
  }

  @GetMapping({"get/{id}"})
  public Product getProductById(@PathVariable("id") Integer id){
    logInfo.info("get one product by id");
    return productService.getProductDetailsById(id);
  }

  @DeleteMapping({"delete/{id}"})
  public ResponseEntity<Object> deleteProductDetails(@PathVariable("id") Integer id) {
    logInfo.info("delete product by id");
    try {
      productService.deleteProductDetails(id);
      logInfo.info("product successfully deleted");
      return ResponseHandler.generateResponse("Successfully removed data!", HttpStatus.OK, null);
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
    }    
  }

  @GetMapping({"modifyProductDiscountedPrice/{id}"})
  public ResponseEntity<Object> modifyProductDiscountedPrice(@PathVariable("id") Integer id){
    logInfo.info("modifying price of products after changing promotion percentage");
    Promotion promotion = promotionService.getPromotionDetailsById(id);
    List<Product> products =  productService.getProductsByPromotionId(id);
    try {
      for (Product product : products) {
        Double discountedPrice = product.getProductPrice() * (100 - promotion.getPromotionPercentage())/100;
        product.setProductDiscountedPrice(Math.round(discountedPrice * 100.0) / 100.0);
        productService.addNewProduct(product);
      }
      logInfo.info("Discounted Products prices Successfully updated!");
      return ResponseHandler.generateResponse("Discounted Products prices Successfully updated!", HttpStatus.OK, null);
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
    }

  }

}
