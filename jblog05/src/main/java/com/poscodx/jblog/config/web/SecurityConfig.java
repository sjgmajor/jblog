package com.poscodx.jblog.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.poscodx.jblog.security.AdminInterceptor;
import com.poscodx.jblog.security.BlogInterceptor;
import com.poscodx.jblog.security.LoginInterceptor;
import com.poscodx.jblog.security.LogoutInterceptor;


@Configuration
@EnableWebMvc
public class SecurityConfig implements WebMvcConfigurer {
	
	// Interceptors
	@Bean
	public HandlerInterceptor loginInterceptor() {
		return new LoginInterceptor();
	}
	@Bean
	public HandlerInterceptor logoutInterceptor() {
		return new LogoutInterceptor();
	}
	@Bean
	public HandlerInterceptor adminInterceptor() {
		return new AdminInterceptor();
	}
	@Bean
	public HandlerInterceptor blogInterceptor() {
		return new BlogInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
				.addInterceptor(loginInterceptor())
				.addPathPatterns("/user/auth");
		
		registry
				.addInterceptor(logoutInterceptor())
				.addPathPatterns("/user/logout/**");
		
		registry
				.addInterceptor(adminInterceptor())
				.addPathPatterns("/**/admin/**");
		
		registry
				.addInterceptor(blogInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns("/assets/**", "/user/**", "/");
	}
}
