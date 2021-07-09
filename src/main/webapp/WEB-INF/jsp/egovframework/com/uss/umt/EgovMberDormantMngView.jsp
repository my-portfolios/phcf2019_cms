<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<c:set var="pageTitle">휴먼회원관리</c:set>
<!DOCTYPE html>
<html>
<head>
<title>${pageTitle} <spring:message code="title.create" /></title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="<c:url value='/css/egovframework/com/com.css' />" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<c:url value='/js/egovframework/com/cmm/jquery-1.12.4.min.js' />"></script>
<link type="text/css" rel="stylesheet" href="/js/egovframework/phcf/jsgrid-1.5.3/jsgrid.min.css" />
<link type="text/css" rel="stylesheet" href="/js/egovframework/phcf/jsgrid-1.5.3/jsgrid-theme.min.css" />
    
<script type="text/javascript" src="<c:url value='/js/egovframework/phcf/jsgrid-1.5.3/jsgrid.min.js'/>"></script>

<script type="text/javascript">

var searchFilter = new Object();
$(document).ready(function() {
	
	var sendMailYnArr = [{code : 'Y', codeNm: 'Y'}, {code : 'N', codeNm: 'N'}];
	
	// jsGrid 셋팅
	$('#jsGrid').jsGrid({
		width: '100%'
		, height: 'auto'
		
		, autoload: true
		
		, filtering: false
		, editing: false
		, inserting: false
		, paging: true
		, pageLoading: true
		, pageSize: 10
		, pageButtoncount: 5
		, pageIndex: 1
		, pageNextText : "다음"
		, pagePrevText : "이전"
		, pageFirstText : "처음"
		, pageLastText  : "마지막"
		, pageNavigatorNextText : "..."
		, pageNavigatorPrevText : "..."
		, pagerFormat : "{prev} {pages} {next}"
		, deleteConfirm: "삭제 하시겠습니까?"
		, controller: {
			loadData: function(filter) {
				
				var d = $.Deferred();
// 				var obj = new Object();	
				if(searchFilter.isSearched){
					searchFilter.pageIndex = 1;
					delete searchFilter.isSearched;
				}
				else 
					searchFilter.pageIndex = filter.pageIndex;
				searchFilter.pageSize = filter.pageSize;
				
				
				
				$.ajax({
					type: 'POST',
					url: '/uss/umt/getDormantMber.do',
					dataType: 'JSON',
					data: searchFilter,
					success : function(data){
						try {
							
							if(typeof data != 'object') { data = JSON.parse(data); }
							
							var list = {
								data: data.value,
								itemsCount : data.totCnt
							};
							
						}
						catch(e){
							alert("오류 발생! \n"+e);
						}
						
						d.resolve(list);
					}
				});
				
				return d.promise();
			}
			/* , insertItem: function(item) {
				return $.ajax({
					type: 'POST'
					, url: '/uss/umt/insertDormantMber.do'
					, data: item
					, success: function(result) {
						$("#jsGrid").jsGrid("loadData");
					}
					, error: function(e) {
						console.log('== error :', e);
						alert('삽입 중 오류 발생.');
					}
				});
			}  
			, updateItem: function(item) {
				return $.ajax({
					type: 'POST'
					, url: '/uss/umt/updateDormantMber.do'
					, data: item
					, success: function(result) {
						$("#jsGrid").jsGrid("loadData");
					}
					, error: function(e) {
						console.log('== error :', e);
						alert('업데이트 중 오류 발생.');
					}
				});
			} 
			, deleteItem: function(item) {
				return $.ajax({
					type: 'POST'
					, url : '/uss/umt/deleteDormantMber.do'
					, data: item
					, success: function(result) {
						$("#jsGrid").jsGrid("loadData");
					}
					, error: function(e) {
						console.log('== error :', e.responseText);
						alert('삭제 중 오류 발생.');
					}
				});
			}*/
		}
	
		, noDataContent: '데이터가 없습니다.'
		, loadMessage: '조회 중...'
			
		, fields: [
			{name: 'mberId', title:'아이디', type: 'text', editing: false, width: 50, align: 'center' }
			, {name: 'mberNm', title: '회원명', type: 'text', editing: false, width: 80, align: 'center' }
			, {name: 'groupNm', title: '그룹이름', type: 'text', editing: false, width: 80, align: 'center' }
			, {name: 'moblphonNo', title: '연락처', type: 'text', editing: false, width: 80, align: 'center' }
			, {name: 'mberEmailAdres', title: '이메일', type: 'text', editing: false, width: 80, align: 'center',
				itemTemplate: function(value, item) {
					return $("<div>").attr("class","mailAdrsDiv").text(value);
				}
			}
			, {name: 'sbscrbDe', title: '최초가입일', type: 'text', editing: false, filtering: false, width: 80, align: 'center' }
			, {name: 'sendMailYn', title: '메일수신여부', type: 'select', items: sendMailYnArr, valueField: 'code', textField: 'codeNm', editing: false, filtering: true,
				itemTemplate: function(value, item) {
					return $("<div>").attr("class","sendMailYnDiv").text(value);
				}, 
				width: 80, align: 'center' }
		]
	});
	
    $("#searchKeyword").keypress(function (event) {
    	if (event.which == 13){ // 엔터키를 누르면 실행한다.
        	search();  
    	}
	});
	
});


	

function search(){
	searchFilter = new Object();
	var searchKeyword = $("#searchKeyword").val();
	var searchCondition = $("#searchCondition").val();
	
	searchFilter.searchKeyword = searchKeyword;
	searchFilter.searchCondition = searchCondition;
	searchFilter.isSearched = true;
	
	
	
	
	$("#jsGrid").jsGrid("loadData");
	$("#jsGrid").jsGrid("reset");
}
</script>

</head>

<body>

<form name="frm" id="frm" action="/uss/umt/getDormantMber.do" method="post" >
<div class="board">
	<h1>휴먼회원 관리</h1>
	<div class="search_box" title="<spring:message code="common.searchCondition.msg" />">
		<ul>
			<li><!-- 조건 -->
                <select name="searchCondition" id="searchCondition" title="<spring:message code="comUssUmt.userManageSsearch.searchConditioTitle" />"><!--  -->
                    <option value="0" ><spring:message code="comUssUmt.userManageSsearch.searchConditionId" /></option><!-- ID  -->
                    <option value="1" selected="selected"><spring:message code="comUssUmt.userManageSsearch.searchConditionName" /></option><!-- Name -->
                </select>
			</li>
			<!-- 검색키워드 및 조회버튼 -->
			<li>
				<input class="s_input" id="searchKeyword" name="searchKeyword" type="text"  size="35" title="<spring:message code="title.search" /> <spring:message code="input.input" />"<%--  value='<c:out value="${value.searchKeyword}"/>' --%>  maxlength="255" >
				<input type="button" onclick="search();" id="searchButton" class="s_btn" value="<spring:message code="button.inquire" />" title="<spring:message code="title.inquire" /> <spring:message code="input.button" />" />
				
				
			</li>
		</ul>
	</div>

	
<%-- 	<input type="hidden" id="page_no" name="page_no" value="${paramMap.page_no }"> --%>

	<div class="area">
		<div id="jsGrid"></div>
	</div>

</div>

</form>

</body>
</html>

