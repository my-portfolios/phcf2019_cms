<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html> 
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>유료멤버십 신청</title>
		<link href="<c:url value='/css/egovframework/com/com.css' />" rel="stylesheet" type="text/css">
		<script src="https://code.jquery.com/jquery-2.2.0.min.js" type="text/javascript"></script>
		<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
		<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
		<script>
			$(function() {
			    $.datepicker.setDefaults({
			        dateFormat: 'yy-mm-dd',
		        	prevText: '이전 달',
		            nextText: '다음 달',
		            monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		            monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		            dayNames: ['일', '월', '화', '수', '목', '금', '토'],
		            dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
		            dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
		            showMonthAfterYear: true,
		            yearSuffix: '년'
			    });
			    $( ".datePicker" ).datepicker();
			});
			
			function updateStatus(seq, value){
				$.ajax({
					url: 'updateMembershipStatus.do',
					type: 'POST',
					data: {
						'id': seq,
						'value': value 
					},
					dateType: 'text',
					success : function(data){
						if(JSON.parse(data).result == value){
							$("#approve_"+seq).remove();
							$("#reject_"+seq).remove();
							$("#result_"+seq).text(JSON.parse(data).result == 'Y' ? "승인" : "반려");
						}
					}
				});
			}
			
			function searchCheck(){
				if($("#start_dt").val() != '' ^ $("#end_dt").val() != ''){
					alert('검색 기간을 입력하십시오!');
					return false;					
				}
				return true;
			}
		</script>
	</head>
	<body>
	
	<div class="board">
		<h1>유료멤버십 리스트 조회</h1>
		<%-- <h1><spring:message code="ussCmt.cmtManageList.cmtManage"/></h1> --%>
		
		<form action="selectMembershipRegList.do" method="post" onsubmit="return searchCheck();">
		<!-- 검색영역 -->
		<div class="search_box" >
			<ul>				
				<li><div>회원유형 :</div> 
				<label><input type="radio" name="type" value="" checked>전체</input></label>
				<label><input type="radio" name="type" value="B">일반회원</input></label>
				<label><input type="radio" name="type" value="P">프리미엄회원</input></label>
				</li>
				
				<li><div>승인여부 :</div>  
				<label><input type="radio" name="check_yn" value="" checked>전체</input></label>
				<label><input type="radio" name="check_yn" value="N">승인 대기</input></label>
				<label><input type="radio" name="check_yn" value="Y">승인 완료</input></label>
				</li>
				
				<li>
				<select name="search_type">
					<option value="MEM_NAME">입금자명</option>
					<option value="MEM_ID">아이디</option>
					<option value="PAY_PRICE">금액</option>
				</select>
				<input type="text" name="search_data" />
				</li>
				
				<li>
				<input type="text" name="start_dt" id="start_dt" class="datePicker"/>~
				<input type="text" name="end_dt" id="end_dt" class="datePicker"/>
				<input type="submit" value="검색" class="s_btn" />
				</li>				
			</ul>
		</div>
		</form>
		
		<table class="board_list" >
			<thead>
			<tr>
				<th>번호</th>
				<th>회원 아이디</th>
				<th>입금자명</th>
				<th>유형</th>
				<th>결제금액</th>
				<th>작성일시</th>
				<th>승인여부</th>
				<th>관리</th>						
			</tr>
			</thead>
			<tbody class="ov">
			<c:choose>
				<c:when test="${fn:length(payList) > 0}">			
					<c:forEach var="items" items="${payList}">
						<tr> 
							<td><c:out value="${items.SEQ}" /></td>
							<td><c:out value="${items.MEM_ID}" /></td>
							<td><c:out value="${items.MEM_NAME}" /></td>
							<td>
								<c:choose>
									<c:when test="${items.PRE_TYPE == 'P'}">프리미엄회원</c:when>
									<c:otherwise>일반회원</c:otherwise>
								</c:choose>
							</td>
							<td><c:out value="${items.PAY_PRICE}" /></td>
							<td><c:out value="${items.CREATE_DT}" /></td>
							<td id="result_${items.SEQ}">
								<c:choose>
									<c:when test="${items.RESULT == 'C'}">취소</c:when>
									<c:when test="${items.RESULT == 'Y'}">승인</c:when>
									<c:when test="${items.RESULT == 'N'}">반려</c:when>
									<c:otherwise>대기</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:if test="${items.CHECK_YN != 'Y'}">
									<input type="button" id="approve_${items.SEQ}" onClick="updateStatus(${items.SEQ},'Y')" value="승인" class="s_btn"/>
									<input type="button" id="reject_${items.SEQ}" onClick="updateStatus(${items.SEQ},'N')" value="반려" class="cancel_btn"/>
								</c:if>
							</td>
							
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="8">데이터가 없습니다.</td>
					</tr>
				</c:otherwise>
			</c:choose>
			</tbody>
		</table>
		
		<div class="pagination">
			<ul>
				<li class="current"><a onclick="return false;">1</a></li>
			</ul>
		</div>
		
		</div>
	</body>
</html>