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
		      		height : 650,
		      		eventBackgroundColor : "white",
		      		eventBorderColor : "black", 
		      		showNonCurrentDates: false,
		    	});
	
		    	calendarObj.render();
		  	});
		
			window.onload = function() {
				var searchObject = new Object();
				searchObject.fixStatus = "Y";
				
				loadEvent(searchObject);
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