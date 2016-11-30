package com.metropolis.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.metropolis.mvc.model.User;
import com.metropolis.security.RepositoryEntityUserDetails;

public class SecurityUtil {

	public static void logInUser(User user) {
		RepositoryEntityUserDetails userDetails = RepositoryEntityUserDetails.builder().entity(user)//
				.password(user.getAccounts().get(0).getPassword())// native is always with 0
				.authority("default").build();
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
