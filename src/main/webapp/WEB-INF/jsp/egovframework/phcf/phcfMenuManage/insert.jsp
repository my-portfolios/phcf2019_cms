<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html> 
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
		<script src="https://code.jquery.com/jquery-2.2.0.min.js" type="text/javascript"></script>
	</head>
	<body>
		<form action="insertRegMenu.do" method="POST">
			메뉴코드 <input type="text" name="MENU_CODE" value="${hashMap.MENU_CODE }"/><br/>
			1depth <input class="depth" type="text" name="DEPTH1_NUMBER" value="${hashMap.DEPTH1_NUMBER }"/><br/>
			2depth <input class="depth" type="text" name="DEPTH2_NUMBER" value="${hashMap.DEPTH2_NUMBER }"/><br/>
			3depth <input class="depth" type="text" name="DEPTH3_NUMBER" value="${hashMap.DEPTH3_NUMBER }"/><br/>
			메뉴명 <input type="text" name="MENU_NAME" value="${hashMap.MENU_NAME }"/><br/>
			링크 <input type="text" name="LINK" value="${hashMap.LINK }"/><br/>
			새창 <input type="checkbox" name="NEW_PAGE" value="${hashMap.NEW_PAGE }"/><br/>
			페이지 <input type="text" name="PAGE" value="${hashMap.PAGE }"/><br/>
					
			<input type="submit" value="추가"/>
		</form>
	</body>
</html>