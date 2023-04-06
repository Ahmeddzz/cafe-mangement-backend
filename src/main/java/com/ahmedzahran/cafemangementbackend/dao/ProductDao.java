package com.ahmedzahran.cafemangementbackend.dao;

import com.ahmedzahran.cafemangementbackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<Product, Integer> {
}
