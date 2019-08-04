package com.linkin.model;

public class SearchNewsDTO extends SearchDTO {
	private static final long serialVersionUID = 1L;
	
	private Long categoryId;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
}
