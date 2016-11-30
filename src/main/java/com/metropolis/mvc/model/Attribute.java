package com.metropolis.mvc.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;

public class Attribute implements Serializable, IAttribute {
	private static final long serialVersionUID = 1L;

	@JsonView(Views.Basic.class)
	private Integer id;
	@JsonView(Views.Basic.class)
	private String displayName;
	@JsonView(Views.Full.class)
	private IAttribute.Type type;
	@JsonInclude(Include.NON_EMPTY)
	@JsonView(Views.Full.class)
	private List<AttributeValue> choices;

	public Attribute() {}

	@Override
	public Integer getId() { return id; }
	@Override
	public String getDisplayName() { return displayName; }
	@Override
	public IAttribute.Type getType() { return type; }
	@Override
	public List<AttributeValue> getChoices() { return choices; }

	@Override
	public void setDisplayName(String displayName) { this.displayName = displayName; }
	@Override
	public void setType(IAttribute.Type type) { this.type = type; }
	@Override
	public void setChoices(List<AttributeValue> choices) { this.choices = choices; }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (getDisplayName() == null ? 0 : getDisplayName().hashCode());
		result = prime * result + (getType() == null ? 0 : getType().hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof IAttribute))
			return false;
		IAttribute other = (IAttribute) obj;
		if (getDisplayName() == null) {
			if (other.getDisplayName() != null)
				return false;
		} else if (!getDisplayName().equals(other.getDisplayName()))
			return false;
		if (getType() != other.getType())
			return false;
		return true;
	}

}
