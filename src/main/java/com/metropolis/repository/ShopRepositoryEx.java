package com.metropolis.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.metropolis.mvc.model.Shop;
import com.metropolis.search.SearchMatcher;
import com.metropolis.search.SearchRequest;

public interface ShopRepositoryEx {
	List<Integer> search(Specification<Shop> spec);
	/**
	 * Searches for shops matching criteria. Only id of entities are fetched along with relevance scores for category,
	 * tags and attributes. Only rows having non-zero sum of scores are returned. Zero scores are replaced by null.
	 *
	 * <strong>Important</strong> Use singleton list with arbitrary non-matchable value instead of empty lists (required
	 * for IN clause)
	 *
	 * @param category
	 *            primary (top) category to match
	 * @param categories
	 *            secondary categories to match (immediate children or descendants)
	 * @param tags
	 *            tags to match; must not be empty or null
	 * @param tagSize
	 *            number of tags; must not be 0
	 * @param noChoiceA
	 *            attributes with no requested choices; must not be empty or null
	 * @param ncSize
	 *            number of attributes without choices; must not be 0
	 * @param choiceA
	 *            attributes with requested choices; must not be empty or null
	 * @param choices
	 *            all the requested choices; must not be empty or null
	 * @param token
	 *            special string token constructed as following: count of requested choices for the attribute with
	 *            <em>a</em> id is on the <em>(a / 10)</em>th place of the token, with first token character at index
	 *            <em>1</em>. Thus no more than 9 choices per attribute are supported
	 * @param aSize
	 *            total attribute count; must not be 0
	 * @return rows in form of <strong>[shopId (int), name(String), categoryScore (double), tagScore (double),
	 *         attributeScore (double)]</strong>; shopId is not null, name is not null, others vary in range (0.0 ..
	 *         1.0] with possible nulls
	 *
	 * @see SearchMatcher#match(SearchRequest)
	 */
	List<Object[]> findAndScore(int category, //
			Collection<Integer> categories, //
			Collection<Integer> tags, //
			int tagSize, //
			Collection<Integer> noChoiceA, //
			int ncSize, //
			Collection<Integer> choiceA, //
			Collection<Integer> choices, //
			String token, //
			int aSize, //
			Sort sort);
}
