package com.metropolis.repository;

import java.util.List;

public interface CategoryRepositoryEx {
	List<Integer> findDescendants(Integer categoryId, boolean withSelf);
}
