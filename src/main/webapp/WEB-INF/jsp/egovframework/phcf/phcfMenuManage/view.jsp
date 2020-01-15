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
		<script>
			function delMenu(){
				$("input:checkbox:checked").each(function() {
					
				});
			}
		</script>
		<style>
			.input {
				width: 90%
			}
		</style>
	</head>
	<body>
	메뉴관리
		<br/>
		<a href="selectMenuManageList.do?page=main">메인</a>
		<a href="selectMenuManageList.do?page=place">문화공간</a>
		<a href="selectMenuManageList.do?page=festival">축제</a>
		<a href="selectMenuManageList.do?page=cms">CMS</a>
		<br/>
		<input type="button" onclick="location.href='insertRegMenu.do'" value="추가"/>
		<input type="button" onclick="delMenu();" value="삭제"/> 
		<table id="list">
			<tr>
				<td style="width: 5%">선택</td>
				<td style="width: 5%">순번</td>
				<td style="width: 10%">메뉴코드</td>
				<td style="width: 5%">1depth</td>
				<td style="width: 5%">2depth</td>
				<td style="width: 5%">3depth</td>
				<td style="width: 15%">메뉴 명</td>
				<td style="width: 30%">링크</td>
				<td style="width: 5%">새 창</td>
				<td style="width: 10%">페이지</td>
			</tr>
			<c:forEach var="items" items="${menuInfo}" varStatus="status">
				<tr>
					<td><input type="checkbox" value="${items.MENU_CODE}" /></td>
					<td><c:out value="${status.count}"/></td>
					<td>${items.MENU_CODE}</td>
					<td><input class="input" type="text" value="${items.DEPTH1_NUMBER}"/></td>
					<td><input class="input" type="text" value="${items.DEPTH2_NUMBER}"/></td>
					<td><input class="input" type="text" value="${items.DEPTH3_NUMBER}"/></td>
					<td><input class="input" type="text" value="${items.MENU_NAME}"/></td>
					<td><input class="input" type="text" value="${items.LINK}"/></td>
					<td><input type="checkbox" <c:if test="${items.LINK=='on'}">checked</c:if>/></td>
					<td><input class="input" type="text" value="${items.PAGE}"/></td>
				</tr>
			</c:forEach>
		</table>

		
	</body>
</html>
