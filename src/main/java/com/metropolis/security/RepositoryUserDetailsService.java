package com.metropolis.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.metropolis.mvc.model.IAccount;
import com.metropolis.repository.AccountRepository;

public class RepositoryUserDetailsService implements UserDetailsService {
	private final AccountRepository accountRepo;

	public RepositoryUserDetailsService(AccountRepository accountRepo) {
		this.accountRepo = accountRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		IAccount account = accountRepo.findNativeByEmail(username);
		if (account == null)
			throw new UsernameNotFoundException(username);
		return RepositoryEntityUserDetails.builder().entity(account.getUser()).password(account.getPassword())
				.authority("default").build();
	}

}
