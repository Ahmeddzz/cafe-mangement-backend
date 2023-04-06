package com.ahmedzahran.cafemangementbackend.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;


public interface ProductService {
    ResponseEntity<String> addNewProduct(Map<String, String> requestMap);
}
