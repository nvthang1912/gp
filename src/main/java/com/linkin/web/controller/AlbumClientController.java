package com.linkin.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linkin.model.AlbumDTO;
import com.linkin.model.SearchAlbumDTO;
import com.linkin.service.AlbumImageService;
import com.linkin.service.AlbumService;

@Controller
public class AlbumClientController {
	@Autowired
	AlbumService albumService;
	@Autowired
	AlbumImageService albumImageService;

	@GetMapping("/album")
	public String album() {
		return "client/album/album";
	}

	@PostMapping("/album/list")
	public @ResponseBody List<AlbumDTO> albumList(@RequestBody SearchAlbumDTO searchAlbumDTO) {
		return albumService.find(searchAlbumDTO);
	}

	@GetMapping("/album/{id}")
	public String album(@PathVariable(value = "id") Long id, Model model) {
		AlbumDTO albumDTO = albumService.getByID(id);
		model.addAttribute("album", albumDTO);
		return "client/album/single-album";
	}

}
