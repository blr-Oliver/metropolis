package com.metropolis.mvc.model;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;

public class Category implements Serializable, ICategory {
	private static final long serialVersionUID = 1L;
	@JsonView(Views.Basic.class)
	private Integer id;
	@JsonView(Views.Basic.class)
	private String displayName;
	@JsonView(Views.Basic.class)
	private String scopeDisplayName;
	@JsonView(Views.Basic.class)
	private String elementDisplayName;
	@JsonView(Views.Basic.class)
	private Integer parentId;
	@JsonIgnore
	private ICategory parent;
	@JsonView(Views.Full.class)
	@JsonInclude(Include.NON_EMPTY)
	private Set<ICategory> children;
	@JsonView(Views.Full.class)
	@JsonInclude(Include.NON_EMPTY)
	private Set<Tag> tags;
	@JsonView(Views.Full.class)
	@JsonInclude(Include.NON_EMPTY)
	private Set<IAttribute> attributes;

	public Category() {}

	@Override
	public Integer getId() { return id; }
	@Override
	public String getDisplayName() { return displayName; }
	@Override
	public String getScopeDisplayName() { return scopeDisplayName; }
	@Override
	public String getElementDisplayName() { return elementDisplayName; }
	@Override
	public Integer getParentId() { return parentId; }
	@Override
	public ICategory getParent() { return parent; }
	@Override
	public Set<ICategory> getChildren() { return children; }
	@Override
	public Set<Tag> getTags() { return tags; }
	@Override
	public Set<IAttribute> getAttributes() { return attributes; }

	@Override
	public void setDisplayName(String displayName) { this.displayName = displayName; }
	@Override
	public void setScopeDisplayName(String scopeDisplayName) { this.scopeDisplayName = scopeDisplayName; }
	@Override
	public void setElementDisplayName(String elementDisplayName) { this.elementDisplayName = elementDisplayName; }
	@Override
	public void setParent(ICategory parent) { this.parent = parent; }
	@Override
	public void setChildren(Set<ICategory> children) { this.children = children; }
	@Override
	public void setTags(Set<Tag> tags) { this.tags = tags; }
	@Override
	public void setAttributes(Set<IAttribute> attributes) { this.attributes = attributes; }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (getDisplayName() == null ? 0 : getDisplayName().hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ICategory))
			return false;
		ICategory other = (ICategory) obj;
		if (getDisplayName() == null) {
			if (other.getDisplayName() != null)
				return false;
		} else if (!getDisplayName().equals(other.getDisplayName()))
			return false;
		return true;
	}

}
