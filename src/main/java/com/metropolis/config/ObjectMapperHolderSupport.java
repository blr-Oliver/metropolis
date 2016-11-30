package com.metropolis.config;

import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metropolis.util.ObjectMapperHolder;

@Configuration
public class ObjectMapperHolderSupport {
	@Bean
	public ObjectMapperHolder objectMapperSupport(HttpMessageConverters converters) {
		ObjectMapperHolder.createInstance(lookupMapper(converters));
		return ObjectMapperHolder.getInstance();
	}

	private ObjectMapper lookupMapper(HttpMessageConverters converters) {
		for (HttpMessageConverter<?> converter : converters.getConverters()) {
			if (converter instanceof MappingJackson2HttpMessageConverter) {
				MappingJackson2HttpMessageConverter jacksonConverter = (MappingJackson2HttpMessageConverter) converter;
				if (jacksonConverter.getObjectMapper() != null)
					return jacksonConverter.getObjectMapper();
			}
		}
		return null;
	}
}
