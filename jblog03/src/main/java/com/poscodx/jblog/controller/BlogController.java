package com.poscodx.jblog.controller;

import java.util.List;
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
import com.poscodx.jblog.service.CategoryService;
import com.poscodx.jblog.service.FileuploadService;
import com.poscodx.jblog.service.PostService;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private PostService postService;
	
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
	
	@RequestMapping(value = "/admin/category", method=RequestMethod.GET)
	public String adminCategory(@PathVariable("id")String blogId, Model model) {
		BlogVo blogVo = blogService.getBlog(blogId);
		model.addAttribute("blogVo", blogVo);
		List<CategoryVo> list = categoryService.getCategory(blogId);
		model.addAttribute("list", list);
		return "blog/admin-category";
	}

	@RequestMapping(value = "/admin/category", method=RequestMethod.POST)
	public String adminCategory(@PathVariable("id")String blogId,
								@RequestParam("name") String name,
								@RequestParam("description") String description,
								CategoryVo categoryVo) {
		categoryVo.setBlogId(blogId);
		categoryVo.setName(name);
		categoryVo.setDescription(description);
		categoryService.insertCategory(categoryVo);
		return "redirect:/" + blogId + "/admin/category";
	}
	
	@RequestMapping("/admin/category/delete/{no}")
	public String delete(@PathVariable("id")String blogId,
						 @PathVariable("no") Long no,
						 CategoryVo categoryVo) {
		categoryVo.setNo(no);
		categoryVo.setBlogId(blogId);
		categoryService.deleteCategory(categoryVo);
		return "redirect:/" + blogId + "/admin/category";
	}
	
	@RequestMapping("/admin/write")
	public String adminWrite(@PathVariable("id")String blogId, Model model) {
		BlogVo blogVo = blogService.getBlog(blogId);
		model.addAttribute("blogVo", blogVo);
		List<CategoryVo> list = categoryService.getCategory(blogId);
		model.addAttribute("list", list);
		return "blog/admin-write";
	}

	@RequestMapping(value = "/admin/write", method=RequestMethod.POST)
	public String adminWrite(@PathVariable("id")String blogId,
							 @RequestParam("title") String title,
							 @RequestParam("content") String content,
							 @RequestParam("category") String categoryName,
							 PostVo postVo, CategoryVo categoryVo) {
		postVo.setTitle(title);
		postVo.setContents(content);
		categoryVo.setBlogId(blogId);
		categoryVo.setName(categoryName);
		postService.insertPost(postVo, categoryVo);
		return "redirect:/" + blogId + "/admin/write";
	}
}
