package com.linkin.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.linkin.model.InfoDeathDTO;
import com.linkin.model.ResponseDTO;
import com.linkin.model.SearchInfoDeathDTO;
import com.linkin.service.InfoDeathService;

@Controller
public class InfoDeathClientController {
	@Autowired
	InfoDeathService InfoDeathService;

	@GetMapping(value = "/ngay-gio")
	public String listInfoDeath(Model model) {
		return "client/info-death/info-death";
	}

	@PostMapping(value = "/ngay-gio")
	public ResponseEntity<ResponseDTO<InfoDeathDTO>> listInfoDeath(@RequestBody SearchInfoDeathDTO searchinfoDeathDTO) {
		ResponseDTO<InfoDeathDTO> responseDTO = new ResponseDTO<>();
		responseDTO.setData(InfoDeathService.find(searchinfoDeathDTO));
		responseDTO.setRecordsFiltered(InfoDeathService.count(searchinfoDeathDTO));
		responseDTO.setRecordsTotal(InfoDeathService.countTotal(searchinfoDeathDTO));

		return new ResponseEntity<ResponseDTO<InfoDeathDTO>>(responseDTO, HttpStatus.OK);
	}
}
