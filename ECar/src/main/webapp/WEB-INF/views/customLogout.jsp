<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Logout Page</h1>

	<!-- POST 방식으로 동작을 하면 CSRF 토큰을 전달 / security_context에서 토큰 만료시킴. 로그인으로 redirect-->
	<form method="post" action="/customLogout">
		<input type='hidden' name='${_csrf.parameterName }' value='${_csrf.token }'>
		<button>로그아웃</button>
	</form>
</body>
</html>