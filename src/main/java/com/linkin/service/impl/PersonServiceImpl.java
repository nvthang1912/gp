package com.linkin.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.linkin.dao.PersonDao;
import com.linkin.entity.Person;
import com.linkin.model.FamilyTreeDTO;
import com.linkin.model.PersonDTO;
import com.linkin.model.SearchPersonDTO;
import com.linkin.service.PersonService;

@Repository
@Transactional
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonDao personDao;

	@Override
	public void add(PersonDTO personDTO) {
		Person person = new Person();
		person.setName(personDTO.getName());
		person.setImage(personDTO.getImage());
		person.setAddress(personDTO.getAddress());
		person.setPhone(personDTO.getPhone());
		person.setDeathday(personDTO.getDeathday());
		if (personDTO.getParentId() != null) {
			person.setParent(new Person(personDTO.getParentId()));
			Person father = personDao.getByID(personDTO.getParentId());
			person.setGeneration(father.getGeneration() + 1);
		} else {
			person.setParent(null);
			person.setGeneration(1);
		}

		personDao.add(person);
		
		personDTO.setId(person.getId());
	}

	@Override
	public PersonDTO getByID(Long id) {
		Person person = personDao.getByID(id);
		if (person != null) {
			PersonDTO personDTO = new PersonDTO();
			personDTO.setId(person.getId());
			personDTO.setName(person.getName());
			personDTO.setAddress(person.getAddress());
			personDTO.setPhone(person.getPhone());
			personDTO.setImage(person.getImage());
			personDTO.setDeathday(person.getDeathday());
			personDTO.setGeneration(person.getGeneration());
			if (person.getParent() != null) {
				personDTO.setParentId(person.getParent().getId());
				personDTO.setParentName(person.getParent().getName());
			}
			return personDTO;
		}
		return null;
	}
	
	@Override
	public List<PersonDTO> find(SearchPersonDTO searchPersonDTO) {
		List<Person> persons = personDao.find(searchPersonDTO);
		List<PersonDTO> personDTOs = new ArrayList<PersonDTO>();
		for (Person person : persons) {
			PersonDTO personDTO = new PersonDTO();
			personDTO.setId(person.getId());
			personDTO.setName(person.getName());
			personDTO.setAddress(person.getAddress());
			personDTO.setPhone(person.getPhone());
			personDTO.setImage(person.getImage());
			personDTO.setDeathday(person.getDeathday());
			personDTO.setGeneration(person.getGeneration());
			if (person.getParent() != null) {
				personDTO.setParentId(person.getParent().getId());
				personDTO.setParentName(person.getParent().getName());
			}
			personDTOs.add(personDTO);
		}
		return personDTOs;
	}

	@Override
	public FamilyTreeDTO getFamilyTree() {
		SearchPersonDTO searchPersonDTO = new SearchPersonDTO();
		searchPersonDTO.setFounder(true);
		List<Person> persons = personDao.find(searchPersonDTO);

		if (CollectionUtils.isEmpty(persons)) {
			return null;
		}

		return getPersonInfo(persons.get(0));
	}

	public FamilyTreeDTO getPersonInfo(Person person) {
		if (person.getChilds().isEmpty()) {
			FamilyTreeDTO familyTreeDTO = new FamilyTreeDTO();
			PersonDTO personDTO = convertDTO(person);
			familyTreeDTO.setText(personDTO);
			familyTreeDTO.setStackChildren(false);
			familyTreeDTO.setImage(personDTO.getImage());
			return familyTreeDTO;
		}

		FamilyTreeDTO familyTreeDTO = new FamilyTreeDTO();
		PersonDTO personDTO = convertDTO(person);
		familyTreeDTO.setText(personDTO);
		familyTreeDTO.setStackChildren(true);
		familyTreeDTO.setImage(personDTO.getImage());
		
		List<FamilyTreeDTO> childs = new ArrayList<>();
		familyTreeDTO.setChildren(childs);
		for (Person p : person.getChilds()) {
			childs.add(getPersonInfo(p));
		}

		return familyTreeDTO;
	}

	public PersonDTO convertDTO(Person person) {
		PersonDTO personDTO = new PersonDTO();
		personDTO.setId(person.getId());
		personDTO.setName(person.getName());
		personDTO.setAddress(person.getAddress());
		personDTO.setPhone(person.getPhone());
		personDTO.setImage(person.getImage());
		personDTO.setDeathday(person.getDeathday());
		personDTO.setGeneration(person.getGeneration());
		if (person.getParent() != null) { 
			personDTO.setParentName(person.getParent().getName());
			personDTO.setParentId(person.getParent().getId());
		}
		return personDTO;
	}

	@Override
	public Long count(SearchPersonDTO searchPersonDTO) {

		return personDao.count(searchPersonDTO);
	}

	@Override
	public Long countTotal(SearchPersonDTO searchPersonDTO) {

		return personDao.countTotal(searchPersonDTO);
	}

	@Override
	public void update(PersonDTO personDTO) {
		Person person = personDao.getByID(personDTO.getId());
		if (person != null) {
			person.setName(personDTO.getName());
			person.setImage(personDTO.getImage());
			person.setAddress(personDTO.getAddress());
			person.setPhone(personDTO.getPhone());
			person.setDeathday(personDTO.getDeathday());
			if (personDTO.getParentId() != null) {
				person.setParent(new Person(personDTO.getParentId()));
				Person father = personDao.getByID(personDTO.getParentId());
				person.setGeneration(father.getGeneration() + 1);
				for (Person child : person.getChilds()){
					child.setGeneration(person.getGeneration() + 1);
					update(convertDTO(child));
				}
			} else {
				person.setParent(null);
			}
			personDao.update(person);
		}
	}

	@Override
	public void delete(Long id) {
		Person person = personDao.getByID(id);
		if (person != null) {
			personDao.delete(id);
		}
	}

}
