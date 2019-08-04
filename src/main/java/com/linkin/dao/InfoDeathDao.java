package com.linkin.dao;

import java.util.List;

import com.linkin.entity.InfoDeath;
import com.linkin.model.SearchInfoDeathDTO;

public interface InfoDeathDao {

	void addInfo(InfoDeath infoDeath);
	
	void updateInfo(InfoDeath infoDeath);
	
	void deleteInfo(InfoDeath infoDeath);
	
	InfoDeath getById(Long id);
	
	List<InfoDeath> find(SearchInfoDeathDTO searchInfoDeathDTO);

	Long count(SearchInfoDeathDTO searchInfoDeathDTO);

	Long countTotal(SearchInfoDeathDTO searchInfoDeathDTO);
	

	
}
