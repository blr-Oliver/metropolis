package com.metropolis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.metropolis.mvc.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
	@Query("select a from Account a where a.type.id = 0 and a.user.email = ?1")
	public Account findNativeByEmail(String email);
}
