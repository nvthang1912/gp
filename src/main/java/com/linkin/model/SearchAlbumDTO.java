package com.linkin.model;

public class SearchAlbumDTO extends SearchDTO {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;

	public SearchAlbumDTO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
