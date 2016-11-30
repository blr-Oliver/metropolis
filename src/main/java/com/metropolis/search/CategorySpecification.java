package com.metropolis.search;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.metropolis.mvc.model.Shop;

public class CategorySpecification implements Specification<Shop> {
	private final Integer categoryId;

	public CategorySpecification(Integer categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public Predicate toPredicate(Root<Shop> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		return categoryId == null ? null : cb.equal(root.get("categoryId"), categoryId);
	}
}
