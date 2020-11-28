package com.sneha.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sneha.model.RoleEnum;
import com.sneha.model.UserRoles;

public interface UserRoleRepository extends JpaRepository<UserRoles,Integer>{
	@Query("FROM UserRoles WHERE name= ?1")
	Optional<UserRoles> findByName(RoleEnum name);
}
