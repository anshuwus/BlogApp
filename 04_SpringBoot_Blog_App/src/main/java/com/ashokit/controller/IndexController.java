package com.ashokit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ashokit.entity.Post;
import com.ashokit.service.IPostService;

@Controller
public class IndexController {
	@Autowired
	private IPostService service;
	
	@GetMapping("/")
	public String indexPage(Model model) {
		List<Post> list = service.getIndexPageData();
		model.addAttribute("list", list);
		return "index";
	}
}
