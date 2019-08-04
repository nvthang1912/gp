package com.linkin.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.linkin.service.AlbumService;

@Controller
public class CommonWebController extends BaseWebController {

	@Autowired
	AlbumService albumService;

	@GetMapping(value = { "/home-page", "/" })
	public String homePage(Model model) {
		return "client/index";
	}
	
	@GetMapping(value = { "/about" })
	public String about(Model model) {
		return "client/about/about";
	}
	
	@GetMapping(value = { "/contact" })
	public String contact(Model model) {
		return "client/contact/contact";
	}
	
	@GetMapping(value = { "/projects" })
	public String projects(Model model) {
		return "client/projects/projects";
	}
	
	@GetMapping(value = { "/services" })
	public String services(Model model) {
		return "client/services/services";
	}
	
	@RequestMapping(value = { "/access-deny" })
	private String deny(HttpServletRequest request) {
		return getViewName(request, "admin/error/deny");
	}

}
