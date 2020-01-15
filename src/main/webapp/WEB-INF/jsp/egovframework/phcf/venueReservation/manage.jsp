<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<link href="<c:url value='/css/egovframework/com/com.css' />" rel="stylesheet" type="text/css">
	<link rel="stylesheet" href="/css/egovframework/phcf/datepicker.css" />
	<script src="http://code.jquery.com/jquery-latest.min.js"></script> 		
	<script src="/js/egovframework/phcf/datepicker.js"></script>
	<script src="/js/egovframework/phcf/datepicker.ko.js"></script>
	<script src="/js/egovframework/phcf/CommonMethod.js"></script>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script>
			$(function(){
			    <c:forEach var="venueItems" items="${codeList}">
					<c:forEach var="masterItems" items="${venueReservationMaster}">
						<c:if test="${venueItems.CODE_NM == masterItems.VENUE_NAME}">
							calendarLoad('${venueItems.CODE}_regStartDt1','${masterItems.REG_START_DT}',true);
							calendarLoad('${venueItems.CODE}_regEndDt1','${masterItems.REG_END_DT}',true);
							calendarLoad('${venueItems.CODE}_useStartDt1','${masterItems.USE_START_DT}',false);
							calendarLoad('${venueItems.CODE}_useEndDt1','${masterItems.USE_END_DT}',false);
						</c:if>
					</c:forEach>
				</c:forEach>
			});
			
			function updateReservationManage(venue,venueCode){
				var regStartDt1 = $("#" + venueCode + "_regStartDt1").val();
				var regEndDt1 = $("#" + venueCode + "_regEndDt1").val();
				var useStartDt1 = $("#" + venueCode + "_useStartDt1").val();
				var useEndDt1 = $("#" + venueCode + "_useEndDt1").val();
				var limitTime = parseInt($("#" + venueCode + "_limitTime").val());
				var useYn = $("#" + venueCode + "_useYn").val();
				
				$.ajax({
					url: "/venueReservation/updateReservationMaster.do",
					type: 'POST',
					data: {
						"venueName" : venue,
						"regStartDt" : regStartDt1,
						"regEndDt" : regEndDt1,
						"useStartDt" : useStartDt1,
						"useEndDt" : useEndDt1,
						"limitTime" : limitTime,
						"useYn" : useYn
					},
					datatype: "json",
					success : function(data){
						if(JSON.parse(data).result == "success") {
							alert("변경되었습니다.");
						}
						else alert("변경에 실패하였습니다");
					}
				});
			}
			
			function calendarLoad(obj,defaultDate,timePickYn){
				$("#" + obj).datepicker({
					language: 'ko',
					position: 'bottom left',
					timepicker : timePickYn,
					showOtherMonths: false,
					moveToOtherMonthsOnSelect: false,
					selectOtherMonths: false,
					navTitles: {
						days: 'yyyy 년 MM'
					}
				}).data('datepicker').selectDate(stringToDate(defaultDate));
			}
		</script>
	</head>
	<body>
	
	<div class="wTableFrm">
		<h2>대관 신청 접수 관리</h2>
		
		
		<c:forEach var="venueItems" items="${codeList}">
			<c:forEach var="masterItems" items="${venueReservationMaster}">
				<c:if test="${venueItems.CODE_NM == masterItems.VENUE_NAME}">
					
					
					<h3>${venueItems.CODE_NM} 대관신청 기간 설정</h3>
					<table class="wTable">
					<colgroup>
						<col style="width:16%">
						<col style="">
					</colgroup>
					<tbody>
					<tr>
						<th>접수날짜 <span class="pilsu">*</span></th>						
						<td class="left">
						<input type="text" id="${venueItems.CODE}_regStartDt1"/> ~
						<input type="text" id="${venueItems.CODE}_regEndDt1"/>						
						※ 접수 할 날짜와 시간을 설정합니다.
						</td>
						<th rowspan="4">
							<div class="btn">
							<input type="button" onclick="updateReservationManage('${venueItems.CODE_NM}','${venueItems.CODE}');" value="설정" class="s_submit"/>						
							</div>
						</th>
					</tr><tr>						
						<th>대관접수 설정 <span class="pilsu">*</span></th>
						<td class="left">
						<input type="text" id="${venueItems.CODE}_useStartDt1" /> ~
						<input type="text" id="${venueItems.CODE}_useEndDt1" />						
						※ 접수 받을  날짜를 설정합니다.
						</td>
					</tr><tr>
						<th>대관 신청서 작성 제한 시간 <span class="pilsu">*</span></th>
						<td class="left">
						<select id="${venueItems.CODE}_limitTime">
							<option value="10" <c:if test="${masterItems.LIMIT_TIME == '10'}">selected</c:if>>10분</option>
							<option value="20" <c:if test="${masterItems.LIMIT_TIME == '20'}">selected</c:if>>20분</option>
							<option value="30" <c:if test="${masterItems.LIMIT_TIME == '30'}">selected</c:if>>30분</option>
							<option value="40" <c:if test="${masterItems.LIMIT_TIME == '40'}">selected</c:if>>40분</option>
							<option value="50" <c:if test="${masterItems.LIMIT_TIME == '50'}">selected</c:if>>50분</option>
							<option value="60" <c:if test="${masterItems.LIMIT_TIME == '60'}">selected</c:if>>60분</option>
						</select>
						※ 신청서 작성 제한 시간을 설정합니다.
						</td>
					</tr><tr>	
						<th>사용 여부 <span class="pilsu">*</span></th>
						<td class="left">
						<select id="${venueItems.CODE}_useYn">
							<option value="Y" <c:if test="${masterItems.USE_YN == 'Y'}">selected</c:if>>사용</option>
							<option value="N" <c:if test="${masterItems.USE_YN == 'N'}">selected</c:if>>미사용</option>
						</select> 
						※ 대관에 대한 사용 여부를 설정합니다.
						</td>
					</tr>
					</tbody>
					</table>
					
					
				</c:if>
			</c:forEach>
		</c:forEach>
		
		
		</div>
	</body>
</html>