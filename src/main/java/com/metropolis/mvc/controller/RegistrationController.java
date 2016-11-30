package com.metropolis.mvc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.metropolis.security.DuplicateEmailException;
import com.metropolis.service.SecurityUtil;
import com.metropolis.service.UserService;

@Controller
@SessionAttributes("user")
@RequestMapping("/user/register")
public class RegistrationController {
	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public String registerPage(Model model) {
		if (!model.containsAttribute("user"))
			model.addAttribute("user", new RegistrationForm());
		return "user/register";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String registerAction(@Valid @ModelAttribute("user") RegistrationForm userData, BindingResult binding) {
		if (binding.hasErrors())
			return "user/register";
		try {
			SecurityUtil.logInUser(userService.registerNewUser(userData));
			return "redirect:/";
		} catch (DuplicateEmailException dupEx) {
			addFieldError("user", "email", userData.getEmail(), "user.email.duplicate", binding);
			return "user/register";
		}
	}

	private void addFieldError(String objectName, String fieldName, String fieldValue, String errorCode,
			BindingResult binding) {
		FieldError error = new FieldError(objectName, fieldName, fieldValue, false, new String[] { errorCode },
				new Object[] {}, errorCode);

		binding.addError(error);
	}
}
