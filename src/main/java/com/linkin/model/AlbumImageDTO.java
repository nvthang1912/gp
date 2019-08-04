package com.linkin.model;

import java.io.Serializable;
import java.util.List;

public class AlbumImageDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String image;
	private Long albumId;
	private List<String> imageNames;

	public AlbumImageDTO() {
	}

	public AlbumImageDTO(Long id) {
		super();
		this.id = id;
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

	public Long getAlbumId() {
		return albumId;
	}

	public void setAlbumId(Long albumId) {
		this.albumId = albumId;
	}

	public List<String> getImageNames() {
		return imageNames;
	}

	public void setImageNames(List<String> imageNames) {
		this.imageNames = imageNames;
	}

}
