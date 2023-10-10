package com.poscodx.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.repository.CategoryRepository;
import com.poscodx.jblog.vo.CategoryVo;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<CategoryVo> getCategory(String blogId) {
		return categoryRepository.findById(blogId);
	}

	public void insertCategory(String blogId, String name, String description) {
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setBlogId(blogId);
		categoryVo.setName(name);
		categoryVo.setDescription(description);
		categoryRepository.insert(categoryVo);
	}

	public void deleteCategory(Long no, String blogId) {
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setNo(no);
		categoryVo.setBlogId(blogId);
		categoryRepository.deletePosts(categoryVo);
		categoryRepository.deleteCategory(categoryVo);
	}

	public String getId(CategoryVo categoryVo) {
		return categoryRepository.findBlogId(categoryVo);
	}
}
