package com.metropolis.mvc.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AuthorizationType implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	@JsonIgnore
	private String authorizeURL;
	@JsonIgnore
	private String tokenURL;
	@JsonIgnore
	private String clientId;
	@JsonIgnore
	private String clientSecret;
	@JsonIgnore
	private String scope;
	@JsonIgnore
	private String query;

	AuthorizationType() {} // JPA only
	public Integer getId() { return id; }
	public String getName() { return name; }
	public String getAuthorizeURL() { return authorizeURL; }
	public String getTokenURL() { return tokenURL; }
	public String getClientId() { return clientId; }
	public String getClientSecret() { return clientSecret; }
	public String getScope() { return scope; }
	public String getQuery() { return query; }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (getName() == null ? 0 : getName().hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AuthorizationType))
			return false;
		AuthorizationType other = (AuthorizationType) obj;
		if (getName() == null) {
			if (other.getName() != null)
				return false;
		} else if (!getName().equals(other.getName()))
			return false;
		return true;
	}

}
