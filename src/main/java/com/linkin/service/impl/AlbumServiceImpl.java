package com.linkin.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.linkin.dao.AlbumDao;
import com.linkin.entity.Album;
import com.linkin.entity.AlbumImage;
import com.linkin.model.AlbumDTO;
import com.linkin.model.AlbumImageDTO;
import com.linkin.model.SearchAlbumDTO;
import com.linkin.service.AlbumService;

@Repository
@Transactional
public class AlbumServiceImpl implements AlbumService {

	@Autowired
	private AlbumDao albumDao;

	@Override
	public void add(AlbumDTO albumDTO) {
		Album album = new Album();
		album.setName(albumDTO.getName());
		albumDao.add(album);

		albumDTO.setId(album.getId());
	}

	@Override
	public AlbumDTO getByID(Long id) {
		Album album = albumDao.getByID(id);
		if (album != null) {
			AlbumDTO albumDTO = new AlbumDTO();
			albumDTO.setId(album.getId());
			albumDTO.setName(album.getName());
			
			List<AlbumImageDTO> albumImageDTOs = new ArrayList<AlbumImageDTO>();
			for (AlbumImage albumImage : album.getAlbumImages()) {
				AlbumImageDTO albumImageDTO = new AlbumImageDTO();
				albumImageDTO.setId(albumImage.getId());
				albumImageDTO.setImage(albumImage.getImage());
				albumImageDTO.setAlbumId(albumImage.getAlbum().getId());
				albumImageDTOs.add(albumImageDTO);
			}
			albumDTO.setAlbumImageDTOs(albumImageDTOs);
			return albumDTO;
		}
		return null;
	}

	@Override
	public List<AlbumDTO> find(SearchAlbumDTO searchAlbumDTO) {
		List<Album> albums = albumDao.find(searchAlbumDTO);
		List<AlbumDTO> albumDTOs = new ArrayList<AlbumDTO>();
		for (Album album : albums) {
			AlbumDTO albumDTO = new AlbumDTO();
			albumDTO.setId(album.getId());
			albumDTO.setName(album.getName());
			List<AlbumImageDTO> albumImageDTOs = new ArrayList<AlbumImageDTO>();
			for (AlbumImage albumImage : album.getAlbumImages()) {
				AlbumImageDTO albumImageDTO = new AlbumImageDTO();
				albumImageDTO.setId(albumImage.getId());
				albumImageDTO.setImage(albumImage.getImage());
				albumImageDTO.setAlbumId(albumImage.getAlbum().getId());
				albumImageDTOs.add(albumImageDTO);
			}
			albumDTO.setAlbumImageDTOs(albumImageDTOs);

			albumDTOs.add(albumDTO);
		}
		return albumDTOs;
	}

	@Override
	public Long count(SearchAlbumDTO searchAlbumDTO) {

		return albumDao.count(searchAlbumDTO);
	}

	@Override
	public Long countTotal(SearchAlbumDTO searchAlbumDTO) {

		return albumDao.countTotal(searchAlbumDTO);
	}

	@Override
	public void update(AlbumDTO albumDTO) {
		Album album = albumDao.getByID(albumDTO.getId());
		if (album != null) {
			album.setName(albumDTO.getName());
			albumDao.update(album);
		}
	}

	@Override
	public void delete(Long id) {
		Album album = albumDao.getByID(id);
		if (album != null) {
			albumDao.delete(id);
		}
	}

}
