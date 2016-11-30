package com.metropolis.search;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.*;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

public class SearchRequestFilter implements Filter {
	private static final Sort DEFAULT_SORT = createJPASort("score$desc,name$asc");

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setAttribute("searchRequest", SearchRequestFilter.fromRequestParams(request.getParameterMap()));
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

	private static SearchRequest fromRequestParams(Map<String, String[]> params) {
		SearchRequest request = new SearchRequest();
		if (params.containsKey("q"))
			request.setQuery(params.get("q")[0]);
		if (params.containsKey("c"))
			request.setCategory(asIntIfPossible(params.get("c")[0]));
		request.setTags(asIntSet(params.get("t")));
		Set<Integer> attributeKeys = asIntSet(params.get("a"));
		Map<Integer, Set<Integer>> attributes = null;
		if (attributeKeys != null && !attributeKeys.isEmpty())
			attributes = attributeKeys.parallelStream().collect(Collectors.toMap(Function.identity(), a -> asIntSet(params.get("a" + a))));
		request.setAttributes(attributes);
		if (params.containsKey("start"))
			request.setStart(asIntIfPossible(params.get("start")[0]));
		if (params.containsKey("count"))
			request.setCount(asIntIfPossible(params.get("count")[0]));
		if (params.containsKey("sort"))
			request.setSort(createJPASort(params.get("sort")[0]));
		setupDefaults(request);
		return request;
	}

	private static Set<Integer> asIntSet(String[] values) {
		return values == null ? Collections.emptySet()
				: Arrays.asList(values).parallelStream().map(SearchRequestFilter::asIntIfPossible).filter(Objects::nonNull).collect(Collectors.toSet());
	}

	private static Integer asIntIfPossible(String value) {
		try {
			return Integer.valueOf(value);
		} catch (NumberFormatException ex) {
			return null;
		}
	}

	private static Sort createJPASort(String sort) {
		if (sort == null || (sort = sort.trim()).length() == 0)
			return null;
		String[] parts = sort.split("\\s*,\\s*");
		List<Order> sortOrders = new ArrayList<>(parts.length);
		for (int i = 0; i < parts.length; ++i) {
			String part = parts[i].toLowerCase();
			String property, direction;
			if (part.contains("$")) {
				String[] subParts = part.split("\\$");
				if (subParts.length != 2)
					continue;
				property = subParts[0];
				direction = subParts[1];
			} else {
				property = part;
				direction = null;
			}
			if ("name".equals(property)) {
				if (direction == null)
					direction = "asc";
			} else if ("score".equals(property) || "relevance".equals(property)) {
				if (direction == null)
					direction = "desc";
			} else
				continue;
			sortOrders.add(new Order(Direction.fromString(direction), property));
		}
		return sortOrders.isEmpty() ? null : new Sort(sortOrders);
	}

	private static void setupDefaults(SearchRequest request) {
		if (request.getCount() == null)
			request.setCount(10);
		if (request.getSort() == null)
			request.setSort(DEFAULT_SORT);
	}

}
