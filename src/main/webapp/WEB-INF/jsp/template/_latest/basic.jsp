<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<table class="board_list" summary="최근게시물을 출력합니다.">
<tr>
	<th>No</th>
	<th>제목</th>
	<th>날짜</th>
</tr>
<c:forEach items="${resultList}" var="resultInfo" varStatus="status">
<tr>
	<td>${status.count}</td>
	<td class="left"><c:out value="${resultInfo.nttSj}"/></td>
	<td><c:out value="${resultInfo.frstRegisterPnttm}"/></td>
</tr>
</c:forEach>
</table>
</body>
</html>