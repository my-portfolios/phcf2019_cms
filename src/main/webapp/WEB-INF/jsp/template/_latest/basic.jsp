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
	<td class="left"><a href="/cop/bbs/selectArticleDetail.do?bbsId=${resultInfo.bbsId }&nttId=${resultInfo.nttId }"><c:out value="${resultInfo.nttSj}"/></a></td>
	<td><c:out value="${resultInfo.frstRegisterPnttm}"/></td>
</tr>
</c:forEach>
</table>

<!-- 페이징 -->

<!-- paging navigation -->

<div class="pagination">
	<ul>
		<c:if test="${paramMap.pageUse == 'Y' }">
			<li class="first"><a href="?pageNum=1" ></a></li>
			<li class="prev"><a href="?pageNum=${paramMap.pageGroupStart-1 } "></a></li>	
			<c:forEach var="page" begin="${paramMap.pageGroupStart}" end="${paramMap.pageGroupEnd}">
				<c:choose>
					<c:when test="${page == paramMap.pageNum}">
						<li class="current" style="top:-8px; position: relative;"><a href="?pageNum=${page }" ><b>${page }</b></a></li>
					</c:when>
					<c:otherwise>
						<li style="top:-8px; position: relative;"><a href="?pageNum=${page }">${page }</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<li class="next"><a href="?pageNum=${paramMap.pageGroupEnd+1 }"></a></li>
			<li class="last"><a href="?pageNum=${paramMap.pageTotal}"></a></li>
		</c:if>
	</ul>
</div>

</body>
</html>