package com.metropolis.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;

public class CategoryRepositoryImpl implements CategoryRepositoryEx {
	private static final String IMMEDIATE_CHILDREN = "select d.id from Category c join c.children d where c.id in ?1";
	@Autowired
	private EntityManager em;

	@Override
	public List<Integer> findDescendants(Integer categoryId, boolean withSelf) {
		if (categoryId == null)
			return null;
		List<Integer> result = new ArrayList<>();
		if (withSelf)
			result.add(categoryId);
		List<Integer> stepResult = Collections.singletonList(categoryId);
		TypedQuery<Integer> immediateSearcher = em.createQuery(IMMEDIATE_CHILDREN, Integer.class);
		do {
			immediateSearcher.setParameter(1, stepResult);
			stepResult = immediateSearcher.getResultList();
			result.addAll(stepResult);
		} while (!stepResult.isEmpty());
		return result;
	}

}
