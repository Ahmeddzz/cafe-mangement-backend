package com.ahmedzahran.cafemangementbackend.dao;

import com.ahmedzahran.cafemangementbackend.model.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Integer> {
}
