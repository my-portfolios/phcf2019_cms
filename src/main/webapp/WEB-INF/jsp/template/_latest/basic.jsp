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
	<c:choose>
		<c:when test="${paramMap.pageUse == 'Y' }">
			<td>${paramMap.totalArticles - (paramMap.pageNum-1)*paramMap.pageArticleNum  - status.count + 1}</td>
		</c:when>
		<c:otherwise>
			<td>${fn:length(resultList) - status.count + 1}</td>
		</c:otherwise>
	</c:choose>
	<td class="left"><c:out value="${resultInfo.nttSj}"/></td>
	<td><c:out value="${resultInfo.frstRegisterPnttm}"/></td>
</tr>
</c:forEach>
</table>
<!-- 페이징 -->
<c:if test="${paramMap.pageUse == 'Y' }">
	<a href="?pageNum=1">&lt;&lt;</a>
	<a href="?pageNum=${paramMap.pageGroupStart-1 }">&lt;</a>
	<c:forEach var="page" begin="${paramMap.pageGroupStart}" end="${paramMap.pageGroupEnd}">
		<c:choose>
			<c:when test="${page == paramMap.pageNum}">
				<a href="?pageNum=${page }"><b>${page }</b></a>
			</c:when>
			<c:otherwise>
				<a href="?pageNum=${page }">${page }</a>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	<a href="?pageNum=${paramMap.pageGroupEnd+1 }">&gt;</a>
	<a href="?pageNum=${paramMap.pageTotal}">&gt;&gt;</a>
</c:if>
</body>
</html>