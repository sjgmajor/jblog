package com.poscodx.jblog.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
			if(result.hasErrors()) {
				model.addAllAttributes(result.getModel());
				return "user/join";
			}
			
		try {
			UserVo existingUser = userService.getUser(userVo);
		    if (existingUser != null) {
		        model.addAttribute("Duplicate", "사용 중인 아이디입니다.");
		        return "user/join";
		    }
			userService.join(userVo);
			return "user/joinsuccess";
		} catch (Exception e){
			model.addAttribute("errorMessage", "회원 가입 과정에서 오류가 발생했습니다.");
			return "user/join";
		}
	}

	@RequestMapping("/login")
	public String login() {
		return "user/login";
	}
}
