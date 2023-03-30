package com.ahmedzahran.cafemangementbackend.dao;

import com.ahmedzahran.cafemangementbackend.model.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserDao extends JpaRepository<User, Integer> {

    User findByEmailId(@Param("email" )String email);

}
