package com.metropolis.mvc.model;

import java.util.Set;

public interface ICategory {

	Integer getId();
	String getDisplayName();
	String getScopeDisplayName();
	String getElementDisplayName();
	Integer getParentId();
	ICategory getParent();
	Set<ICategory> getChildren();

	void setDisplayName(String displayName);
	void setScopeDisplayName(String scopeDisplayName);
	void setElementDisplayName(String elementDisplayName);
	void setParent(ICategory parent);
	void setChildren(Set<ICategory> children);
	void setAttributes(Set<IAttribute> attributes);
	void setTags(Set<Tag> tags);
	Set<IAttribute> getAttributes();
	Set<Tag> getTags();

}