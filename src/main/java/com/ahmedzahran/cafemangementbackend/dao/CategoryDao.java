package com.ahmedzahran.cafemangementbackend.dao;

import com.ahmedzahran.cafemangementbackend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryDao extends JpaRepository<Category, Integer> {

    List<Category> getAllCategory();
}
