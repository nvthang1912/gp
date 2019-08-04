package com.linkin.service;

import java.util.List;

import com.linkin.model.FamilyTreeDTO;
import com.linkin.model.PersonDTO;
import com.linkin.model.SearchPersonDTO;

public interface PersonService {
	void add(PersonDTO personDTO);

	void update(PersonDTO personDTO);

	void delete(Long id);

	PersonDTO getByID(Long id);

	List<PersonDTO> find(SearchPersonDTO searchPersonDTO);

	Long count(SearchPersonDTO searchPersonDTO);

	Long countTotal(SearchPersonDTO searchPersonDTO);

	FamilyTreeDTO getFamilyTree();
}
