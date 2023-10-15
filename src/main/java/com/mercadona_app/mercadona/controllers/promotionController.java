package com.mercadona_app.mercadona.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadona_app.mercadona.models.Product;
import com.mercadona_app.mercadona.models.Promotion;
import com.mercadona_app.mercadona.response.ResponseHandler;
import com.mercadona_app.mercadona.services.PromotionService;

@RestController
@RequestMapping("api/promotion")
public class promotionController {

  private static final Logger logInfo = LoggerFactory.getLogger(promotionController.class);

  
  @Autowired
  PromotionService promotionService;

  @PostMapping({"add"})
  public ResponseEntity<Object> addNewPromotion(@RequestBody Promotion promotion) {
    logInfo.info("add new Promotion");
    try{
      Promotion result = promotionService.addNewPromotion(promotion);
      logInfo.info("new Promotion added successfully");
      return ResponseHandler.generateResponse("Successfully added data!", HttpStatus.OK, result);
    } catch (Exception e) {
        return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
    }
  }

  @GetMapping({"getAll"})
  public ResponseEntity<Object> getAllPromotions(){
    logInfo.info("get all Promotions");
    try{
      List<Promotion> result = promotionService.getAllPromotions();
      logInfo.info("retrieve all Promotions successfully");
      return ResponseHandler.generateResponse("Successfully retrieved data!", HttpStatus.OK, result);
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
    }
  }

  @GetMapping({"get/{id}"})
  public Promotion getPromotionById(@PathVariable("id") Integer id){
    logInfo.info("get one promotion by id");
    return promotionService.getPromotionDetailsById(id);
  }

  @DeleteMapping({"delete/{id}"})
  public ResponseEntity<Object> deletePromotionDetails(@PathVariable("id") Integer id) {
    logInfo.info("delete promotion by id");
    try {
      promotionService.deletePromotionDetails(id);
      logInfo.info("promotion successfully deleted");
      return ResponseHandler.generateResponse("Successfully removed data!", HttpStatus.OK, null);
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
    }
  }

}
