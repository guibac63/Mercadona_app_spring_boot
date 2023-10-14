package com.mercadona_app.mercadona.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {

  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private Integer id;
  
  private String imageName;

  private String type;
  
  @Column(length = 50000000)
  private byte[] picByte;

  @JsonIgnore
  @OneToOne(mappedBy = "image")
  private Product product;
}
