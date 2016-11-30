package com.metropolis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.metropolis.mvc.model.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer>, JpaSpecificationExecutor<Shop>, ShopRepositoryEx {
}