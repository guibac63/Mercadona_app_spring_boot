package com.mercadona_app.mercadona.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mercadona_app.mercadona.models.Promotion;
import com.mercadona_app.mercadona.models.UserEntity;
import com.mercadona_app.mercadona.repository.PromotionRepository;
import com.mercadona_app.mercadona.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromotionService {
    
  @Autowired
  private PromotionRepository promotionRepository;

  @Autowired
  private UserRepository userRepository;
  
  public Promotion addNewPromotion(Promotion promotion){

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if(auth != null){
      String username = auth.getName();  
      Optional<UserEntity> userOptional = userRepository.findByUsername(username);
      UserEntity user = userOptional.get();
      promotion.setUserEntity(user);
    } 
    promotion.setWritingDate(new Date());
    return promotionRepository.save(promotion);
  }

  public List<Promotion> getAllPromotions() {
    return (List<Promotion>) promotionRepository.findAll();
  }

  public void deletePromotionDetails(Integer id) {
    promotionRepository.deleteById(id);
  }

  public Promotion getPromotionDetailsById(Integer id) {
    return promotionRepository.findById(id).get();
  }

}
