package com.linkin.model;

public class SearchAlbumImageDTO extends SearchDTO {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String image;

	public SearchAlbumImageDTO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
