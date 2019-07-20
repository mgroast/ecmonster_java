<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    ログインページ
    <c:if test="${not empty errorMessages}">
        <ul>
	        <c:forEach var="msg" items="${errorMessages}">
	            <li><c:out value="${msg}"/></li>
	        </c:forEach>
        </ul>
    </c:if>
    
    <form method="post" action="${pageContext.request.contextPath}/fc/auth/auth">
        <p>ID: <input type="text" name="id"></p>
        <p>PASS: <input type="password" name="pass"></p>
        <p><input type="submit" value="ログイン"></p>
    </form>
</body>
</html>