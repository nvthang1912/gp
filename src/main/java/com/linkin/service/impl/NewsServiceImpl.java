package com.linkin.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.dao.NewsDao;
import com.linkin.entity.Category;
import com.linkin.entity.News;
import com.linkin.model.NewsDTO;
import com.linkin.model.SearchNewsDTO;
import com.linkin.service.NewsService;
import com.linkin.utils.DateTimeUtils;

@Service
@Transactional
public class NewsServiceImpl implements NewsService {
	@Autowired
	NewsDao newssDao;

	@Override
	public void add(NewsDTO newsDTO) {
		News listNew = new News();
		listNew.setContent(newsDTO.getContent());
		listNew.setTitle(newsDTO.getTitle());
		listNew.setImage(newsDTO.getImage());
		if (newsDTO.getCategoryId() != null) {
			listNew.setCategory(new Category(newsDTO.getCategoryId()));
		}
		newssDao.add(listNew);
	}
	@Override
	public void update(NewsDTO newsDTO) {
		News listNew = newssDao.getById(newsDTO.getId());
		if (listNew != null) {
			if (StringUtils.isNotBlank(newsDTO.getImage())) {
				listNew.setImage(newsDTO.getImage());
			}
			listNew.setContent(newsDTO.getContent());
			listNew.setTitle(newsDTO.getTitle());
			if (newsDTO.getCategoryId() != null) {
				listNew.setCategory(new Category(newsDTO.getCategoryId()));
			}

			newssDao.update(listNew);
		}
	}
	@Override
	public void delete(Long id) {
		News listNew = newssDao.getById(id);
		if (listNew != null) {
			newssDao.delete(id);
		}
	}

	@Override
	public NewsDTO getById(Long id) {
		News listNew = newssDao.getById(id);
		if (listNew != null) {
			NewsDTO newsDTO = new NewsDTO();
			newsDTO.setId(listNew.getId());
			newsDTO.setContent(listNew.getContent());
			newsDTO.setTitle(listNew.getTitle());
			newsDTO.setImage(listNew.getImage());
			newsDTO.setCreatedDate(DateTimeUtils.formatDate(listNew.getCreatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM_SS));
			if (listNew.getCategory() != null) {
				newsDTO.setCategoryId(listNew.getCategory().getId());
				newsDTO.setCategoryName(listNew.getCategory().getName());
			}
			return newsDTO;
		}
		return null;
	}

	@Override
	public List<NewsDTO> find(SearchNewsDTO searchNewsDTO) {
		List<News> listNew = newssDao.find(searchNewsDTO);
		List<NewsDTO> newsDTOs = new ArrayList<NewsDTO>();
		for (News news : listNew) {
			NewsDTO newsDTO = new NewsDTO();
			newsDTO.setId(news.getId());
			newsDTO.setContent(news.getContent());
			newsDTO.setTitle(news.getTitle());
			newsDTO.setImage(news.getImage());
			newsDTO.setCreatedDate(DateTimeUtils.formatDate(news.getCreatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM_SS));
			if (news.getCategory() != null) {
				newsDTO.setCategoryId(news.getCategory().getId());
				newsDTO.setCategoryName(news.getCategory().getName());
			}
			newsDTOs.add(newsDTO);
		}
		return newsDTOs;
	}

	@Override
	public Long count(SearchNewsDTO searchNewsDTO) {
		return newssDao.count(searchNewsDTO);
	}

	@Override
	public Long countTotal(SearchNewsDTO searchNewsDTO) {
		return newssDao.countTotal(searchNewsDTO);
	}

}
