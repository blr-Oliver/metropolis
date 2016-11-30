package com.metropolis.search;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;
import com.metropolis.mvc.model.Views;

public class SearchResult<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonView(Views.Basic.class)
	@JsonInclude(Include.NON_EMPTY)
	private String type;
	@JsonView(Views.Basic.class)
	private int id;
	@JsonView(Views.Basic.class)
	private float score;
	@JsonView(Views.Basic.class)
	@JsonInclude(Include.NON_EMPTY)
	private Map<String, Object> meta = new HashMap<>();
	@JsonView(Views.Basic.class)
	@JsonInclude(Include.NON_NULL)
	private T data;

	public String getType() { return type; }
	public Integer getId() { return id; }
	public float getScore() { return score; }
	public Map<String, Object> getMeta() { return meta; }
	public T getData() { return data; }

	public void setType(String type) { this.type = type; }
	public void setId(Integer id) { this.id = id; }
	public void setScore(float score) { this.score = score; }
	public void setMeta(Map<String, Object> meta) { this.meta = meta; }
	public void setData(T data) { this.data = data; }
}
