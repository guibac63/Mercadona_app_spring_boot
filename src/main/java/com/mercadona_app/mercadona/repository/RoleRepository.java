package com.mercadona_app.mercadona.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mercadona_app.mercadona.models.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
  Optional<Role> findByName(String name);
}
