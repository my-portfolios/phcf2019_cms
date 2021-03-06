<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="pageTitle"><spring:message code="comStsBst.bbsStats.title"/></c:set>
<%
 /**
  * @ Author : 권혜진
  * @ 홈페이지 통계
  *
  */
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>${pageTitle}</title>
<link href="<c:url value='/css/egovframework/com/com.css' />" rel="stylesheet" type="text/css">
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/jqueryui.css' />">
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/Chart.min.css' />">
<link type="text/css" rel="stylesheet" href="/js/egovframework/phcf/jsgrid-1.5.3/jsgrid.min.css" />
<link type="text/css" rel="stylesheet" href="/js/egovframework/phcf/jsgrid-1.5.3/jsgrid-theme.min.css" />
<script src="<c:url value='/js/egovframework/com/cmm/jquery.js' />"></script>
<script src="<c:url value='/js/egovframework/com/cmm/jqueryui.js' />"></script>
<script src="<c:url value='/js/egovframework/com/cmm/Chart.min.js' />"></script>
<script src="<c:url value='/js/egovframework/com/cmm/Chart.bundle.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/phcf/jsgrid-1.5.3/jsgrid.min.js'/>"></script>
<script type="text/javaScript" language="javascript"></script>
</head>

<body>

<noscript class="noScriptTitle"><spring:message code="common.noScriptTitle.msg" /></noscript>


<div class="grid-container board" >
  <div class="content_title" >
  	<h1>홈페이지 통계</h1>
  </div>
   
  <div class="chart-container div_box"  align="center" style="height: auto; width:96.5%;">
	<p> 월별 접속자 통계 </p>
	<canvas id="canvas" style="height: auto; width:100%;" ></canvas>
  </div>
  <br /><br />
  
  
  <div class="left-box">
  	<h1> 최근게시물 </h1>
  	<div id="jsGrid" ></div>
  </div>
  
  <div class="right-box">
	<div class="righttop-box" style="height: auto; width: 100%;">
		<h1> 인기메뉴목록 </h1>
		<div id="jsGrid2"></div>
	</div>
	<br /><br />
	
	<div class="lefttop-box" style="height: auto; width: 100%;">
		<h1> 금일접속자현황 </h1>
		<p class="div_box"> 총 <c:out value="${conectCnt}"></c:out>명</p>
	</div>
  </div>
  
  <br /><br />
</div>

<script>

var chartLabels = []; // 받아올 데이터를 저장할 배열 선언

var lineChartData = {
	labels : [],
	datasets : [
		{
			label : "월별 접속자 통계",
			fillColor : "rbga(151,187,205,0.2)",
			strokeColor : "rbga(151,187,205,1)",
			pointColor : "rbga(151,187,205,1)",
			pointStrokeColor : "#fff",
			pointHighlightFill : "#fff",
			pointHighlightStroke : "rbga(151,187,205,1)",
			data : [] //일단은 빈 데이터로 먼저 베이스만 그리고 데이터를 조회해 오면 update를 이용해서 다시 호출해서 데이터를 뿌려주는 방식
		}
	]
}

$(document).ready(function() {
	
	var LineChartDemo = new Chart($("#canvas"), {
		type : 'line',
		data : lineChartData,
		options :{
			scales : {
				yAxes : [{
					ticks :{
						beginAtZero : true
					}
				}],
				xAxes : [{
					ticks :{
						beginAtZero : true
					}
				}]
			}
		}
	});
	
	$.getJSON("/cms/statistic/selectMonthlyReport.do", { }, function(result){
		
		var resultData = [];
		var resultDataMonth = [];
		$.each(result.data, function(i, v) {
			resultData.push(v.OCNT);
		});
		
		$.each(result.data, function(i, v) {
			resultDataMonth.push(v.OMONTH + "월");
		});
		
		console.log('resultData : ', resultDataMonth);
		lineChartData.datasets[0].data = resultData; // [17, 23, 17, 23, 17, 23, 25, 35, 129]
		lineChartData.labels = resultDataMonth ;
		LineChartDemo.update();
	});

});

var month = "";

//최근게시물 JSGRID 셋팅
$('#jsGrid').jsGrid({
	width: '100%'
	, height: 'auto'
	, autoload: true
// 	, filtering: true
	, editing: false
	, paging: false
	, pageLoading: true
//		, pageSize: 10
//		, pageButtoncount: 5
//		, pageIndex: 1
	
	, controller: {
		loadData: function(filter) {
			
			var d = $.Deferred();
			
			console.log('== filter :', filter);
			
			$.ajax({
				type: 'POST'
				, url: '/cms/statistic/selectRcntBbsList.do'
				, data: filter
				, dataType: 'JSON'
			}).done(function(response) {
				
				d.resolve({data: response.value, itemsCount: response.totCnt });
			});
			
			return d.promise();
		}
	} 
	
	, noDateContent: '데이터가 없습니다.'
	, loadMessage: '조회 중...'

	, fields: [
			{name: 	'BBS_ID', 	title: '게시판ID', 	type: 'text', 	editing: false, 	align: 'center' }
		, 	{name: 'NTT_SJ', 	title: '게시물제목', 	type: 'text', 	editing: false, 	align: 'center' }
		, 	{name: 'FRST_REGIST_PNTTM', title: '등록일자', type: 'text', editing: false, 	align: 'center' }
		, 	{name: 'NTCR_ID', 	title: '게시자명', 	type: 'text', 	editing: false, 	align: 'center' }
	]
});

//인기메뉴목록 JSGRID2 셋팅
$('#jsGrid2').jsGrid({
	width: '100%'
	, height: 'auto'
	, autoload: true
// 	, filtering: true
	, editing: false
	, paging: false
	, pageLoading: true
//		, pageSize: 10
//		, pageButtoncount: 5
//		, pageIndex: 1
	
	, controller: {
		loadData: function(filter) {
			
			var d = $.Deferred();
			
			console.log('== filter :', filter);
			
			$.ajax({
				type: 'POST'
				, url: '/cms/statistic/selectPopulMenuList.do'
				, data: filter
				, dataType: 'JSON'
			}).done(function(response) {
				
				d.resolve({data: response.value, itemsCount: response.totCnt });
			});
			
			return d.promise();
		}
	} 
	
	, noDateContent: '데이터가 없습니다.'
	, loadMessage: '조회 중...'

	, fields: [
			{name: 	'MENU_NM', 	title: '메뉴명', 	type: 'text', 	editing: false, 	align: 'center' }
		, 	{name: 'RDCNT', 	title: '접속자수', 	type: 'text', 	editing: false, 	align: 'center' }
	]
});


</script>
</body>
</html>
