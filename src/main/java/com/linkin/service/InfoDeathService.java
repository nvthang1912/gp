package com.linkin.service;

import java.util.List;

import com.linkin.model.InfoDeathDTO;
import com.linkin.model.SearchInfoDeathDTO;

public interface InfoDeathService {

	void addInfoDeath(InfoDeathDTO infoDeathDTO);

	void updateInfoDeath(InfoDeathDTO infoDeathDTO);

	void deleteInfoDeath(Long id);

	InfoDeathDTO getInfoById(Long id);

	List<InfoDeathDTO> find(SearchInfoDeathDTO searchInfoDeathDTO);

	Long count(SearchInfoDeathDTO searchInfoDeathDTO);

	Long countTotal(SearchInfoDeathDTO searchInfoDeathDTO);
}
