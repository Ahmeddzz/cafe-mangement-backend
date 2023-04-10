package com.ahmedzahran.cafemangementbackend.dao;

import com.ahmedzahran.cafemangementbackend.model.Product;
import com.ahmedzahran.cafemangementbackend.wrapper.ProductWrapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductDao extends JpaRepository<Product, Integer> {
    List<ProductWrapper> getAllProducts();

    @Modifying
    @Transactional
    Integer updateProductStatus(@Param("status") String status, @Param("id") Integer id);

    List<ProductWrapper> getProductByCategory(@Param("categoryId") Integer categoryId);

    ProductWrapper getProductById(@Param("id") Integer id);
}
