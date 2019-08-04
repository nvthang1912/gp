package com.linkin.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linkin.model.NewsDTO;
import com.linkin.model.SearchCategoryDTO;
import com.linkin.model.SearchNewsDTO;
import com.linkin.service.CategoryService;
import com.linkin.service.NewsService;

@Controller
public class NewsClientController {
	@Autowired
	NewsService newsService;
	
	@Autowired
	CategoryService categoryService;

	@GetMapping("/tin-tuc")
	public String news(Model model) {
		SearchCategoryDTO searchCategoryDTO = new SearchCategoryDTO();
		searchCategoryDTO.setStart(null);
		model.addAttribute("categories", categoryService.find(searchCategoryDTO));
		return "client/news/news";
	}

	@PostMapping("/news/list")
	public @ResponseBody List<NewsDTO> newsList(@RequestBody SearchNewsDTO searchNewsDTO) {
		return newsService.find(searchNewsDTO);
	}
	
	@GetMapping("/tin-tuc/{id}")
	public String news(@PathVariable(value = "id") Long id, Model model) {
		NewsDTO newsDTO = newsService.getById(id);
		model.addAttribute("news", newsDTO);
		return "client/news/single-news";
	}

}
