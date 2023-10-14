package com.mercadona_app.mercadona.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
  private String accessToken;
  private String tokenType = "Bearer ";

  public AuthResponseDTO() {
  }

  public AuthResponseDTO(String accessToken) {
    this.accessToken = accessToken;
  }
}
