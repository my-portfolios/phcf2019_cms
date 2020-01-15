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

<div class="board">
<h1>대관신청 리스트</h1>
<%-- <h1><spring:message code="ussCmt.cmtManageList.cmtManage"/></h1> --%>

	<!-- 검색영역 -->
	<div class="search_box" title="<spring:message code="common.searchCondition.msg" />">
		<ul>
			<li><!-- 상태-->
                <select class="floatleft" name="SEARCH_ANS_YN">
					<option value="">++승인상태++</option>
					<option value="N">접수요청</option>
					<option value="R">접수취소</option>
					<option value="D">접수완료</option>
					<option value="Y">승인완료</option>	
					<option value="C">승인거절</option>	
					<option value="Z">승인취소</option>
				</select>
			</li>
			<li><!-- 조건 -->
                <select class="floatleft" name="S_CD_VALUE">
					<option value="">++대관++</option>					
						<option value="포항문화예술회관">포항문화예술회관</option>					
						<option value="중앙아트홀">중앙아트홀</option>					
						<option value="대잠홀">대잠홀</option>					
						<option value="구룡포생활문화센터(아라예술촌)">구룡포생활문화센터(아라예술촌)</option>					
						<option value="귀비고">귀비고</option>					
						<option value="기타">기타</option>					
				</select>
			</li>
			<li>
                <select name="searchCondition" id="searchCondition" title="<spring:message code="comUssUmt.userManageSsearch.searchConditioTitle" />"><!--  -->
                    <option value="0" <c:if test="${mberVO.searchCondition == '0'}">selected="selected"</c:if> ><spring:message code="comUssUmt.userManageSsearch.searchConditionId" /></option><!-- ID  -->
                    <option value="1" <c:if test="${empty mberVO.searchCondition || mberVO.searchCondition == '1'}">selected="selected"</c:if> ><spring:message code="comUssUmt.userManageSsearch.searchConditionName" /></option><!-- Name -->
                </select>
			</li>
			<!-- 검색키워드 및 조회버튼 -->
			<li>
				<input class="s_input" name="searchKeyword" type="text"  size="35" title="<spring:message code="title.search" /> <spring:message code="input.input" />" value='<c:out value="${mberVO.searchKeyword}"/>'  maxlength="255" >
				<input type="submit" class="s_btn" value="<spring:message code="button.inquire" />" title="<spring:message code="title.inquire" /> <spring:message code="input.button" />" />
			</li>
		</ul>
	</div>
	
	<div id="jsGrid"></div>
</div>	
<script>

//최근게시물 JSGRID 셋팅
$('#jsGrid').jsGrid({
	width: '100%'
	, height: 'auto'
	, autoload: true
 	, filtering: true
	, editing: false
	, paging: true
	, pageLoading: true
		, pageSize: 10
		, pageButtoncount: 5
	, pageIndex: 1
	
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