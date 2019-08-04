package com.linkin.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.linkin.dao.AlbumImageDao;
import com.linkin.entity.Album;
import com.linkin.entity.AlbumImage;
import com.linkin.model.AlbumImageDTO;
import com.linkin.model.SearchAlbumImageDTO;
import com.linkin.service.AlbumImageService;

@Repository
@Transactional
public class AlbumImageServiceImpl implements AlbumImageService {

	@Autowired
	private AlbumImageDao albumImageDao;

	@Override
	public void add(AlbumImageDTO albumImageDTO) {
		AlbumImage albumImage = new AlbumImage();

		albumImage.setImage(albumImageDTO.getImage());
		albumImage.setAlbum(new Album(albumImageDTO.getAlbumId()));
		albumImageDao.add(albumImage);
	}

	@Override
	public AlbumImageDTO getByID(Long id) {
		AlbumImage albumImage = albumImageDao.getByID(id);
		if (albumImage != null) {
			AlbumImageDTO albumImageDTO = new AlbumImageDTO();
			albumImageDTO.setId(albumImage.getId());
			albumImageDTO.setImage(albumImage.getImage());
			albumImageDTO.setAlbumId(albumImage.getAlbum().getId());

			return albumImageDTO;
		}
		return null;
	}

	@Override
	public List<AlbumImageDTO> find(SearchAlbumImageDTO searchAlbumImageDTO) {
		List<AlbumImage> albumImages = albumImageDao.find(searchAlbumImageDTO);
		List<AlbumImageDTO> albumImageDTOs = new ArrayList<AlbumImageDTO>();
		for (AlbumImage albumImage : albumImages) {
			AlbumImageDTO albumImageDTO = new AlbumImageDTO();
			albumImageDTO.setId(albumImage.getId());
			albumImageDTO.setImage(albumImage.getImage());
			albumImageDTO.setAlbumId(albumImage.getAlbum().getId());

			albumImageDTOs.add(albumImageDTO);
		}
		return albumImageDTOs;
	}

	@Override
	public Long count(SearchAlbumImageDTO searchAlbumImageDTO) {

		return albumImageDao.count(searchAlbumImageDTO);
	}

	@Override
	public Long countTotal(SearchAlbumImageDTO searchAlbumImageDTO) {

		return albumImageDao.countTotal(searchAlbumImageDTO);
	}

	@Override
	public void update(AlbumImageDTO albumImageDTO) {
		AlbumImage albumImage = albumImageDao.getByID(albumImageDTO.getId());
		if (albumImage != null) {
			albumImage.setImage(albumImageDTO.getImage());
			albumImage.setAlbum(new Album(albumImageDTO.getAlbumId()));
			albumImageDao.update(albumImage);
		}
	}

	@Override
	public void delete(Long id) {
		AlbumImage albumImage = albumImageDao.getByID(id);
		if (albumImage != null) {
			albumImageDao.delete(id);
		}
	}

}
