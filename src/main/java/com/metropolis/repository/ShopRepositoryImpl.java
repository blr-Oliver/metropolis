package com.metropolis.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;

import com.metropolis.mvc.model.Shop;

public class ShopRepositoryImpl implements ShopRepositoryEx {
	private static final String FIND_QUERY_UNORDERED = "select s.id, s.display_name," + //
			" case when id_category = :category then 1.0 when id_category in :categories then 0.8 else null end c," + //
			" case when t.score > 0 then t.score else null end t," + //
			" case when a.score > 0 then a.score else null end a," + //
			" (case when id_category = :category then 1.0 when id_category in :categories then 0.8 else null end + a.score + t.score) x " + //
			"from shop s " + //
			"inner join (" + //
			" select id_shop," + //
			"  sum(case when id_tag in :tags then 1.0 else 0.0 end) / :tagSize score " + //
			" from shop_tag group by id_shop" + //
			") t on s.id = t.id_shop " + //
			"inner join (" + //
			" select id_shop," + //
			"  sum(" + //
			"   case when id_attribute in :noChoiceA then 1.0 / (id_attribute % 10) else 0.0 end / :ncSize +" + //
			"   case when id_attribute in :choiceA and (id_val in :choices or id_val is null) then 1.0 / substring(:token, id_attribute / 10, 1) else 0.0 end" + //
			"  ) / :aSize score " + //
			" from shop_attribute group by id_shop" + //
			") a on s.id = a.id_shop " + //
			"where (case when id_category = :category then 1.0 when id_category in :categories then 0.8 else null end + a.score + t.score) > 0";

	@Autowired
	private EntityManager em;
	@Override
	public List<Integer> search(Specification<Shop> spec) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Integer> query = builder.createQuery(Integer.class);
		Root<Shop> root = query.from(Shop.class);

		if (spec != null) {
			Predicate predicate = spec.toPredicate(root, query, builder);
			if (predicate != null)
				query.where(predicate);
		}

		query.select(root.get("id")); // TODO add distinct
		return em.createQuery(query).getResultList();
	}
	@Override
	public List<Object[]> findAndScore(int category, Collection<Integer> categories, Collection<Integer> tags, int tagSize, Collection<Integer> noChoiceA, int ncSize,
			Collection<Integer> choiceA, Collection<Integer> choices, String token, int aSize, Sort sort) {
		String query = FIND_QUERY_UNORDERED;
		if (sort != null) {
			List<String> orders = new ArrayList<>(3);
			for (Order o: sort) {
				String property = o.getProperty();
				if ("name".equalsIgnoreCase(property)) {
					orders.add("s.display_name " + (o.isAscending() ? "asc" : "desc"));
				} else if ("score".equalsIgnoreCase(property) || "relevance".equalsIgnoreCase(property)) {
					orders.add("x " + (o.isAscending() ? "asc" : "desc"));
				} else if ("id".equalsIgnoreCase(property) || "shop".equalsIgnoreCase(property)) {
					orders.add("s.id " + (o.isAscending() ? "asc" : "desc"));
				}
			}
			if (!orders.isEmpty())
				query += " order by " + String.join(", ", orders);
		}
		query = query.replace(":categories", asInClauseParameter(categories));
		query = query.replace(":tags", asInClauseParameter(tags));
		query = query.replace(":noChoiceA", asInClauseParameter(noChoiceA));
		query = query.replace(":choiceA", asInClauseParameter(choiceA));
		query = query.replace(":choices", asInClauseParameter(choices));
		Query nativeQuery = em.createNativeQuery(query);
		nativeQuery.setParameter("category", category);
		nativeQuery.setParameter("tagSize", tagSize);
		nativeQuery.setParameter("ncSize", ncSize);
		nativeQuery.setParameter("token", token);
		nativeQuery.setParameter("aSize", aSize);
		@SuppressWarnings("unchecked")
		List<Object[]> resultList = nativeQuery.getResultList();
		return resultList;
	}

	private static String asInClauseParameter(Collection<Integer> items) {
		StringBuilder sb = new StringBuilder("(");
		for (Integer item: items)
			sb.append(item).append(',');
		sb.setCharAt(sb.length() - 1, ')');
		return sb.toString();
	}

}
