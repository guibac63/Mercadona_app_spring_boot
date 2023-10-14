package com.mercadona_app.mercadona.repository;

import java.util.ArrayList;
import java.util.Collections;
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
import com.mercadona_app.mercadona.models.UserEntity;

@ComponentScan(basePackages = "com.mercadona_app.mercadona.security")
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {
  
  @Autowired
  private UserRepository userRepository;


  @Test
  @Transactional
  public void UserRepository_SaveAll_ReturnSavedUser() {

    Role role = Role.builder().name("ADMIN").build();
    List<Role> roles = new ArrayList<>();
    roles.add(role);

    UserEntity user = UserEntity.builder()
        .username("usertest")
        .password("test")
        .roles(roles)
        .build();

    UserEntity savedUser = userRepository.save(user);

    Assertions.assertThat(savedUser).isNotNull();
    Assertions.assertThat(savedUser.getId()).isGreaterThan(0);

  }

  @Test
  @Transactional
  public void UserRepository_GetAll_ReturnMoreThenOneUser() {

    Role role = Role.builder().name("ADMIN").build();
    List<Role> roles = new ArrayList<>();
    roles.add(role);

    UserEntity user = UserEntity.builder()
        .username("usertest")
        .password("test")
        .roles(roles)
        .build();

    UserEntity user2 = UserEntity.builder()
        .username("usertest2")
        .password("test2")
        .roles(roles)
        .build();

    userRepository.save(user);
    userRepository.save(user2);

    List<UserEntity> userList = userRepository.findAll();

    Assertions.assertThat(userList).isNotNull();
    Assertions.assertThat(userList.size()).isEqualTo(2);
  }

  @Test
  @Transactional
  public void UserRepository_FindById_ReturnUser() {

    Role role = Role.builder().name("ADMIN").build();
    List<Role> roles = new ArrayList<>();
    roles.add(role);

    UserEntity user = UserEntity.builder()
        .username("usertest")
        .password("test")
        .roles(roles)
        .build();

    userRepository.save(user);

    UserEntity userSave = userRepository.findById(user.getId()).get();

    Assertions.assertThat(userSave).isNotNull();
  }

  @Test
  @Transactional
  public void UserRepository_UpdateUser_ReturnUserNotNullAndExpectedName() {
    
    Role role = Role.builder().name("ADMIN").build();
    List<Role> roles = new ArrayList<>();
    roles.add(role);

    UserEntity user = UserEntity.builder()
        .username("usertest")
        .password("test")
        .roles(roles)
        .build();

    userRepository.save(user);

    UserEntity userSave = userRepository.findById(user.getId()).get();
    userSave.setUsername("USER");

    UserEntity updatedUser = userRepository.save(userSave);

    Assertions.assertThat(updatedUser.getUsername()).isNotNull();
    Assertions.assertThat(updatedUser.getUsername()).isEqualTo("USER");
  }

  @Test
  @Transactional
  public void UserRepository_UserDelete_ReturnUserIsEmpty() {

    Role role = Role.builder().name("ADMIN").build();
    List<Role> roles = new ArrayList<>();
    roles.add(role);

    UserEntity user = UserEntity.builder()
        .username("usertest")
        .password("test")
        .roles(roles)
        .build();

    userRepository.save(user);

    userRepository.deleteById(user.getId());
    Optional<UserEntity> userReturn = userRepository.findById(user.getId());

    Assertions.assertThat(userReturn).isEmpty();
  }
}
