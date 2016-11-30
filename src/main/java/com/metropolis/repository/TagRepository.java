package com.metropolis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metropolis.mvc.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
}
