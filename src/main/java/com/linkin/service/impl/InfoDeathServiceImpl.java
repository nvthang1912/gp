package com.linkin.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

import com.linkin.dao.InfoDeathDao;
import com.linkin.entity.InfoDeath;
import com.linkin.model.InfoDeathDTO;
import com.linkin.model.SearchInfoDeathDTO;
import com.linkin.service.InfoDeathService;
import com.linkin.utils.DateTimeUtils;

@Service
@Transactional
public class InfoDeathServiceImpl implements InfoDeathService {

	@Autowired
	private InfoDeathDao infoDeathDao;

	@Autowired
	private SessionRegistry sessionRegistry;

	@Override
	public void addInfoDeath(InfoDeathDTO infoDeathDTO) {
		InfoDeath infoDeath = new InfoDeath();
		infoDeath.setId(infoDeathDTO.getId());
		infoDeath.setName(infoDeathDTO.getName());
		infoDeath.setSex(infoDeathDTO.getSex());
		infoDeath.setAddress(infoDeathDTO.getAddress());
		infoDeath.setHomeTown(infoDeathDTO.getHomeTown());
		infoDeath.setCemetery(infoDeathDTO.getCemetery());
		infoDeath.setBirthday(infoDeathDTO.getBirthday());
		infoDeath.setDeathDay(infoDeathDTO.getDeathDay());
		infoDeath.setNote(infoDeathDTO.getNote());

		infoDeathDao.addInfo(infoDeath);
	}

	@Override
	public void updateInfoDeath(InfoDeathDTO infoDeathDTO) {
		InfoDeath infoDeath = infoDeathDao.getById(infoDeathDTO.getId());
		if (infoDeath != null) {
			infoDeath.setName(infoDeathDTO.getName());
			infoDeath.setSex(infoDeathDTO.getSex());
			infoDeath.setAddress(infoDeathDTO.getAddress());
			infoDeath.setHomeTown(infoDeathDTO.getHomeTown());
			infoDeath.setBirthday(infoDeathDTO.getBirthday());
			infoDeath.setDeathDay(infoDeathDTO.getDeathDay());
			infoDeath.setNote(infoDeathDTO.getNote());

			infoDeathDao.updateInfo(infoDeath);
		}
	}

	@Override
	public void deleteInfoDeath(Long id) {
		InfoDeath infoDeath = infoDeathDao.getById(id);
		if (infoDeath != null) {
			infoDeathDao.deleteInfo(infoDeath);
		}

	}

	@Override
	public InfoDeathDTO getInfoById(Long id) {
		InfoDeath infoDeath = infoDeathDao.getById(id);
		if (infoDeath != null) {
			InfoDeathDTO infoDeathDTO = new InfoDeathDTO();
			infoDeathDTO.setId(infoDeath.getId());
			infoDeathDTO.setName(infoDeath.getName());
			infoDeathDTO.setSex(infoDeath.getSex());
			infoDeathDTO.setAddress(infoDeath.getAddress());
			infoDeathDTO.setHomeTown(infoDeath.getHomeTown());
			infoDeathDTO.setBirthday(infoDeath.getBirthday());
			infoDeathDTO.setDeathDay(infoDeath.getDeathDay());
			infoDeathDTO.setNote(infoDeath.getNote());

			return infoDeathDTO;
		}

		return null;
	}

	@Override
	public List<InfoDeathDTO> find(SearchInfoDeathDTO searchInfoDeathDTO) {
		List<InfoDeath> listInfoDeaths = infoDeathDao.find(searchInfoDeathDTO);
		List<InfoDeathDTO> infoDeathDTOs = new ArrayList<InfoDeathDTO>();
		for (InfoDeath infoDeath : listInfoDeaths) {
			InfoDeathDTO infoDeathDTO = new InfoDeathDTO();
			infoDeathDTO.setId(infoDeath.getId());
			infoDeathDTO.setName(infoDeath.getName());
			infoDeathDTO.setSex(infoDeath.getSex());
			infoDeathDTO.setAddress(infoDeath.getAddress());
			infoDeathDTO.setHomeTown(infoDeath.getHomeTown());
			infoDeathDTO.setCemetery(infoDeath.getCemetery());
			infoDeathDTO.setBirthday(infoDeath.getBirthday());
			infoDeathDTO.setDeathDay(infoDeath.getDeathDay());
			infoDeathDTO.setNote(infoDeath.getNote());
			infoDeathDTOs.add(infoDeathDTO);
		}
		return infoDeathDTOs;
	}

	@Override
	public Long count(SearchInfoDeathDTO searchInfoDeathDTO) {
		return infoDeathDao.count(searchInfoDeathDTO);
	}

	@Override
	public Long countTotal(SearchInfoDeathDTO searchInfoDeathDTO) {
		return infoDeathDao.countTotal(searchInfoDeathDTO);
	}

}
