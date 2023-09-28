package com.poscodx.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.repository.BlogRepository;
import com.poscodx.jblog.repository.UserRepository;
import com.poscodx.jblog.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BlogRepository blogRepository;
	
	public void join(UserVo userVo) {
		userRepository.insert(userVo);
		blogRepository.insert(userVo.getId());
	}

	public UserVo getUser(UserVo userVo) {
		return userRepository.findByIdAndPassword(userVo);
	}
}
