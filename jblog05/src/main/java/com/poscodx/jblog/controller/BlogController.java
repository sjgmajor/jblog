package com.poscodx.jblog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
			@PathVariable("categoryNo") Optional <Long> categoryNo, 
			@PathVariable("postNo") Optional <Long> postNo,
			Model model) {
		
		// link 정보
		// postNo가 없을 때 : categoryVo 활용
		// postNo가 있을 때 : postVo 활용
		CategoryVo categoryVo = new CategoryVo();
		PostVo postVo = new PostVo();
		if (categoryNo.isPresent()) {
			Long categoryNo1 = categoryNo.get();
			categoryVo.setNo(categoryNo1);
			postVo.setCategoryNo(categoryNo1);
		}
		if (postNo.isPresent()) {
			Long postNo1 = postNo.get();
			postVo.setNo(postNo1);
		}
		categoryVo.setBlogId(blogId);
		// link의 blogId와 postNo, categoryNo가 일치 여부 확인
		boolean state = !blogId.equals(categoryService.getId(categoryVo));

		// blogVo
		BlogVo blogVo = blogService.getBlog(blogId);
		model.addAttribute("blogVo", blogVo);
		// category 리스트
		List<CategoryVo> categoryList = categoryService.getCategory(blogId);
		model.addAttribute("categoryList", categoryList);
		// post리스트
		List<PostVo> postList = postService.getPostList(categoryVo);
		// post정보
		PostVo post = postService.getPost(categoryVo, postVo, state);

		// link의 blogId와 postNo, categoryNo가 일치하지 않을 때
		// post 기본정보 출력
		if(state) {
			CategoryVo defaultCategoryVo = new CategoryVo();
			defaultCategoryVo.setBlogId(blogId);
			postList = postService.getPostList(defaultCategoryVo);
			post = postService.getPost(defaultCategoryVo, postVo, state);
		}

		model.addAttribute("postList", postList);
		model.addAttribute("post", post);
		
		return "blog/main";
	}

	@GetMapping(value = "/admin/basic")
	public String adminBasic(@PathVariable("id")String blogId, Model model) {
		BlogVo blogVo = blogService.getBlog(blogId);
		model.addAttribute("blogVo", blogVo);
		return "blog/admin-basic";
	}
	
	@PostMapping(value = "/admin/basic/update")
	public String adminBasicUpdate(@PathVariable("id")String blogId,
								   @RequestParam("file") MultipartFile file,
								   @RequestParam("title") String title) {
		String url = fileuploadService.restore(file);
		blogService.UpdateBlog(url, title, blogId);
		return "redirect:/" + blogId + "/admin/basic";
	}
	
	@GetMapping(value = "/admin/category")
	public String adminCategory(@PathVariable("id")String blogId, Model model) {
		BlogVo blogVo = blogService.getBlog(blogId);
		List<CategoryVo> list = categoryService.getCategory(blogId);
		model.addAttribute("blogVo", blogVo);
		model.addAttribute("list", list);
		return "blog/admin-category";
	}

	@PostMapping(value = "/admin/category")
	public String adminCategory(@PathVariable("id")String blogId,
								@RequestParam("name") String name,
								@RequestParam("description") String description,
								CategoryVo categoryVo) {
		categoryService.insertCategory(blogId, name, description);
		return "redirect:/" + blogId + "/admin/category";
	}
	
	@RequestMapping("/admin/category/delete/{no}")
	public String delete(@PathVariable("id")String blogId,
						 @PathVariable("no") Long no) {
		categoryService.deleteCategory(no, blogId);
		return "redirect:/" + blogId + "/admin/category";
	}
	
	@RequestMapping("/admin/write")
	public String adminWrite(@PathVariable("id")String blogId, Model model) {
		BlogVo blogVo = blogService.getBlog(blogId);
		List<CategoryVo> list = categoryService.getCategory(blogId);
		model.addAttribute("blogVo", blogVo);
		model.addAttribute("list", list);
		return "blog/admin-write";
	}

	@PostMapping(value = "/admin/write")
	public String adminWrite(@PathVariable("id")String blogId,
							 @RequestParam("title") String title,
							 @RequestParam("content") String content,
							 @RequestParam("category") String categoryName) {
		postService.insertPost(title, content, blogId, categoryName);
		return "redirect:/" + blogId + "/admin/write";
	}
}
