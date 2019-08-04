package com.linkin.dao;

import java.util.List;

import com.linkin.entity.AlbumImage;
import com.linkin.model.SearchAlbumImageDTO;

public interface AlbumImageDao {
	void add(AlbumImage albumImage);

	void update(AlbumImage albumImage);

	void delete(Long id);

	AlbumImage getByID(Long id);

	List<AlbumImage> find(SearchAlbumImageDTO searchAlbumImageDTO);

	Long count(SearchAlbumImageDTO searchAlbumImageDTO);

	Long countTotal(SearchAlbumImageDTO searchAlbumImageDTO);

}
