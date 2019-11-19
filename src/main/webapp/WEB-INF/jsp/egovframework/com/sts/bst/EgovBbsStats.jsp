<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="pageTitle"><spring:message code="comStsBst.bbsStats.title"/></c:set>
<%
 /**
  * @ 권혜진 테스트
  *
  */
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>${pageTitle}</title>
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/jqueryui.css' />">
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/Chart.min.css' />">
<script src="<c:url value='/js/egovframework/com/cmm/jquery.js' />"></script>
<script src="<c:url value='/js/egovframework/com/cmm/jqueryui.js' />"></script>
<script src="<c:url value='/js/egovframework/com/cmm/Chart.min.js' />"></script>
<script src="<c:url value='/js/egovframework/com/cmm/Chart.bundle.min.js' />"></script>
<script type="text/javaScript" language="javascript"></script>
</head>

<!-- <body onLoad="javascript:fnInitAll();"> -->
<body>

<noscript class="noScriptTitle"><spring:message code="common.noScriptTitle.msg" /></noscript>

<div class="grid-container" style="border: 1px solid gray;">
  <div class="content_title" style="height: auto; width: 100%; border-bottom:1px solid gray;">
  	<h3>홈페이지 통계 </h3>
  </div>
  <div class="chart-container" align="center" style="height: auto; width:100%;">
	<h4> chartJS(월별 접속자 통계) </h4>
	<canvas id="myChart" style="height: 50%; width:80%;" ></canvas>
     </div>
  <div class="left-box" style="border-top : 1px solid gray;">
  	<h3> 최근게시물 </h3>
  </div>
  <div class="right-box">
	<div class="righttop-box" style="height: auto; width: 100%; border:1px solid gray;">
		<h3> 인기메뉴목록 </h3>
	</div>
	<div class="lefttop-box"style="height: auto; width: 100%; border:1px solid gray;">
		<h3> 금일접속자현황 </h3>
	</div>
  </div>
</div>

<script>

$(function(){
	
})


/* var chartLabels = [];
var chartData = [];

$.postJSON("/sts/cst/selectMonthlyReport.do", function(data){
	console.log('여긴 안왓나');
	$.each(data, function(inx, obj){
		chartLabels.push(obj.dd);
		chartData.push(obj.income);
	});
	createChart();
	console.log("create Chart")
});



var lineChartData = {
		labels : chartLabels,
		datasets : [
			{
				label : "Date Income",
				fillColor : "rbga(151,187,205,0.2)",
				strokeColor : "rbga(151,187,205,1)",
				pointColor : "rbga(151,187,205,1)",
				pointStrokeColor : "#fff",
				pointHighlightFill : "#fff",
				pointHighlightStroke : "rbga(151,187,205,1)",
				data : chartData
		}
			]
}


function createChart(){

	var ctx = document.getElementById("canvas").getContext("2d");
	LineChartDemo = Chart.Line(ctx,{
		data : lineChartData,
		options :{
			scales : {
				yAxes : [{
					ticks :{
						beginAtZero : true
					}
				}]
			}
		}
	})
}
 */

</script>
</body>
</html>
