package com.poscodx.jblog.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poscodx.jblog.service.UserService;
import com.poscodx.jblog.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/join")
	public String join(@ModelAttribute UserVo userVo) {
		return "user/join";
	}
	
	@PostMapping(value = "/join")
	public String join(
			@Valid @ModelAttribute("userVo") UserVo userVo,
			BindingResult result,
			Model model) {		
		
		try {
			if(result.hasErrors()) {
				model.addAllAttributes(result.getModel());
				return "user/join";
			}
			userService.join(userVo);
			return "user/joinsuccess";
			
		} catch (DuplicateKeyException e){
			model.addAttribute("Duplicate", "사용할 수 없는 아이디입니다.");
			return "user/join";
		}
	}

	@RequestMapping("/login")
	public String login() {
		return "user/login";
	}
}
