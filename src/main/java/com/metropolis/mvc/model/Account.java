package com.metropolis.mvc.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Account implements Serializable, IAccount {
	private static final long serialVersionUID = 1L;

	Account() {	// JPA only
	}
	public Account(AuthorizationType type, User user){
		this.type = type;
		this.user = user;
	}

	private Integer id;
	private Integer userId;
	@JsonIgnore
	private User user;
	private AuthorizationType type;
	private String clientId;
	@JsonIgnore
	private String password;

	@Override
	public Integer getId() { return id; }
	@Override
	public Integer getUserId() { return userId; }
	@Override
	public User getUser() { return user; }
	@Override
	public AuthorizationType getType() { return type; }
	@Override
	public String getClientId() { return clientId; }
	@Override
	public String getPassword() { return password; }

	@Override
	public void setClientId(String login) { clientId = login; }
	@Override
	public void setPassword(String password) { this.password = password; }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (getType() == null ? 0 : getType().hashCode());
		result = prime * result + (getUserId() == null ? 0 : getUserId().hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof IAccount))
			return false;
		IAccount other = (IAccount) obj;
		if (getType() == null) {
			if (other.getType() != null)
				return false;
		} else if (!getType().equals(other.getType()))
			return false;
		if (getUserId() == null) {
			if (other.getUserId() != null)
				return false;
		} else if (!getUserId().equals(other.getUserId()))
			return false;
		return true;
	}

}
