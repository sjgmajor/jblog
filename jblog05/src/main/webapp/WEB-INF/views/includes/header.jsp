<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="header">
	<h2>
		<a href="${pageContext.request.contextPath}">Jblog</a>
	</h2>
	<h1>
		<a href="${pageContext.request.contextPath}/${blogVo.blogId}">${blogVo.title }</a>
	</h1>
	<ul class="header">
		<c:choose>
			<c:when test="${empty authUser }">
				<li><a href="${pageContext.request.contextPath}/user/login">로그인</a></li>
				<li><a href="${pageContext.request.contextPath}/user/join">회원가입</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a></li>
				<c:choose>
					<c:when test="${authUser.id eq blogVo.blogId }">
						<li><a href="${pageContext.request.contextPath}/${authUser.id}/admin/basic">블로그 관리</a></li>
					</c:when>
				</c:choose>
			</c:otherwise>
		</c:choose>
	</ul>
</div>