package com.poscodx.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;

@Repository
public class PostRepository {

	@Autowired
	private SqlSession sqlSession;

	public void insert(PostVo postVo) {
		if(postVo.getTitle() == "" || postVo.getContents() == "") {
		}
		else {
			sqlSession.insert("post.insert", postVo);
		}
	}

	public List<PostVo> findAllPost(CategoryVo categoryVo) {
		return sqlSession.selectList("post.findAllPost", categoryVo);
	}

	public List<PostVo> findAllPostByPostVo(PostVo postVo) {
		return sqlSession.selectList("post.findAllPostByPostVo", postVo);
	}

	public PostVo findPost(CategoryVo categoryVo) {
		return sqlSession.selectOne("post.findPost", categoryVo);
	}
	
	public PostVo findPostByPostVo(PostVo postVo) {
		return sqlSession.selectOne("post.findPostByPostVo", postVo);
	}

}
