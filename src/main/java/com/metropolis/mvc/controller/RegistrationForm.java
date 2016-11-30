package com.metropolis.mvc.controller;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class RegistrationForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Email
	@NotEmpty
	@Size(max = 500)
	private String email;
	@NotEmpty
	@Size(min = 3, max = 300)
	private String name;
	@NotEmpty
	@Size(min = 6, max = 256)
	private String password;

	public String getEmail() { return email; }
	public String getName() { return name; }
	public String getPassword() { return password; }

	public void setEmail(String email) { this.email = email; }
	public void setName(String name) { this.name = name; }
	public void setPassword(String password) { this.password = password; }
}
