package com.mercadona_app.mercadona.controllers;

import java.util.List;

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
  
  @Autowired
  PromotionService promotionService;

  @PostMapping({"add"})
  public ResponseEntity<Object> addNewPromotion(@RequestBody Promotion promotion) {
    try{
      Promotion result = promotionService.addNewPromotion(promotion);
      return ResponseHandler.generateResponse("Successfully added data!", HttpStatus.OK, result);
    } catch (Exception e) {
        return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
    }
  }

  @GetMapping({"getAll"})
  public ResponseEntity<Object> getAllPromotions(){
    try{
      List<Promotion> result = promotionService.getAllPromotions();
      return ResponseHandler.generateResponse("Successfully retrieved data!", HttpStatus.OK, result);
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
    }
  }

  @GetMapping({"get/{id}"})
  public Promotion getPromotionById(@PathVariable("id") Integer id){
    return promotionService.getPromotionDetailsById(id);
  }

  @DeleteMapping({"delete/{id}"})
  public ResponseEntity<Object> deletePromotionDetails(@PathVariable("id") Integer id) {
    try {
      promotionService.deletePromotionDetails(id);
      return ResponseHandler.generateResponse("Successfully removed data!", HttpStatus.OK, null);
    } catch (Exception e) {
      return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
    }
  }

}
