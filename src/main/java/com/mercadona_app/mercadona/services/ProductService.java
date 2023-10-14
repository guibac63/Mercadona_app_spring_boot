package com.mercadona_app.mercadona.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mercadona_app.mercadona.models.Product;
import com.mercadona_app.mercadona.models.UserEntity;
import com.mercadona_app.mercadona.repository.ProductRepository;
import com.mercadona_app.mercadona.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductService {
  
  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private UserRepository userRepository;

  public Product addNewProduct(Product product) {
      
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      String username = auth.getName();
      Optional<UserEntity> userOptional = userRepository.findByUsername(username);
      UserEntity user = userOptional.get();
      product.setUserEntity(user);
    }
    product.setWritingDate(new Date());
    return productRepository.save(product);
  }

  public List<Product> getAllProducts() {
    return (List<Product>) productRepository.findAll();
  }

  public void deleteProductDetails(Integer id) {
    productRepository.deleteById(id);
  }

  public List<Product> getProductsByPromotionId(Integer id) {
    return (List<Product>) productRepository.findByPromotionId(id);
  }

  public Product getProductDetailsById(Integer id) {
    return productRepository.findById(id).get();
  }

}
