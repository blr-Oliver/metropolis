package com.metropolis.mvc.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonView;

public class Tag implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonView(Views.Basic.class)
	private Integer id;
	@JsonView(Views.Basic.class)
	private String displayName;

	public Integer getId() { return id; }
	public String getDisplayName() { return displayName; }

	public void setDisplayName(String displayName) { this.displayName = displayName; }

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
		if (!(obj instanceof Tag))
			return false;
		Tag other = (Tag) obj;
		if (getDisplayName() == null) {
			if (other.getDisplayName() != null)
				return false;
		} else if (!getDisplayName().equals(other.getDisplayName()))
			return false;
		return true;
	}


}
