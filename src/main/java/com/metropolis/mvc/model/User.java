package com.metropolis.mvc.model;

import java.io.Serializable;
import java.util.Map;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String email;
	private boolean enabled = true;
	private boolean locked = false;
	private Map<Integer, IAccount> accounts;
	
	public Integer getId() { return id; }
	public String getName() { return name; }
	public String getEmail() { return email; }
	public boolean isEnabled() { return enabled; }
	public boolean isLocked() { return locked; }
	public Map<Integer, IAccount> getAccounts() { return accounts; }

	public void setName(String firstName) { this.name = firstName; }
	public void setEmail(String email) { this.email = email; }
	public void setEnabled(boolean enabled) { this.enabled = enabled; }
	public void setLocked(boolean locked) { this.locked = locked; }
	public void setAccounts(Map<Integer, IAccount> accounts) { this.accounts = accounts; }
}
