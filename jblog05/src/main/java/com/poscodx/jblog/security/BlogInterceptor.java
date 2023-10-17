package com.poscodx.jblog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.poscodx.jblog.service.BlogService;

public class BlogInterceptor implements HandlerInterceptor {

	@Autowired
	public BlogService blogService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String url = request.getRequestURI();
		String blogId = url.split("/")[2];
		if(blogService.getBlog(blogId) == null) {
			response.sendRedirect(request.getContextPath());
			return false;
		}
		
		return true;
	}
	
}
