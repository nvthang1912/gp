package com.linkin.service;

import java.util.List;

import com.linkin.model.AlbumDTO;
import com.linkin.model.SearchAlbumDTO;

public interface AlbumService {
	void add(AlbumDTO albumDTO);

	void update(AlbumDTO albumDTO);

	void delete(Long id);

	AlbumDTO getByID(Long id);

	List<AlbumDTO> find(SearchAlbumDTO searchAlbumDTO);

	Long count(SearchAlbumDTO searchAlbumDTO);

	Long countTotal(SearchAlbumDTO searchAlbumDTO);
}
