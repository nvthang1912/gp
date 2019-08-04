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

import com.linkin.model.PersonDTO;
import com.linkin.model.ResponseDTO;
import com.linkin.model.SearchPersonDTO;
import com.linkin.service.PersonService;

@Controller
@RequestMapping(value = "/admin")
public class PersonController {
	@Autowired
	PersonService personService;

	@GetMapping(value = "/person/list")
	public String listPerson(Model model) {
		SearchPersonDTO searchPersonDTO = new SearchPersonDTO();
		searchPersonDTO.setStart(null);
		model.addAttribute("parents", personService.find(searchPersonDTO));
		return "admin/person/list-person";
	}

	@PostMapping(value = "/person/list")
	public ResponseEntity<ResponseDTO<PersonDTO>> listPerson(@RequestBody SearchPersonDTO searchPersonDTO) {
		ResponseDTO<PersonDTO> responseDTO = new ResponseDTO<>();
		responseDTO.setData(personService.find(searchPersonDTO));
		responseDTO.setRecordsFiltered(personService.count(searchPersonDTO));
		responseDTO.setRecordsTotal(personService.countTotal(searchPersonDTO));

		return new ResponseEntity<ResponseDTO<PersonDTO>>(responseDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/person/delete/{id}")
	public ResponseEntity<String> delPerson(@PathVariable(name = "id") Long id) {
		personService.delete(id);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@GetMapping(value = "/person/delete-multi/{ids}")
	public ResponseEntity<String> delPerson(@PathVariable(name = "ids") List<Long> ids) {
		for (Long id : ids) {
			try {
				personService.delete(id);
			} catch (Exception e) {

			}
		}
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@PostMapping("/person/add")
	public @ResponseBody PersonDTO addPerson(@RequestBody PersonDTO personDTO) {
		personService.add(personDTO);
		return personDTO;
	}

	@PutMapping("/person/update")
	public @ResponseBody PersonDTO updatePerson(@RequestBody PersonDTO personDTO) {
		personService.update(personDTO);
		return personDTO;
	}
}
