package com.metropolis.mvc.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonView;
import com.metropolis.mvc.model.Shop;
import com.metropolis.mvc.model.Views;
import com.metropolis.repository.ShopRepository;

@Controller
@RequestMapping(value = "/API/shops", produces = "application/json; charset=UTF-8")
public class ShopAPIController {

	@Autowired
	private ShopRepository shopRepo;

	@JsonView(Views.Basic.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getSingleShop(@PathVariable("id") Integer id) {
		Shop shop = shopRepo.findOne(id);
		if (shop == null)
			return ResponseEntity.notFound().build();
		else
			return ResponseEntity.ok(shop);
	}

	@JsonView(Views.Basic.class)
	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public @ResponseBody List<Shop> getMultipleShops(@RequestParam(value = "id", required = false) List<Integer> ids) {
		if (ids == null || ids.isEmpty())
			return shopRepo.findAll();
		return shopRepo.findAll(ids);
	}

}
