package com.metropolis.mvc.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;
import com.metropolis.util.MultiMap;
import com.metropolis.util.MultiMapDecorator;

public class Shop implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonView(Views.Basic.class)
	private Integer id;
	@JsonView(Views.Basic.class)
	private String name;
	@JsonIgnore
	private Integer categoryId;
	@JsonView(Views.Basic.class)
	private ICategory category;
	@JsonView(Views.Basic.class)
	private String shortDescription;
	@JsonView(Views.Basic.class)
	@JsonInclude(Include.NON_EMPTY)
	private String description;
	@JsonView(Views.Basic.class)
	@JsonInclude(Include.NON_EMPTY)
	private Set<Tag> tags;
	@JsonView(Views.Basic.class)
	@JsonInclude(Include.NON_EMPTY)
	private Set<AttributeSelection> attributeSelections;

	public Integer getId() { return id; }
	public String getName() { return name; }
	public Integer getCategoryId() { return categoryId; }
	public ICategory getCategory() { return category; }
	public String getShortDescription() { return shortDescription; }
	public String getDescription() { return description; }
	public Set<Tag> getTags() { return tags; }
	public Set<AttributeSelection> getAttributeSelections() { return attributeSelections; }

	@JsonIgnore
	public MultiMap<IAttribute, AttributeValue, Set<AttributeValue>> getAttributes() {
		Set<AttributeSelection> attributes = getAttributeSelections();
		if (attributes == null)
			return null;
		MultiMapDecorator<IAttribute, AttributeValue, Set<AttributeValue>> result = MultiMapDecorator.newInstance(new LinkedHashMap<>(), LinkedHashSet.class);
		attributes.stream().forEach(s -> result.add(s.getAttribute(), s.getValue()));
		return result.wipeNullMarkers();
	}

	public void setName(String name) { this.name = name; }
	public void setCategory(ICategory category) { this.category = category; }
	public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }
	public void setDescription(String fullDescription) { description = fullDescription; }
	public void setTags(Set<Tag> tags) { this.tags = tags; }
	public void setAttributeSelections(Set<AttributeSelection> attributes) { attributeSelections = attributes; }

	public static class AttributeSelection implements Serializable {
		private static final long serialVersionUID = 1L;

		@JsonIgnore
		private Integer attributeId;
		@JsonIgnore
		private Integer valueId;
		@JsonView(Views.Basic.class)
		private IAttribute attribute;
		@JsonView(Views.Basic.class)
		@JsonInclude(Include.NON_NULL)
		private AttributeValue value;

		AttributeSelection() { // JPA only
		}
		public AttributeSelection(IAttribute attribute, AttributeValue value) {
			this.attribute = attribute;
			this.value = value;
		}
		public Integer getAttributeId() { return attributeId; }
		public Integer getValueId() { return valueId; }
		public IAttribute getAttribute() { return attribute; }
		public AttributeValue getValue() { return value; }

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (getAttribute() == null ? 0 : getAttribute().hashCode());
			result = prime * result + (getValue() == null ? 0 : getValue().hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof AttributeSelection))
				return false;
			AttributeSelection other = (AttributeSelection) obj;
			if (getAttribute() == null) {
				if (other.getAttribute() != null)
					return false;
			} else if (!getAttribute().equals(other.getAttribute()))
				return false;
			if (getValue() == null) {
				if (other.getValue() != null)
					return false;
			} else if (!getValue().equals(other.getValue()))
				return false;
			return true;
		}

	}
}
