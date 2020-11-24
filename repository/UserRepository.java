package com.sneha.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sneha.model.UserDao;


@Repository
public interface UserRepository extends JpaRepository<UserDao, String> {
    UserDao findByUsername(String username);
    
}