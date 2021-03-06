<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="/css/egovframework/phcf/datepicker.css" />

<script src="/js/egovframework/phcf/datepicker.js"></script>
<script src="/js/egovframework/phcf/datepicker.ko.js"></script>
<script>
	var datePicker;
	var datePicker_use = false;
	$(function(){
		$("#searchType").on("change",function(){
			if($(this).val() == "BB.USE_DATE"){
				datePicker = $("#keyword").datepicker({
					language: 'ko',
					position: 'bottom right',
					showOtherMonths: false,
					moveToOtherMonthsOnSelect: false,
					selectOtherMonths: false,
					navTitles: {
						days: 'yyyy 년 MM'
					}
				}).data('datepicker');
				datePicker_use = true;
			}
			else if(datePicker_use) {
				datePicker.destroy();
				datePicker_use = false;
			}
		});
		
	});
</script>
<div class="search_box">
	<ul>
		<li><!-- 상태-->
			<c:if test='<%= request.getParameter("fixStatus") == null %>'>
	            <select class="floatleft" id="RESULT">
					<option value="">상태</option>
					<option value="R">접수요청</option>
					<option value="C">접수취소</option>
					<!--<option value="S">접수완료</option>-->
					<option value="A">승인완료</option>	
					<option value="D">승인거절</option>	
					<option value="O">승인취소</option>
					<option value="B">취소요청</option>
				</select>
			</c:if>
		</li>
		<li><!-- 조건 -->
               <select class="floatleft" id="VENUE" onchange="selectRoomList(this.value)" >
				<option value="">대관 장소</option>					
				<option value="포항문화예술회관">포항문화예술회관</option>					
				<option value="대잠홀">대잠홀</option>
				<option value="중앙아트홀(인디플러스 포항)">중앙아트홀(인디플러스 포항)</option>					
				<option value="구룡포생활문화센터">구룡포생활문화센터</option>					
				<option value="아르코공연연습센터">아르코공연연습센터</option>				
			</select>
		</li>
		<li><!-- 조건 -->
               <select class="floatleft" id="ROOM">
				<option value="">대관 시설</option>					
							
			</select>
		</li>
		<li>
	        <select class="floatleft" id="searchType"><!--  -->
	            <option value="AA.USER_ID">아이디</option>
	            <option value="AA.MANAGER_NAME">이름</option>
	            <c:if test='<%= request.getParameter("fixStatus") == null %>'>
	            	<option value="BB.USE_DATE">대관일</option>
	            </c:if>
	        </select>
		</li>
		<!-- 검색키워드 및 조회버튼 -->
		<li>
			<input class="s_input" id="keyword" type="text"/>
			<input type="button" class="s_btn" value="검색" onclick="search();"/>
		</li>
	</ul>
</div>

<script>
	function selectRoomList(value) {
		$.post('/venueReservation/selectRoomList.do', {
			venue: value
		}, function(event) {
			$("#ROOM").empty();
			$("#ROOM").append('<option value="">대관 시설</option>');
			$.each(JSON.parse(event).roomList, function(i, iv) {
				console.log(iv);
				$("#ROOM").append("<option value='" + iv +"'>"+ iv +"</option>");
			});
			
		}).fail(function(){
			
		});
	}

</script>