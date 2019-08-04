package com.linkin.service;

import java.util.List;

import com.linkin.model.AlbumImageDTO;
import com.linkin.model.SearchAlbumImageDTO;

public interface AlbumImageService {
	void add(AlbumImageDTO albumImageDTO);

	void update(AlbumImageDTO albumImageDTO);

	void delete(Long id);

	AlbumImageDTO getByID(Long id);

	List<AlbumImageDTO> find(SearchAlbumImageDTO searchAlbumImageDTO);

	Long count(SearchAlbumImageDTO searchAlbumImageDTO);

	Long countTotal(SearchAlbumImageDTO searchAlbumImageDTO);
}
