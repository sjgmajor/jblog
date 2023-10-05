package com.poscodx.jblog.service;

import java.util.List;

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

	}

	public List<PostVo> getPostList(CategoryVo categoryVo) {
		return postRepository.findAllPost(categoryVo);
	}
	
	public List<PostVo> getPostList(PostVo postVo) {
		return postRepository.findAllPostByPostVo(postVo);
	}
	
	public PostVo getPost(CategoryVo categoryVo) {
		return postRepository.findPost(categoryVo);
	}
	
	public PostVo getPost(PostVo postVo) {
		return postRepository.findPostByPostVo(postVo);
	}

	public void insertPost(String title, String content, String blogId, String categoryName) {
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setBlogId(blogId);
		categoryVo.setName(categoryName);

		PostVo postVo = new PostVo();
		postVo.setTitle(title);
		postVo.setContents(content);
		
		Long categoryNo = categoryRepository.findByName(categoryVo);
		postVo.setCategoryNo(categoryNo);
		postRepository.insert(postVo);
	}





}
