package com.metropolis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module.Feature;

@Configuration
public class JacksonHibernate4ModuleConfig {
	@Bean
	public Module hibernate4Module() {
		Hibernate4Module module = new Hibernate4Module();
		module.configure(Feature.FORCE_LAZY_LOADING, true);
		module.configure(Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS, true);
		module.configure(Feature.USE_TRANSIENT_ANNOTATION, false);
		module.configure(Feature.REQUIRE_EXPLICIT_LAZY_LOADING_MARKER, false);
		return module;
	}

}
