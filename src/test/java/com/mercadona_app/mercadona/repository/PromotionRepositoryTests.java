package com.mercadona_app.mercadona.repository;


import java.text.SimpleDateFormat;
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

import com.mercadona_app.mercadona.models.Promotion;

@ComponentScan(basePackages = "com.mercadona_app.mercadona.security")
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PromotionRepositoryTests {
  
  @Autowired
  private PromotionRepository promotionRepository;

 @Test
  @Transactional
  public void PromotionRepository_SaveAll_ReturnSavedPromotion() {

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

    Assertions.assertThat(savedPromotion).isNotNull();
    Assertions.assertThat(savedPromotion.getId()).isGreaterThan(0);

  }

  @Test
  @Transactional
  public void PromotionRepository_GetAll_ReturnMoreThenOnePromotion() {

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

    promotionRepository.save(promotion);
    promotionRepository.save(promotion2);

    List<Promotion> promotionList = promotionRepository.findAll();

    Assertions.assertThat(promotionList).isNotNull();
    Assertions.assertThat(promotionList.size()).isEqualTo(2);
  }

  @Test
  @Transactional
  public void PromotionRepository_FindById_ReturnPromotion() {
  
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

    promotionRepository.save(promotion);

    Promotion savedPromotion = promotionRepository.findById(promotion.getId()).get();

    Assertions.assertThat(savedPromotion).isNotNull();
  }

  @Test
  @Transactional
  public void PromotionRepository_UpdatePromotion_ReturnPromotionNotNullAndExpectedName() {
  
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

    promotionRepository.save(promotion);

    Promotion savedPromotion = promotionRepository.findById(promotion.getId()).get();
    savedPromotion.setPromotionName("Promotion three");

    Promotion updatedPromotion = promotionRepository.save(savedPromotion);

    Assertions.assertThat(updatedPromotion.getPromotionName()).isNotNull();
    Assertions.assertThat(updatedPromotion.getPromotionName()).isEqualTo("Promotion three");
  }

  @Test
  @Transactional
  public void PromotionRepository_PromotionDelete_ReturnPromotionIsEmpty() {

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

    promotionRepository.save(promotion);

    promotionRepository.deleteById(promotion.getId());
    Optional<Promotion> promotionReturn = promotionRepository.findById(promotion.getId());

    Assertions.assertThat(promotionReturn).isEmpty();
  }
}
