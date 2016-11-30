package com.metropolis.mvc.model;

import java.util.List;

public interface IAttribute {
	enum Type {
		TOGGLE, CHOICE, MULTI;
		public int getCode() {
			return ordinal();
		}
	}

	Integer getId();
	String getDisplayName();
	IAttribute.Type getType();
	List<AttributeValue> getChoices();

	void setDisplayName(String displayName);
	void setType(IAttribute.Type type);
	void setChoices(List<AttributeValue> choices);
}