
package com.linkin.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.dao.CategoryDao;
import com.linkin.entity.Category;
import com.linkin.model.CategoryDTO;
import com.linkin.model.SearchCategoryDTO;
import com.linkin.service.CategoryService;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryDao categoryDao;

	@Override
	public void add(CategoryDTO categoryDTO) {
		Category syllabus = new Category();
		syllabus.setName(categoryDTO.getName());
		categoryDao.add(syllabus);

	}

	@Override
	public void update(CategoryDTO categoryDTO) {
		Category syllabus = categoryDao.getById(categoryDTO.getId());
		if (syllabus != null) {
			syllabus.setName(categoryDTO.getName());
			categoryDao.update(syllabus);
		}
	}

	@Override
	public void delete(Long id) {
		Category syllabus = categoryDao.getById(id);
		if (syllabus != null) {
			categoryDao.delete(id);
		}
	}

	@Override
	public CategoryDTO getById(Long id) {
		Category syllabus = categoryDao.getById(id);
		if (syllabus != null) {
			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setId(syllabus.getId());
			categoryDTO.setName(syllabus.getName());
			return categoryDTO;
		}
		return null;
	}

	@Override
	public List<CategoryDTO> find(SearchCategoryDTO searchCategoryDTO) {
		List<Category> listSyllabus = categoryDao.find(searchCategoryDTO);
		List<CategoryDTO> SyllabusDTOS = new ArrayList<CategoryDTO>();
		for (Category Category : listSyllabus) {
			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setId(Category.getId());
			categoryDTO.setName(Category.getName());
			SyllabusDTOS.add(categoryDTO);
		}
		return SyllabusDTOS;
	}

	@Override
	public Long count(SearchCategoryDTO searchCategoryDTO) {
		return categoryDao.count(searchCategoryDTO);

	}

	@Override
	public Long countTotal(SearchCategoryDTO searchCategoryDTO) {
		return categoryDao.countTotal(searchCategoryDTO);
	}

}
