package com.mercadona_app.mercadona.models;

import java.util.Date;

import javax.persistence.*;

import org.apache.tomcat.jni.Address;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
  
  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private int id;

  private String productLibelle;

  private String productDescription;

  private Double productPrice;

  private double productDiscountedPrice;

   private Date writingDate;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "category_id")
  private Category category;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "promotion_id")
  private Promotion promotion;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private UserEntity userEntity;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

}
