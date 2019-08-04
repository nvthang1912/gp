package com.linkin.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linkin.model.FamilyTreeDTO;
import com.linkin.model.PersonDTO;
import com.linkin.model.ResponseDTO;
import com.linkin.model.SearchPersonDTO;
import com.linkin.service.PersonService;

@Controller
public class PersonClientController {
	@Autowired
	PersonService personService;

	@GetMapping(value = "/gia-pha")
	public String listPerson(Model model) {
		return "client/person/person";
	}

	@PostMapping(value = "/gia-pha")
	public ResponseEntity<ResponseDTO<PersonDTO>> listPerson(@RequestBody SearchPersonDTO searchPersonDTO) {
		ResponseDTO<PersonDTO> responseDTO = new ResponseDTO<>();
		responseDTO.setData(personService.find(searchPersonDTO));
		responseDTO.setRecordsFiltered(personService.count(searchPersonDTO));
		responseDTO.setRecordsTotal(personService.countTotal(searchPersonDTO));

		return new ResponseEntity<ResponseDTO<PersonDTO>>(responseDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/family-tree")
	public @ResponseBody FamilyTreeDTO listFamilyTree() {
		return personService.getFamilyTree();
	}
}
