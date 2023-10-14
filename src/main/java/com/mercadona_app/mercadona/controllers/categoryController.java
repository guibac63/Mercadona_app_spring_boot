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

import com.mercadona_app.mercadona.models.Category;
import com.mercadona_app.mercadona.response.ResponseHandler;
import com.mercadona_app.mercadona.services.CategoryService;


@RestController
@RequestMapping("api/category")
public class categoryController {
  
  @Autowired
  CategoryService categoryService;


  @PostMapping({"add"})
  public ResponseEntity<Object> addNewCategory(@RequestBody Category category) {
    try{
      Category result = categoryService.addNewCategory(category);
      return ResponseHandler.generateResponse("Successfully added data!", HttpStatus.OK, result);
    } catch (Exception e) {
        return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
    }
  }

  @GetMapping({"getAll"})
  public ResponseEntity<Object> getAllCategories(){
    try{
      List<Category> result = categoryService.getAllCategories();
      return ResponseHandler.generateResponse("Successfully retrieved data!", HttpStatus.OK, result);
    }catch(Exception e){
      return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
    }
  }

  @GetMapping({"get/{id}"})
  public Category getCategoryById(@PathVariable("id") Integer id){
    return categoryService.getCategoryDetailsById(id);
  }

  @DeleteMapping({"delete/{id}"})
  public ResponseEntity<Object> deleteCategoryDetails(@PathVariable("id") Integer id) {
    try{
      categoryService.deleteCategoryDetails(id);
      return ResponseHandler.generateResponse("Successfully removed data!", HttpStatus.OK, null);
    }catch(Exception e){
      return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
    }
  }

}
