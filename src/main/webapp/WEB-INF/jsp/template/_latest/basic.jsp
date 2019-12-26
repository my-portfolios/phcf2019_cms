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
<table>
<tr>
	<td>No</td>
	<td>제목</td>
	<td>날짜</td>
</tr>
<c:forEach items="${resultList}" var="resultInfo" varStatus="status">
<tr>
	<td>${status.count}</td>
	<td><c:out value="${resultInfo.nttSj}"/></td>
	<td><c:out value="${resultInfo.frstRegisterPnttm}"/></td>
</tr>
</c:forEach>
</table>
</body>
</html>