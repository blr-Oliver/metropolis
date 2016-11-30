package com.metropolis.mvc.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class OAuthToken implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer accountId;
	@JsonIgnore
	private String accessToken;
	@JsonIgnore
	private String refreshToken;
	private Date expiresAt;

	public OAuthToken() {}

	public Integer getAccountId() { return accountId; }
	public String getAccessToken() { return accessToken; }
	public String getRefreshToken() { return refreshToken; }
	public Date getExpiresAt() { return expiresAt; }

	public void setAccountId(Integer accountId) { this.accountId = accountId; }
	public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
	public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
	public void setExpiresAt(Date expiresAt) { this.expiresAt = expiresAt; }

}
