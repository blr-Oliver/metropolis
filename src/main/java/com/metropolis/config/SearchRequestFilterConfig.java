package com.metropolis.config;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.metropolis.search.SearchRequestFilter;

@Configuration
public class SearchRequestFilterConfig {

	@Bean
	public FilterRegistrationBean searchRequestFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new SearchRequestFilter());
		registration.addUrlPatterns("/", "/API/search/shops");
		return registration;
	}
}
