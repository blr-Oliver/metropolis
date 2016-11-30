package com.metropolis.service;

import com.metropolis.mvc.controller.RegistrationForm;
import com.metropolis.mvc.model.User;
import com.metropolis.security.DuplicateEmailException;

public interface UserService {
	User registerNewUser(RegistrationForm userData) throws DuplicateEmailException;
}