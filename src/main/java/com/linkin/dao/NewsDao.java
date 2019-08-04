package com.linkin.dao;

import java.util.List;

import com.linkin.entity.News;
import com.linkin.model.SearchNewsDTO;

public interface NewsDao {
	void add(News news);

	void update(News news);

	void delete(Long id);

	News getById(Long id);

	List<News> find(SearchNewsDTO searchNewsDTO);

	Long count(SearchNewsDTO searchNewsDTO);

	Long countTotal(SearchNewsDTO searchNewsDTO);
}
