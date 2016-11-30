package com.metropolis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metropolis.mvc.model.AttributeValue;

@Repository
public interface AttributeValueRepository extends JpaRepository<AttributeValue, Integer> {
}
