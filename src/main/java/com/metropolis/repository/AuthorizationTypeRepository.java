package com.metropolis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metropolis.mvc.model.AuthorizationType;

@Repository
public interface AuthorizationTypeRepository extends JpaRepository<AuthorizationType, Integer> {
	AuthorizationType findByName(String name);
}
