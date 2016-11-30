package com.metropolis.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.criteria.*;

import org.springframework.data.jpa.domain.Specification;

import com.metropolis.mvc.model.Shop;
import com.metropolis.mvc.model.Shop.AttributeSelection;

public class AttributeSpecification implements Specification<Shop> {

	private final Map<Integer, Set<Integer>> attributes;

	public AttributeSpecification(Map<Integer, Set<Integer>> attributes) {
		this.attributes = attributes;
	}

	@Override
	public Predicate toPredicate(Root<Shop> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		if (attributes == null || attributes.isEmpty())
			return null;
		Subquery<Integer> subquery = query.subquery(Integer.class);
		Root<Shop> subRoot = subquery.from(Shop.class);
		Path<Integer> rootId = subRoot.get("id");
		subquery.select(rootId);
		SetJoin<Shop, AttributeSelection> attrSelections = subRoot.join(subRoot.getModel().getSet("attributeSelections", Shop.AttributeSelection.class), JoinType.INNER);
		Map<Boolean, Set<Integer>> splitByHavingChoice = attributes.entrySet().stream()
				.collect(Collectors.partitioningBy(e -> e.getValue() == null || e.getValue().isEmpty(), Collectors.mapping(Map.Entry::getKey, Collectors.toSet())));
		List<Predicate> restrictions = new ArrayList<>(2);
		Path<Object> selectionAttribute = attrSelections.get("attributeId");
		if (splitByHavingChoice.containsKey(true) && !splitByHavingChoice.get(true).isEmpty())
			restrictions.add(selectionAttribute.in(splitByHavingChoice.get(true)));
		if (splitByHavingChoice.containsKey(false) && !splitByHavingChoice.get(false).isEmpty()) {
			Path<Object> selectionValue = attrSelections.get("valueId");
			for (Integer attributeId: splitByHavingChoice.get(false)) {
				restrictions.add(cb.and(//
						cb.equal(selectionAttribute, attributeId), //
						cb.or(//
								selectionValue.in(attributes.get(attributeId)), //
								cb.isNull(selectionValue)//
				)));
			}
		}
		subquery.where(cb.or(restrictions.toArray(new Predicate[restrictions.size()])));
		subquery.groupBy(rootId);
		subquery.having(cb.equal(cb.countDistinct(selectionAttribute), attributes.size()));
		return root.get("id").in(subquery);
	}

}
