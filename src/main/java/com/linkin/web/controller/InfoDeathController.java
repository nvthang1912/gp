package com.linkin.web.controller;

import java.util.List;

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

import com.linkin.model.InfoDeathDTO;
import com.linkin.model.ResponseDTO;
import com.linkin.model.SearchInfoDeathDTO;
import com.linkin.service.InfoDeathService;

@Controller
@RequestMapping(value = "/admin")
public class InfoDeathController {

	@Autowired
	InfoDeathService infoDeathService;

	@GetMapping(value = "/info-death/list")
	public String list(Model model) {
		return "admin/info-death/info-list";
	}

	@PostMapping(value = "/info-death/list")
	public ResponseEntity<ResponseDTO<InfoDeathDTO>> list(@RequestBody SearchInfoDeathDTO searchInfoDeathDTO) {
		ResponseDTO<InfoDeathDTO> responseDTO = new ResponseDTO<>();
		responseDTO.setData(infoDeathService.find(searchInfoDeathDTO));
		responseDTO.setRecordsFiltered(infoDeathService.count(searchInfoDeathDTO));
		responseDTO.setRecordsFiltered(infoDeathService.countTotal(searchInfoDeathDTO));

		return new ResponseEntity<ResponseDTO<InfoDeathDTO>>(responseDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/info-death/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
		infoDeathService.deleteInfoDeath(id);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@GetMapping(value = "/info-death/delete-multi/{ids}")
	public ResponseEntity<String> delete(@PathVariable(name = "ids") List<Long> ids) {
		for (Long id : ids) {
			try {
				infoDeathService.deleteInfoDeath(id);
			} catch (Exception e) {

			}
		}
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@PostMapping("/info-death/add")
	public @ResponseBody InfoDeathDTO add(@RequestBody InfoDeathDTO infoDeathDTO) {
		infoDeathService.addInfoDeath(infoDeathDTO);
		return infoDeathDTO;
	}

	@PutMapping("/info-death/update")
	public @ResponseBody InfoDeathDTO updateDictionary(@RequestBody InfoDeathDTO infoDeathDTO) {
		infoDeathService.updateInfoDeath(infoDeathDTO);
		return infoDeathDTO;
	}
}
