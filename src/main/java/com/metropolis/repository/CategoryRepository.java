package com.metropolis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metropolis.mvc.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>, CategoryRepositoryEx {
}
