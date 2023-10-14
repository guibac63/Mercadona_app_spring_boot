package com.mercadona_app.mercadona.repository;

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

import com.mercadona_app.mercadona.models.Role;


@ComponentScan(basePackages = "com.mercadona_app.mercadona.security")
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RoleRepositoryTests {
  
  @Autowired
  private RoleRepository roleRepository;

  @Test
  @Transactional
  public void RoleRepository_SaveAll_ReturnSavedRole() {

  Role role = Role.builder()
  .name("ADMIN").build();

  Role savedRole = roleRepository.save(role);

  Assertions.assertThat(savedRole).isNotNull();
  Assertions.assertThat(savedRole.getId()).isGreaterThan(0);

  }

  @Test
  @Transactional
  public void RoleRepository_GetAll_ReturnMoreThenOneRole() {
  Role role = Role.builder()
  .name("CUSTOMER").build();
  Role role2 = Role.builder()
  .name("USER").build();

  roleRepository.save(role);
  roleRepository.save(role2);

  List<Role> roleList = roleRepository.findAll();

  Assertions.assertThat(roleList).isNotNull();
  Assertions.assertThat(roleList.size()).isEqualTo(2);
  }

  @Test
  @Transactional
  public void RoleRepository_FindById_ReturnRole() {
  Role role = Role.builder()
  .name("ADMIN").build();

  roleRepository.save(role);

  Role roleSave = roleRepository.findById(role.getId()).get();

  Assertions.assertThat(roleSave).isNotNull();
  }

  @Test
  @Transactional
  public void RoleRepository_UpdateRole_ReturnRoleNotNullAndExpectedName() {
  Role role = Role.builder()
  .name("ADMIN").build();

  roleRepository.save(role);

  Role roleSave = roleRepository.findById(role.getId()).get();
  roleSave.setName("USER");

  Role updatedRole = roleRepository.save(roleSave);

  Assertions.assertThat(updatedRole.getName()).isNotNull();
  Assertions.assertThat(updatedRole.getName()).isEqualTo("USER");
  }

  @Test
  @Transactional
  public void RoleRepository_RoleDelete_ReturnRoleIsEmpty() {
  Role role = Role.builder()
  .name("ADMIN").build();

  roleRepository.save(role);

  roleRepository.deleteById(role.getId());
  Optional<Role> roleReturn = roleRepository.findById(role.getId());

  Assertions.assertThat(roleReturn).isEmpty();
  }

}
