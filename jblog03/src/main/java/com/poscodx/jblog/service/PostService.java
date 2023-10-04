package com.poscodx.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.repository.CategoryRepository;
import com.poscodx.jblog.repository.PostRepository;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public void insertPost(PostVo postVo, CategoryVo categoryVo) {
		
		Long categoryNo = categoryRepository.findByName(categoryVo);
		postVo.setCategoryNo(categoryNo);
		postRepository.insert(postVo);
	}

}
