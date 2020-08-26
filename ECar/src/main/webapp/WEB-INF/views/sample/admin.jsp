<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>/sample admin page</h1>
	
	<p>principal : <sec:authentication property="principal"/></p>
	<p>MemberVO : <sec:authentication property="principal.member"/></p>
	<p>username : <sec:authentication property="principal.username"/></p>
	<p>userid : <sec:authentication property="principal.member.userid"/></p>
	<p>name : <sec:authentication property="principal.member.name"/></p>
	<p>password : <sec:authentication property="principal.member.password"/></p>
	<p>code : <sec:authentication property="principal.member.agencyCode"/></p>
	<p>service : <sec:authentication property="principal.member.service"/></p>
	<p>address: <sec:authentication property="principal.member.address"/></p>
	<p>addressDetail : <sec:authentication property="principal.member.addressDetail"/></p>
	<p>enabled : <sec:authentication property="principal.member.enabled"/></p>
	<p>authorization: <sec:authentication property="principal.member.authorization"/></p>
	<p>auth : <sec:authentication property="principal.member.authList"/></p>
	
	<a href="/customLogout">Logout</a>
</body>
</html>