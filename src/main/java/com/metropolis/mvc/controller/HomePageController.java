package com.metropolis.mvc.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.metropolis.repository.CategoryRepository;
import com.metropolis.search.SearchRequest;
import com.metropolis.search.SearchService;

@Controller
public class HomePageController {
	@Autowired
	private CategoryRepository categoryRepo;
	@Autowired
	private SearchService searchService;

	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public String root(HttpServletRequest req, Model model) throws IOException {
		SearchRequest request = (SearchRequest) req.getAttribute("searchRequest");
		model.addAttribute("categoryRoot", categoryRepo.findOne(0));
		model.addAttribute("shops", searchService.search(request));
		return "index";
	}

	@RequestMapping(value = { "/Home", "/Index" }, method = RequestMethod.GET)
	public String rootAlias() {
		return "redirect:/";
	}

}
