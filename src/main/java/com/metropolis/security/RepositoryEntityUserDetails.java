package com.metropolis.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.metropolis.mvc.model.User;

public class RepositoryEntityUserDetails extends org.springframework.security.core.userdetails.User {
	private static final long serialVersionUID = 1L;

	private final User entity;

	public RepositoryEntityUserDetails(User entity, String username, String password, boolean enabled,
			boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, true, true, accountNonLocked, authorities);
		if (entity == null)
			throw new NullPointerException();
		this.entity = entity;
	}

	public User getEntity() { return entity; }

	public static Builder builder() { return new Builder(); }
	public static class Builder {
		private User entity;
		private String userName;
		private String password;
		private boolean enabled = true;
		private boolean locked = false;
		private Set<GrantedAuthority> authorities;

		public Builder() { authorities = new HashSet<>(); }

		public Builder entity(User entity) {
			this.entity = entity;
			if (entity.getEmail() != null)
				userName(entity.getEmail());
			return enabled(entity.isEnabled()).locked(entity.isLocked());
		};

		public Builder userName(String userName){ this.userName = userName; return this; };
		public Builder password(String password){ this.password = password; return this; };
		public Builder enabled(boolean enabled){ this.enabled = enabled; return this; };
		public Builder locked(boolean locked){ this.locked = locked; return this; };
		public Builder authority(String auth){ authorities.add(new SimpleGrantedAuthority(auth)); return this; };

		public RepositoryEntityUserDetails build(){
			return new RepositoryEntityUserDetails(entity, userName, password, enabled, !locked, authorities);
		}
	}
}
