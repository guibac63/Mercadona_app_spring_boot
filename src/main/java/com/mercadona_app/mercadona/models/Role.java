package com.mercadona_app.mercadona.models;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "roles")
public class Role {
  public Role() {
  }

  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private int id;

  private String name;
}
