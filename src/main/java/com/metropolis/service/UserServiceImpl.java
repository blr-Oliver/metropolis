package com.metropolis.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metropolis.mvc.controller.RegistrationForm;
import com.metropolis.mvc.model.Account;
import com.metropolis.mvc.model.IAccount;
import com.metropolis.mvc.model.User;
import com.metropolis.repository.AuthorizationTypeRepository;
import com.metropolis.repository.UserRepository;
import com.metropolis.security.DuplicateEmailException;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private AuthorizationTypeRepository authTypeRepo;

	@Override
	public User registerNewUser(RegistrationForm userData) throws DuplicateEmailException {
		if (userRepo.findByEmail(userData.getEmail()) != null)
			throw new DuplicateEmailException(userData.getEmail());
		User user = new User();
		user.setEmail(userData.getEmail());
		user.setName(userData.getName());
		IAccount account = new Account(authTypeRepo.findByName("native"), user);
		account.setClientId("dummy");
		account.setPassword(userData.getPassword());
		user.setAccounts(new HashMap<>());
		user.getAccounts().put(0, account);
		return userRepo.saveAndFlush(user);
	}
}
