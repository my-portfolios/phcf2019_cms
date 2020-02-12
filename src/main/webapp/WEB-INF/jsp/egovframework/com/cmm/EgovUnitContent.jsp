<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>eGovFrame <spring:message code="comCmm.unitContent.1"/></title>
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR&display=swap" rel="stylesheet">
<link href="<c:url value='/css/egovframework/com/cmm/main.css' />" rel="stylesheet" type="text/css">
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

<div class="dash_title"><span></span> Dashboard</div>
<div class="board">
	<div class="left dashboard_box1 ">
		<li onclick="window.open('http://localhost:8080/cop/bbs/selectArticleList.do?bbsId=BBSMSTR_000000000297')"><p class="circle">${fndNoticeArticleCnt }</p>재단공지</li>	
		<li onclick="window.open('http://localhost:8080/cop/bbs/selectArticleList.do?bbsId=BBSMSTR_000000000307')"><p class="circle">${extNoticeArticleCnt }</p>외부공지</li>	
		<li onclick="window.open('http://localhost:8080/cop/bbs/selectArticleList.do?bbsId=BBSMSTR_000000000310')"><p class="circle">${rcrtArticleCnt }</p>채용공고</li>	
		<li onclick="window.open('http://localhost:8080/cop/bbs/selectArticleList.do?bbsId=BBSMSTR_000000000309')"><p class="circle">${auctArticleCnt }</p>입찰공고</li>			
		<li onclick="location.href='/uss/umt/EgovMberManage.do';" class="circle_bg1"><p class="circle" style="color: #cf7aa4 !important;">${newMemCnt }</p>신규회원</li>
		<li onclick="location.href='/premiumMember/selectMembershipRegList.do';" class="circle_bg1"><p class="circle" style="color: #cf7aa4 !important;">${newPrmMemCnt }</p>유료회원</li>
		<li onclick="window.open('http://localhost:8080/cop/bbs/insertArticleView.do?bbsId=BBSMSTR_000000000297')" class="circle_bg2"><img src="/images/egovframework/com/cmm/main/ic_dashboard_1.png"><br /><br/>공지 등록</li>
		<li onclick="location.href='/uss/umt/EgovMberManage.do';" class="circle_bg2"><img src="/images/egovframework/com/cmm/main/ic_dashboard_2.png"><br /><br/>회원 조회</li>
		<li onclick="location.href='/uss/umt/EgovMberDormantMngView.do';" class="circle_bg2"><img src="/images/egovframework/com/cmm/main/ic_dashboard_3.png"><br /><br/>휴면회원 조회</li>
		<li onclick="location.href='#';" class="circle_bg2"><img src="/images/egovframework/com/cmm/main/ic_dashboard_4.png"><br />버스킹 무대<br/>신청 관리</li>
		<li onclick="window.open('http://localhost:8280/cop/bbs/insertArticleView.do?bbsId=BBSMSTR_000000000281')" class="circle_bg2"><img src="/images/egovframework/com/cmm/main/ic_dashboard_5.png"><br />시립예술단<br/>공연 일정등록</li>
		<li onclick="window.open('http://localhost:8280/cop/bbs/insertArticleView.do?bbsId=BBSMSTR_000000000311')" class="circle_bg2" style="letter-spacing: -1px;"><img src="/images/egovframework/com/cmm/main/ic_dashboard_6.png">
		<br />인디플러스 포항<br/>영화정보 등록</li>
	</div>
	
	<div class="dashboard_box2"><p class="dash_subtitle">인기 메뉴
	<span class="btn_more" onclick="location.href='/cms/statistic/phcfStatusReport.do';" >+</span></p>
		<div id="jsGrid2" style="height: 288px;"></div>
	</div>

	<div class="cler"></div>

	<div class="dashboard_box3"><p class="dash_subtitle">최근 게시글</p>
	<c:import url="/cop/bbs/latestArticleListView.do" charEncoding="utf-8">
 		<c:param name="skinNm" value="" /> <%-- 스킨이름 (기본값 basic) --%>
		<c:param name="bbsId" value="" /> <%-- 필수 BBS_ID --%>
		<c:param name="cntOfArticle" value="5" /> <%-- 게시물 갯수(기본값 5) --%>
		<c:param name="ordColmn" value="" /> <%-- 정렬 칼럼(기본값 FRST_REGIST_PNTTM 최초등록시점) --%>
		<c:param name="ordWay" value="" /> <%-- 정렬방식 (기본값 DESC) --%>
		<c:param name="cateName" value="" /> <%-- 카테고리 --%>
		<c:param name="pageUse" value="N" /> <%-- 페이지 사용유무 --%>
		<c:param name="pageGroupNum" value="5" /> <%-- 페이지 그룹갯수 --%>
		<c:param name="pageArticleNum" value="5" /> <%-- 한 페이지 게시물갯수 --%>
	</c:import>
	</div>
	
	<div class="dashboard_box4"><p class="dash_subtitle">업무 요청 게시판 
	<span class="btn_more" onclick="location.href='/cop/bbs/selectArticleList.do?bbsId=BBSMSTR_000000000331';" >+</span></p>
	<c:import url="/cop/bbs/latestArticleListView.do" charEncoding="utf-8">
 		<c:param name="skinNm" value="" /> <%-- 스킨이름 (기본값 basic) --%>
		<c:param name="bbsId" value="BBSMSTR_000000000331" /> <%-- 필수 BBS_ID --%>
		<c:param name="cntOfArticle" value="5" /> <%-- 게시물 갯수(기본값 5) --%>
		<c:param name="ordColmn" value="" /> <%-- 정렬 칼럼(기본값 FRST_REGIST_PNTTM 최초등록시점) --%>
		<c:param name="ordWay" value="" /> <%-- 정렬방식 (기본값 DESC) --%>
		<c:param name="pageUse" value="N" /> <%-- 페이지 사용유무 --%>
		<c:param name="pageGroupNum" value="5" /> <%-- 페이지 그룹갯수 --%>
		<c:param name="pageArticleNum" value="5" /> <%-- 한 페이지 게시물갯수 --%>
	</c:import>
	</div>
	
	<div class="dashboard_box5"><p class="dash_subtitle">월별 접속자 통계</p>
		<div class="chart-container div_box"  align="center" style="height: auto; width:96.5%;">
		<p> 월별 접속자 통계 </p>
		<canvas id="canvas" style="height: auto; width:100%;" ></canvas>
	  </div>
	</div>
	
	<div class="clear"></div>
	
	
</div>
<br /><br /><br /><br />
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
	, rowClick: function(args) { console.log(args); }
	, noDateContent: '데이터가 없습니다.'
	, loadMessage: '조회 중...'

	, fields: [
			{name: 	'MENU_NM', 	title: '메뉴명', 	type: 'text', 	editing: false, 	align: 'center' }
		, 	{name: 'RDCNT', 	title: '접속자수', 	type: 'text', 	editing: false, 	align: 'center' }
	]
});


</script>


	<%-- 
	<c:if test="${loginVO != null}">
		${loginVO.name }<spring:message code="comCmm.unitContent.2"/> <a href="${pageContext.request.contextPath }/uat/uia/actionLogout.do"><spring:message code="comCmm.unitContent.3"/></a>
	</c:if> 
	<c:if test="${loginVO == null }">
		<jsp:forward page="/uat/uia/egovLoginUsr.do"/>
	</c:if>
	<p/><p/><p/>
	<b><spring:message code="comCmm.unitContent.4"/><br /><br/><!-- 실행 시 오류 사항이 있으시면 표준프레임워크센터로 연락하시기 바랍니다. -->
	<b><img src="${pageContext.request.contextPath }/images/egovframework/com/cmm/icon/tit_icon.png"> <spring:message code="comCmm.unitContent.5"/></b><p/><!-- 화면 설명 -->
	<spring:message code="comCmm.unitContent.6"/><p/><!-- 왼쪽 메뉴는 메뉴와 관련된 컴포넌트(메뉴관리, 사이트맵 등)들의 영향을 받지 않으며, -->
	<spring:message code="comCmm.unitContent.7"/><p/><!-- 각 컴포넌트를 쉽게 찾아볼 수 있는 바로 가기 링크페이지입니다. -->

	<br /><b><img src="${pageContext.request.contextPath }/images/egovframework/com/cmm/icon/tit_icon.png"> egovframework.com.cmm.web.EgovComIndexController.java</b><p/>

	<spring:message code="comCmm.unitContent.8"/><p/><!-- 컴포넌트 설치 후 설치된 컴포넌트들을 IncludedInfo annotation을 통해 찾아낸 후 -->
	<spring:message code="comCmm.unitContent.9"/><p/><br /><!-- 화면에 표시할 정보를 처리하는 Controller 클래스입니다. -->
	<spring:message code="comCmm.unitContent.10"/><p/><!-- 개발 시 메뉴 구조가 잡히기 전에 배포 파일들에 포함된 공통 컴포넌트들의 목록성 화면에 URL을 제공하여 -->
	<spring:message code="comCmm.unitContent.11"/><p/><!-- 개발자가 편리하게 활용할 수 있도록 작성되었습니다. -->
	<spring:message code="comCmm.unitContent.12"/> <p/><!-- 운영 시에 본 컨트롤을 사용하여 메뉴를 구성하는 경우, -->
	<spring:message code="comCmm.unitContent.13"/><p/><!-- 성능 문제를 일으키거나 사용자별 메뉴 구성에 오류를 발생할 수 있기 때문에 -->
	<spring:message code="comCmm.unitContent.14"/><p /><!-- 실 운영 시에는 삭제해서 배포하는 것을 권장해 드립니다. -->
	
	--%>
	
	
</body>
</html>