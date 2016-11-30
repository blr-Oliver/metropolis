package com.metropolis.mvc.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonView;

public class AttributeValue implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonView(Views.Basic.class)
	private Integer id;
	@JsonView(Views.Basic.class)
	private String displayName;

	public AttributeValue(){}

	public Integer getId() { return id; }
	public String getDisplayName() { return displayName; }

	public void setDisplayName(String displayName) { this.displayName = displayName; }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (displayName == null ? 0 : displayName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AttributeValue))
			return false;
		AttributeValue other = (AttributeValue) obj;
		if (displayName == null) {
			if (other.displayName != null)
				return false;
		} else if (!displayName.equals(other.displayName))
			return false;
		return true;
	}

}
