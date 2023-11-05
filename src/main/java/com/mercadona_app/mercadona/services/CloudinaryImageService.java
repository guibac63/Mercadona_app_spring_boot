package com.mercadona_app.mercadona.services;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryImageService {
  public Map upload(MultipartFile file);
}
