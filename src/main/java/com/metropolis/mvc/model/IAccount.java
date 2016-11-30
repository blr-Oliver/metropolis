package com.metropolis.mvc.model;

public interface IAccount {
	Integer getId();
	Integer getUserId();
	User getUser();
	AuthorizationType getType();
	String getClientId();
	String getPassword();

	void setClientId(String login);
	void setPassword(String password);
}