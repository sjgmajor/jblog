package com.poscodx.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.BlogVo;

@Repository
public class BlogRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<BlogVo> findAll() {
		return sqlSession.selectList("blog.findAll");
	}
	
	public BlogVo findById(String blogId) {
		return sqlSession.selectOne("blog.findById", blogId);
	}

	public void insert(String blogId) {
		sqlSession.insert("blog.insert", blogId);
	}

	public void update(BlogVo blogVo) {
		sqlSession.update("blog.update", blogVo);
	}
}
