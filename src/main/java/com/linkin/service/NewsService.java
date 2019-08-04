package com.linkin.service;

import java.util.List;

import com.linkin.model.NewsDTO;
import com.linkin.model.SearchNewsDTO;

public interface NewsService {
	void add(NewsDTO newsDTO);

	void update(NewsDTO newsDTO);

	void delete(Long id);

	NewsDTO getById(Long id);

	List<NewsDTO> find(SearchNewsDTO searchNewsDTO);

	Long count(SearchNewsDTO searchNewsDTO);

	Long countTotal(SearchNewsDTO searchNewsDTO);
}
