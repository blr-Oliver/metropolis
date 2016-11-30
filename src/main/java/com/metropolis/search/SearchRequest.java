package com.metropolis.search;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Sort;

/**
 * Empty tag or attribute collection is considered to be equal to null
 *
 * @author Vasily Liaskovsky
 */
public class SearchRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	private String query;
	private Integer category;
	private Set<Integer> tags;
	private Map<Integer, Set<Integer>> attributes;
	private Integer start = 0;
	private Integer count;
	private Sort sort;

	public String getQuery() { return query; }
	public Integer getCategory() { return category; }
	public Set<Integer> getTags() { return tags; }
	public Map<Integer, Set<Integer>> getAttributes() { return attributes; }
	public Integer getStart() { return start; }
	public Integer getCount() { return count; }
	public Sort getSort() { return sort; }

	public void setQuery(String query) {
		this.query = query;
		if (this.query != null) {
			this.query = this.query.trim();
			if (this.query.length() == 0)
				this.query = null;
		}
	}
	public void setCategory(Integer category) { this.category = category; }
	public void setTags(Set<Integer> tags) {
		this.tags = tags;
		if (this.tags != null && this.tags.isEmpty())
			this.tags = null;
	}
	public void setAttributes(Map<Integer, Set<Integer>> attributes) {
		this.attributes = attributes;
		if (this.attributes != null && this.attributes.isEmpty())
			this.attributes = null;
	}
	public void setStart(Integer start) {
		if(start != null && start < 0)
			throw new IllegalArgumentException();
		this.start = start;
		if (this.start == null)
			this.start = 0;
	}
	public void setCount(Integer count) {
		if (count != null && count < 1)
			throw new IllegalArgumentException();
		this.count = count;
	}
	public void setSort(Sort sort) { this.sort = sort; }
	public boolean hasQuery() {
		return query != null;
	}
	public boolean hasConstraints() {
		return category != null || tags != null && !tags.isEmpty() || attributes != null && !attributes.isEmpty();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (attributes == null || attributes.isEmpty() ? 0 : attributes.hashCode());
		result = prime * result + (category == null ? 0 : category.hashCode());
		result = prime * result + (query == null ? 0 : query.hashCode());
		result = prime * result + (tags == null || tags.isEmpty() ? 0 : tags.hashCode());
		result = prime * result + (start == null ? 0 : start.hashCode());
		result = prime * result + (count == null ? 0 : count.hashCode());
		result = prime * result + (sort == null ? 0 : sort.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof SearchRequest))
			return false;
		SearchRequest other = (SearchRequest) obj;
		if (attributes == null || attributes.isEmpty()) {
			if (other.attributes != null && !other.attributes.isEmpty())
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (query == null) {
			if (other.query != null)
				return false;
		} else if (!query.equals(other.query))
			return false;
		if (tags == null || tags.isEmpty()) {
			if (other.tags != null && !other.tags.isEmpty())
				return false;
		} else if (!tags.equals(other.tags))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		if (count == null) {
			if (other.count != null)
				return false;
		} else if (!count.equals(other.count))
			return false;
		if (sort == null) {
			if (other.sort != null)
				return false;
		} else if (!sort.equals(other.sort))
			return false;
		return true;
	}

}
