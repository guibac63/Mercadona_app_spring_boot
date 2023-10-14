package com.mercadona_app.mercadona.controller;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadona_app.mercadona.controllers.categoryController;
import com.mercadona_app.mercadona.models.Category;
import com.mercadona_app.mercadona.services.CategoryService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.assertj.core.util.Lists;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.CoreMatchers;

@WebMvcTest(controllers = categoryController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {
  
  @Autowired
  private MockMvc mockMvc;
  
  @MockBean
  private CategoryService categoryService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void CategoryController_CreateCategory_ReturnCreated() throws Exception {
    
    Category category = Category.builder().categoryName("Fruits").build();

    when(categoryService.addNewCategory(ArgumentMatchers.any())).thenAnswer((invocation -> invocation.getArgument(0)));

    ResultActions response = mockMvc.perform(post("/api/category/add")
                            .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(category)));
  

    response.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.categoryName",CoreMatchers.is(category.getCategoryName())))
            .andDo(MockMvcResultHandlers.print());

  }

  @Test
  public void CategoryController_GetAllCategories_ReturnAll() throws Exception {
    
    List<Category> categoryList = new ArrayList<Category>();

    Category category = Category.builder().categoryName("Fruits").build();
    Category category1 = Category.builder().categoryName("Légumes").build();

    categoryList.add(category);
    categoryList.add(category1);

    when(categoryService.getAllCategories()).thenReturn(categoryList);

    ResultActions response = mockMvc.perform(get("/api/category/getAll")
                            .contentType(org.springframework.http.MediaType.APPLICATION_JSON));

    List <String> verifList = Lists.newArrayList("Fruits","Légumes");

    response.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.[*].categoryName",CoreMatchers.equalTo(verifList)));

  }

  @Test
  public void CategoryController_GetCategoryById_ReturnCategory() throws Exception {
    
    int categoryId = 1;

    Category category = Category.builder().categoryName("Fruits").build();

    when(categoryService.getCategoryDetailsById(categoryId)).thenReturn(category);

    ResultActions response = mockMvc.perform(get("/api/category/get/1")
                            .contentType(org.springframework.http.MediaType.APPLICATION_JSON));

    response.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.categoryName",CoreMatchers.is(category.getCategoryName())));

  }

  @Test
  public void CategoryController_DeleteCategoryById() throws Exception {
    
    Category category = Category.builder().categoryName("Fruits").build();

    ResultActions response = mockMvc.perform(delete("/api/category/delete/1")
                            .contentType(org.springframework.http.MediaType.APPLICATION_JSON));

    response.andExpect(MockMvcResultMatchers.status().isOk());

  }



}
