package com.metropolis.search;

import java.util.Collection;

import javax.persistence.criteria.*;

import org.springframework.data.jpa.domain.Specification;

import com.metropolis.mvc.model.Shop;
import com.metropolis.mvc.model.Tag;

public class TagSpecification implements Specification<Shop> {

	private final Collection<Integer> tags;

	public TagSpecification(Collection<Integer> tags) {
		this.tags = tags;
	}

	@Override
	public Predicate toPredicate(Root<Shop> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		return tags == null || tags.isEmpty() ? null : getOrCreateTagJoin(root).get("id").in(tags);
	}

	private SetJoin<Shop, ?> getOrCreateTagJoin(Root<Shop> root) {
		for (Join<Shop, ?> j : root.getJoins()) {
			if (j instanceof SetJoin) {
				SetJoin<Shop, ?> setJoin = (SetJoin<Shop, ?>) j;
				if (setJoin.getModel().getElementType().getJavaType().isAssignableFrom(Tag.class))
					return setJoin;
			}
		}
		return root.join(root.getModel().getSet("tags", Tag.class), JoinType.INNER);
	}

}
