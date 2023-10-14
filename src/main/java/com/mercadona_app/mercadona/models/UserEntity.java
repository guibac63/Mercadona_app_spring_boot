package com.mercadona_app.mercadona.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private int id;

  private String username;

  private String password;
  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
  private List<Role> roles = new ArrayList<>();

  @OneToMany(mappedBy = "userEntity")
  @JsonIgnore
  private Set<Category> category;

  @OneToMany(mappedBy = "userEntity")
  @JsonIgnore
  private Set<Promotion> promotion;

  @OneToMany(mappedBy = "userEntity")
  @JsonIgnore
  private Set<Product> product;


}
