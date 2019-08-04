package com.linkin.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linkin.model.NewsDTO;
import com.linkin.model.ResponseDTO;
import com.linkin.model.SearchCategoryDTO;
import com.linkin.model.SearchNewsDTO;
import com.linkin.service.CategoryService;
import com.linkin.service.NewsService;

@Controller
@RequestMapping(value = "/admin")
public class NewsController {
	@Autowired
	NewsService newsService;
	@Autowired
	CategoryService categoryService;

	@GetMapping(value = "/news/list")
	public String listDictionary(Model model) {
		SearchCategoryDTO searchCategoryDTO = new SearchCategoryDTO();
		searchCategoryDTO.setStart(null);
		model.addAttribute("categorys", categoryService.find(searchCategoryDTO));
		return "admin/news/list-news";
	}

	@PostMapping(value = "/news/list")
	public ResponseEntity<ResponseDTO<NewsDTO>> listDictionary(@RequestBody SearchNewsDTO searchNewsDTO) {
		ResponseDTO<NewsDTO> responseDTO = new ResponseDTO<>();
		responseDTO.setData(newsService.find(searchNewsDTO));
		responseDTO.setRecordsFiltered(newsService.count(searchNewsDTO));
		responseDTO.setRecordsTotal(newsService.countTotal(searchNewsDTO));

		return new ResponseEntity<ResponseDTO<NewsDTO>>(responseDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/news/delete/{id}")
	public ResponseEntity<String> delNews(@PathVariable(name = "id") Long id) {
		newsService.delete(id);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@GetMapping(value = "/news/delete-multi/{ids}")
	public ResponseEntity<String> delDictionary(@PathVariable(name = "ids") List<Long> ids) {
		for (Long id : ids) {
			try {
				newsService.delete(id);
			} catch (Exception e) {
			}
		}
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@PostMapping("/news/add")
	public @ResponseBody NewsDTO add(@RequestBody NewsDTO newsDTO) {
		newsService.add(newsDTO);
		return newsDTO;
	}

	@PostMapping("/news/update")
	public @ResponseBody NewsDTO update(@RequestBody NewsDTO newDTO) {
		newsService.update(newDTO);
		return newDTO;
	}
}
