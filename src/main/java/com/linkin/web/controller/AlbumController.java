package com.linkin.web.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linkin.model.AlbumDTO;
import com.linkin.model.AlbumImageDTO;
import com.linkin.model.ResponseDTO;
import com.linkin.model.SearchAlbumDTO;
import com.linkin.service.AlbumImageService;
import com.linkin.service.AlbumService;

@Controller
@RequestMapping(value = "/admin")
public class AlbumController {

	@Autowired
	AlbumService albumService;
	@Autowired
	AlbumImageService albumImageService;

	@GetMapping(value = "/album/list")
	public String listAlbum(Model model) {
		return "admin/album/list-album";
	}

	@PostMapping(value = "/album/list")
	public ResponseEntity<ResponseDTO<AlbumDTO>> listAlbum(@RequestBody SearchAlbumDTO searchAlbumDTO) {
		ResponseDTO<AlbumDTO> responseDTO = new ResponseDTO<>();
		responseDTO.setData(albumService.find(searchAlbumDTO));
		responseDTO.setRecordsFiltered(albumService.count(searchAlbumDTO));
		responseDTO.setRecordsTotal(albumService.countTotal(searchAlbumDTO));

		return new ResponseEntity<ResponseDTO<AlbumDTO>>(responseDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/album/delete/{id}")
	public ResponseEntity<String> delAlbum(@PathVariable(name = "id") Long id) {
		albumService.delete(id);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@GetMapping(value = "/album/delete-multi/{ids}")
	public ResponseEntity<String> delAlbum(@PathVariable(name = "ids") List<Long> ids) {
		for (Long id : ids) {
			try {
				albumService.delete(id);
			} catch (Exception e) {

			}
		}
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@PostMapping("/album/add")
	public @ResponseBody AlbumDTO addAlbum(@RequestBody AlbumDTO albumDTO) {
		albumService.add(albumDTO);
		// save image
		if (albumDTO.getImageNames() != null && !albumDTO.getImageNames().isEmpty()) {
			for (String fileName : albumDTO.getImageNames()) {
				if (StringUtils.isNotBlank(fileName)) {
					AlbumImageDTO imageDTO = new AlbumImageDTO();
					imageDTO.setImage(fileName);
					imageDTO.setAlbumId(albumDTO.getId());
					albumImageService.add(imageDTO);
				}
			}
		}
		return albumDTO;
	}

	@PutMapping("/album/update")
	public @ResponseBody AlbumDTO updateAlbum(@RequestBody AlbumDTO albumDTO) {
		albumService.update(albumDTO);
		return albumDTO;
	}
}
