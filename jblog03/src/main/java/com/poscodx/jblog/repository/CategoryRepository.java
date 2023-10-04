package com.poscodx.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.CategoryVo;

@Repository
public class CategoryRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	public void insert(CategoryVo categoryVo) {
		sqlSession.insert("category.insert", categoryVo);
	}

	public List<CategoryVo> findById(String blogId) {
		return sqlSession.selectList("category.findById", blogId);
	}

	public void delete(CategoryVo categoryVo) {
		sqlSession.delete("category.delete", categoryVo);
	}

	public Long findByName(CategoryVo categoryVo) {
		return sqlSession.selectOne("category.findByName", categoryVo);
	}
}
