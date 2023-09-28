package com.poscodx.jblog.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.FileuploadService;
import com.poscodx.jblog.vo.BlogVo;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {
	
	@Autowired
	private BlogService blogService;

	@Autowired
	private FileuploadService fileuploadService;
	
	@RequestMapping({"", "/{categoryNo}", "/{categoryNo}/{postNo}"})
	public String index(
			@PathVariable("id")String blogId,
			@PathVariable("categoryNo") Optional <String> categoryNo, 
			@PathVariable("postNo")Optional <String> postNo,
			Model model) {
		BlogVo blogVo = blogService.getBlog(blogId);
		model.addAttribute("blogVo", blogVo);
		return "blog/main";
	}

	@RequestMapping(value = "/admin/basic", method=RequestMethod.GET)
	public String adminBasic(@PathVariable("id")String blogId, Model model) {
		BlogVo blogVo = blogService.getBlog(blogId);
		model.addAttribute("blogVo", blogVo);
		return "blog/admin-basic";
	}
	
	@RequestMapping(value = "/admin/basic/update", method=RequestMethod.POST)
	public String adminBasicUpdate(@PathVariable("id")String blogId,
								   @RequestParam("file") MultipartFile file,
								   @RequestParam("title") String title,
								   BlogVo blogVo) {
		String url = fileuploadService.restore(file);
		blogVo.setImage(url);
		blogVo.setTitle(title);
		blogVo.setBlogId(blogId);
		
		blogService.UpdateBlog(blogVo);
		return "redirect:/" + blogId + "/admin/basic";
	}
	
	@RequestMapping("/admin/category")
	public String adminCategory(@PathVariable("id")String blogId) {
		return "blog/admin-category";
	}
	
	@RequestMapping("/admin/write")
	public String adminWrite(@PathVariable("id")String blogId) {
		return "blog/admin-write";
	}
}
