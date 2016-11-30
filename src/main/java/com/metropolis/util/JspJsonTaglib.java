package com.metropolis.util;

import com.fasterxml.jackson.core.JsonProcessingException;

public final class JspJsonTaglib {
	private JspJsonTaglib() {
	}

	public static String json(Object object) throws JsonProcessingException {
		return ObjectMapperHolder.getInstance().getMapper().writeValueAsString(object);
	}
	public static String jsonWithView(Object object, Class<?> viewClass) throws JsonProcessingException {
		return ObjectMapperHolder.getInstance().getMapper().writerWithView(viewClass).writeValueAsString(object);
	}
	public static String jsonWithView(Object object, String viewClassName) throws JsonProcessingException, ClassNotFoundException {
		return jsonWithView(object, obtainViewClass(object, viewClassName));
	}
	private static Class<?> obtainViewClass(Object object, String className) throws ClassNotFoundException {
		return Class.forName(className);
	}
}
