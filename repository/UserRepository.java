package com.sneha.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sneha.model.UserDao;


@Repository
public interface UserRepository extends JpaRepository<UserDao, String> {

	@Query("FROM UserDao WHERE username= ?1")
	Optional<UserDao> findByUserName(String username);
    
}