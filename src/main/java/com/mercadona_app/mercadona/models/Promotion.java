package com.mercadona_app.mercadona.models;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.annotations.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Promotion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) 
  private int id;

  @Column(unique=true)
  private String promotionName;

  private Date beginningDate; 

  private Date endingDate;

  private Date writingDate;

  @Min(value = 1, message = "La valeur doit être d'au moins 1")
  @Max(value = 99, message = "La valeur doit être au maximum 99")
  private int promotionPercentage;

  @OneToMany(mappedBy = "promotion")
  @JsonIgnore
  private Set<Product> products;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private UserEntity userEntity;



  
}
