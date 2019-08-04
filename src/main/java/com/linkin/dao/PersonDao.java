package com.linkin.dao;

import java.util.List;

import com.linkin.entity.Person;
import com.linkin.model.SearchPersonDTO;

public interface PersonDao {
	void add(Person person);

	void update(Person person);

	void delete(Long id);

	Person getByID(Long id);

	List<Person> find(SearchPersonDTO searchPersonDTO);

	Long count(SearchPersonDTO searchPersonDTO);

	Long countTotal(SearchPersonDTO searchPersonDTO);

}
