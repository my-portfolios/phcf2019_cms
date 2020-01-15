<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link href="<c:url value='/css/egovframework/com/com.css' />" rel="stylesheet" type="text/css">
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/jqueryui.css' />">

<script src="<c:url value='/js/egovframework/com/cmm/jquery.js' />"></script>
<script src="<c:url value='/js/egovframework/com/cmm/jqueryui.js' />"></script>


<link type="text/css" rel="stylesheet" href="/css/egovframework/phcf/fullcalendar/core.min.css" />
<link type="text/css" rel="stylesheet" href="/css/egovframework/phcf/fullcalendar/daygrid.main.min.css" />
<script src="/js/egovframework/phcf/fullcalendar/core.main.min.js"></script>
<script src="/js/egovframework/phcf/fullcalendar/daygrid.main.min.js"></script>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
		<script>
			var jsonString;
			var eventsList = new Object();
			var events;
			var eventsListIndex = 0;
			var calendarObj;
			
			document.addEventListener('DOMContentLoaded', function() {
		    	var calendarEl = document.getElementById('calendarDiv');
	
		    	calendarObj = new FullCalendar.Calendar(calendarEl, {
		      		plugins: [ 'dayGrid' ],
		      		locale : 'ko',
		      		height : 650,
		      		eventBackgroundColor : "white",
		      		eventBorderColor : "black", 
		      		showNonCurrentDates: false,
		    	});
	
		    	calendarObj.render();
		  	});
		
			window.onload = function() {
				loadEvent();
			};
			
			function loadEvent(filter){
				console.log(calendarObj);		
				var getEventsList = calendarObj.getEvents();
				$.each(getEventsList, function(index, item){
					item.remove();
				}); 
				
				$.ajax({
					type: 'POST',
					url: '/venueReservation/selectReservationListToJson.do',
					dataType: 'json',
					data: filter,
					success : function(data){
						jsonString = data.venueReservationRegJson;
						jsonString = JSON.parse(jsonString);
						
						$.each(jsonString, function(index, item){
							events = new Object();
							
							for(var i=1;i<=5;i++){
								if(item["USE_DATE" + i] != null) {
									events.title = item["EVENT_NAME"];
									events.start = item["USE_DATE" + i].substring(0,10) + "T" + item["USE_START_TIME" + i];
									events.end = item["USE_DATE" + i].substring(0,10) + "T" + item["USE_END_TIME" + i];
									
									calendarObj.addEvent(events);
								}
							}
						});
					}
				});
			}
			
			function search(){
				var searchType = $("#searchType").val();
				var venue = $("#VENUE").val();
				var result = $("#RESULT").val();
				var keyword = $("#keyword").val();
				
				var searchObject = new Object();
				searchObject.searchCnd = searchType;
				searchObject.venueCnd = venue;
				searchObject.resultCnd = result;
				searchObject.keyword = keyword;
				
				loadEvent(searchObject);
			}
		</script>
	</head>
	<body>
		<div class="board">
			<h1>대관신청 캘린더</h1>
			<div class="search_box">
				<ul>
					<li><!-- 상태-->
		                <select class="floatleft" id="RESULT">
							<option value="">상태</option>
							<option value="R">접수요청</option>
							<option value="C">접수취소</option>
							<option value="S">접수완료</option>
							<option value="A">승인완료</option>	
							<option value="D">승인거절</option>	
							<option value="O">승인취소</option>
						</select>
					</li>
					<li><!-- 조건 -->
		                <select class="floatleft" id="VENUE">
							<option value="">대관 장소</option>					
								<option value="포항문화예술회관">포항문화예술회관</option>					
								<option value="중앙아트홀">중앙아트홀</option>					
								<option value="대잠홀">대잠홀</option>					
								<option value="구룡포생활문화센터(아라예술촌)">구룡포생활문화센터(아라예술촌)</option>					
								<option value="귀비고">귀비고</option>					
								<option value="기타">기타</option>					
						</select>
					</li>
					<li>
		                <select id="searchType"><!--  -->
		                    <option value="USER_ID">아이디</option>
		                    <option value="MANAGER_NAME">이름</option>
		                </select>
					</li>
					<!-- 검색키워드 및 조회버튼 -->
					<li>
						<input class="s_input" id="keyword" type="text"/>
						<input type="submit" class="s_btn" value="검색" onclick="search();"/>
					</li>
				</ul>
			</div>
			
			<div id='calendarDiv'></div>
		</div>
	</body>
</html>