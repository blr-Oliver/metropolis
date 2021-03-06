package com.metropolis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metropolis.mvc.model.Attribute;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Integer> {
}
