package com.mercadona_app.mercadona.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.meanbean.test.BeanTester;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DTOTester {
  
  @Test
  void testAllDTOs(){
  
    BeanTester beanTester = new BeanTester();
    beanTester.testBean(AuthResponseDTO.class);
    beanTester.testBean(LoginDto.class);
    beanTester.testBean(RegisterDto.class);

  }

}
