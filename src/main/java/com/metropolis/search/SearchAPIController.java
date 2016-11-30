package com.metropolis.search;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonView;
import com.metropolis.mvc.model.Shop;
import com.metropolis.mvc.model.Views;

@Controller
@RequestMapping(value = "/API/search", produces = "application/json; charset=UTF-8")
public class SearchAPIController {
	@Autowired
	private SearchService searchService;

	@JsonView(Views.Basic.class)
	@RequestMapping(value = "/shops", method = RequestMethod.GET)
	@ResponseBody
	public List<SearchResult<Shop>> searchShops(HttpServletRequest req) throws IOException {
		return searchService.search((SearchRequest) req.getAttribute("searchRequest"));
	}

}
