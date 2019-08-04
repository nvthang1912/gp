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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linkin.model.AlbumImageDTO;
import com.linkin.model.ResponseDTO;
import com.linkin.model.SearchAlbumImageDTO;
import com.linkin.service.AlbumImageService;

@Controller
@RequestMapping(value = "/admin")
public class AlbumImageController {

	@Autowired
	AlbumImageService albumImageService;

	@GetMapping(value = "/albumImage/list")
	public String listAlbumImage(Model model) {
		return "admin/albumImage/list-albumImage";
	}

	@PostMapping(value = "/albumImage/list")
	public ResponseEntity<ResponseDTO<AlbumImageDTO>> listAlbumImage(
			@RequestBody SearchAlbumImageDTO searchAlbumImageDTO) {
		ResponseDTO<AlbumImageDTO> responseDTO = new ResponseDTO<>();
		responseDTO.setData(albumImageService.find(searchAlbumImageDTO));
		responseDTO.setRecordsFiltered(albumImageService.count(searchAlbumImageDTO));
		responseDTO.setRecordsTotal(albumImageService.countTotal(searchAlbumImageDTO));

		return new ResponseEntity<ResponseDTO<AlbumImageDTO>>(responseDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/albumImage/delete/{id}")
	public ResponseEntity<String> delAlbumImage(@PathVariable(name = "id") Long id) {
		albumImageService.delete(id);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@GetMapping(value = "/albumImage/delete-multi/{ids}")
	public ResponseEntity<String> delAlbumImage(@PathVariable(name = "ids") List<Long> ids) {
		for (Long id : ids) {
			try {
				albumImageService.delete(id);
			} catch (Exception e) {

			}
		}
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@PostMapping("/albumImage/add")
	public @ResponseBody AlbumImageDTO addAlbumImage(@RequestBody AlbumImageDTO albumImageDTO) {
		// save image
		if (albumImageDTO.getImageNames() != null && !albumImageDTO.getImageNames().isEmpty()) {
			for (String fileName : albumImageDTO.getImageNames()) {
				if (StringUtils.isNotBlank(fileName)) {
					AlbumImageDTO imageDTO = new AlbumImageDTO();
					imageDTO.setImage(fileName);
					imageDTO.setAlbumId(albumImageDTO.getAlbumId());
					albumImageService.add(imageDTO);
				}
			}
		}

		return albumImageDTO;
	}

	@PostMapping("/albumImage/update")
	public @ResponseBody AlbumImageDTO updateAlbumImage(@RequestBody AlbumImageDTO albumImageDTO) {
		albumImageService.update(albumImageDTO);
		return albumImageDTO;
	}
}
