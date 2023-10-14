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
import com.mercadona_app.mercadona.controllers.promotionController;
import com.mercadona_app.mercadona.models.Promotion;
import com.mercadona_app.mercadona.services.PromotionService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.assertj.core.util.Lists;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;

@WebMvcTest(controllers = promotionController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PromotionControllerTest {
  
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PromotionService promotionService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void PromotionController_CreatePromotion_ReturnCreated() throws Exception {

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

    when(promotionService.addNewPromotion(ArgumentMatchers.any())).thenAnswer((invocation -> invocation.getArgument(0)));

    ResultActions response = mockMvc.perform(post("/api/promotion/add")
        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(promotion)));

    response.andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.promotionName", CoreMatchers.is(promotion.getPromotionName())))
        .andDo(MockMvcResultHandlers.print());

  }

  @Test
  public void PromotionController_GetAllPromotions_ReturnAll() throws Exception {

    List<Promotion> promotionList = new ArrayList<Promotion>();

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

    Calendar calendar2 = Calendar.getInstance();
    calendar2.set(2023, 12, 10, 59, 59, 59);
    Date endingDate2 = calendar.getTime();

    Promotion promotion2 = Promotion.builder()
        .promotionName("Promotion two")
        .beginningDate(beginningDate)
        .endingDate(endingDate2)
        .promotionPercentage(25)
        .build();

    promotionList.add(promotion);
    promotionList.add(promotion2);

    when(promotionService.getAllPromotions()).thenReturn(promotionList);

    ResultActions response = mockMvc.perform(get("/api/promotion/getAll")
        .contentType(org.springframework.http.MediaType.APPLICATION_JSON));

    List<String> verifList = Lists.newArrayList("Promotion one", "Promotion two");

    response.andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.[*].promotionName", CoreMatchers.equalTo(verifList)));

  }

  @Test
  public void PromotionController_GetPromotionById_ReturnPromotion() throws Exception {

    int categoryId = 1;

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

    when(promotionService.getPromotionDetailsById(categoryId)).thenReturn(promotion);

    ResultActions response = mockMvc.perform(get("/api/promotion/get/1")
        .contentType(org.springframework.http.MediaType.APPLICATION_JSON));

    response.andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.promotionName", CoreMatchers.is(promotion.getPromotionName())));

  }

  @Test
  public void PromotionController_DeletePromotionById() throws Exception {

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

    ResultActions response = mockMvc.perform(delete("/api/promotion/delete/1")
        .contentType(org.springframework.http.MediaType.APPLICATION_JSON));

    response.andExpect(MockMvcResultMatchers.status().isOk());

  }






}
