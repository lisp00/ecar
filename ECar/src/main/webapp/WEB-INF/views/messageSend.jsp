<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Message Push Form</h1>
	<form action="/push" method="POST">
		target <select name="target">
				<option value="single">single</option>
				<option value="multi">multi</option>
				<option value="topic">topic</option>
			</select>
		notification <input type="text" name="title" value="test">
		body<input type="text" name="body" value="body data">
		<!-- Security는 POST, PUT, DELETE 등의 변환을 하는 메소드의 경우 csrf 토큰 전달 과정이 필요함 -->
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> 
		<input type="submit">
	</form>
</body>
</html>

<script type="text/javascript">
	var token = document.getElementById('target').options[document.getElementById('target').selectedIndex].text;
</script>