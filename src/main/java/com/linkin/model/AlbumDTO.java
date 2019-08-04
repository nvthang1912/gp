package com.linkin.model;

import java.io.Serializable;
import java.util.List;

public class AlbumDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private List<AlbumImageDTO> albumImageDTOs;
	private List<String> imageNames;

	public AlbumDTO() {
	}

	public AlbumDTO(Long id) {
		super();
		this.id = id;
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

	public List<AlbumImageDTO> getAlbumImageDTOs() {
		return albumImageDTOs;
	}

	public void setAlbumImageDTOs(List<AlbumImageDTO> albumImageDTOs) {
		this.albumImageDTOs = albumImageDTOs;
	}

	public List<String> getImageNames() {
		return imageNames;
	}

	public void setImageNames(List<String> imageNames) {
		this.imageNames = imageNames;
	}

}
