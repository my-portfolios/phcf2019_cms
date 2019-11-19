<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="pageTitle"><spring:message code="comStsBst.bbsStats.title"/></c:set>
<%
 /**
  * @Author : 권혜진
  * 대관신청관리
  *
  */
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>대관신청관리</title>
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
	<h1> HELLO WORLD ,  대관신청관리 입니다.</h1>
	<div class="left-box" style="border-top : 1px solid gray;">
	  	<h3> 최근게시물 </h3>
	  	<div id="jsGrid"></div>
	</div>
	
<script>

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

</script>
</body>
</html>