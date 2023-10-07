package com.poscodx.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.repository.BlogRepository;
import com.poscodx.jblog.vo.BlogVo;

@Service
public class BlogService {
	
	@Autowired
	private BlogRepository blogRepository;
	
	public List<BlogVo> getBlogList() {
		return blogRepository.findAll();
	}
	
	public BlogVo getBlog(String blogId) {
		return blogRepository.findById(blogId);
	}
	
	public void insertBlog(String blogId) {
		blogRepository.insert(blogId);
	}

	public void UpdateBlog(String url, String title, String blogId) {
		BlogVo blogVo = new BlogVo();
		blogVo.setImage(url);
		blogVo.setTitle(title);
		blogVo.setBlogId(blogId);
		blogRepository.update(blogVo);
	}
}
