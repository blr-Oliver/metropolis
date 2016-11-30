package com.metropolis.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperHolder {
	private final ObjectMapper mapper;

	private ObjectMapperHolder(ObjectMapper mapper) {
		this.mapper = mapper;
	}
	public ObjectMapper getMapper() {
		return mapper;
	}
	private static ObjectMapperHolder instance;
	public static ObjectMapperHolder getInstance() {
		return instance;
	}
	public static void createInstance(ObjectMapper mapper) {
		instance = new ObjectMapperHolder(mapper);
	}
}
