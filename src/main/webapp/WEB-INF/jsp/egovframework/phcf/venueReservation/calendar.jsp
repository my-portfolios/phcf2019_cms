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
<script src="/js/egovframework/phcf/CommonMethod.js"></script>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>대관신청캘린더</title>
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
		      		height : 670,
		      		eventBackgroundColor : "white",
		      		eventBorderColor : "white", 
		      		showNonCurrentDates: false,
		      		buttonText : {today:    '오늘',list:     '리스트'}
		      			
		    	});
	
		    	calendarObj.render();
		  	});
		
			window.onload = function() {
				var searchObject = new Object();
				searchObject.fixStatus = "Y";
				
				loadEvent(searchObject);
				
				$("[aria-label='prev'], [aria-label='next']").on("click", function() {
					loadEvent(searchObject);
				});
			};
			
			function loadEvent(filter){
				console.log(calendarObj);
				filter['currentDate']=dateToString(calendarObj.getDate()).substring(0,7);
				
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
							if(item.RESULT == "A") {
								var colorCode = "white";
								if(item["VENUE"]=="포항문화예술회관") colorCode = "#AED6F1"; //하늘
								else if(item["VENUE"]=="대잠홀") colorCode = "#76D7C4"; //민트
								else if(item["VENUE"]=="중앙아트홀(인디플러스 포항)") colorCode = "#F9E79F"; //노랑
								else if(item["VENUE"]=="구룡포생활문화센터") colorCode = "#D2B4DE"; //보라
								else if(item["VENUE"]=="아르코공연연습센터") colorCode = "#F5B7B1"; //분홍
									  
								$.each(item.venueReservationDatesList, function(datesIndex,datesItem){
									events = new Object();
									
									events.title = item.EVENT_NAME;
									events.start = datesItem.USE_DATE + "T" + datesItem.USE_START_TIME;
									events.end = datesItem.USE_DATE + "T" + datesItem.USE_END_TIME;
									events.backgroundColor = colorCode;
									calendarObj.addEvent(events);
								});
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
				searchObject.fixStatus = "Y";
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
			<c:import url="/venueReservation/searchView.do">
				<c:param name="fixStatus" value="Y"/>
			</c:import>
			
			<div id='calendarDiv'></div>
		</div>
	</body>
</html>