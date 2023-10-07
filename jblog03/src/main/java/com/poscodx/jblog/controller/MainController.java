package com.poscodx.jblog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.vo.BlogVo;

@Controller
public class MainController {
	
	@Autowired
	public BlogService blogService;
	
	@RequestMapping("/")
	public String index(Model model) {
		List<BlogVo> blogList = blogService.getBlogList();
		model.addAttribute("blogList", blogList);
		return "main/index";
	}
}
