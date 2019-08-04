package com.linkin.dao;

import java.util.List;

import com.linkin.entity.Album;
import com.linkin.model.SearchAlbumDTO;

public interface AlbumDao {
	void add(Album album);

	void update(Album album);

	void delete(Long id);

	Album getByID(Long id);

	List<Album> find(SearchAlbumDTO searchAlbumDTO);

	Long count(SearchAlbumDTO searchAlbumDTO);

	Long countTotal(SearchAlbumDTO searchAlbumDTO);

}
