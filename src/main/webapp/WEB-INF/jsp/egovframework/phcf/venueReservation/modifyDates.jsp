<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>대관신청리스트</title>
<link href="<c:url value='/css/egovframework/com/com.css' />" rel="stylesheet" type="text/css">
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/jqueryui.css' />">
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/Chart.min.css' />">
<link type="text/css" rel="stylesheet" href="/js/egovframework/phcf/jsgrid-1.5.3/jsgrid.min.css" />
<link type="text/css" rel="stylesheet" href="/js/egovframework/phcf/jsgrid-1.5.3/jsgrid-theme.min.css" />
<script src="<c:url value='/js/egovframework/com/cmm/jquery.js' />"></script>
<script src="<c:url value='/js/egovframework/com/cmm/jqueryui.js' />"></script>
<link rel="stylesheet" type="text/css" href="/css/egovframework/phcf/popup.css"/>
<script src="<c:url value='/js/egovframework/com/cmm/Chart.min.js' />"></script>
<script src="<c:url value='/js/egovframework/com/cmm/Chart.bundle.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/phcf/jsgrid-1.5.3/jsgrid.min.js'/>"></script>
<link rel="stylesheet" href="/css/egovframework/phcf/datepicker.css" />

<script src="/js/egovframework/phcf/datepicker.js"></script>
<script src="/js/egovframework/phcf/datepicker.ko.js"></script>
<script>
window.onload = function() {
	$('#jsGrid').jsGrid({
		width: '100%',
		height: 'auto',
		autoload: false,
		controller: {
			loadData: function(filter) {			
				var d = $.Deferred();
				
				$.ajax({
					type: 'POST',
					url: '/venueReservation/selectVenueReservationInfo.do',
					data: filter,
					dataType: 'JSON',
					success : function(data){
						d.resolve(data.list);
					}
				});
				
				return d.promise();
			},
			deleteItem: function(item) {
				 return $.ajax({
					type: 'POST'
					, url: '/venueReservation/deleteVenueReservationDates.do'
					, data: item
					, success: function(result) {
						$("#jsGrid").jsGrid("loadData");
					}
					, error: function(e) {
						alert('삭제에 실패했습니다!');
					}
				}); 
			} 
		},
		pageNextText : "다음",
		pagePrevText : "이전",
		pageFirstText : "처음",
		pageLastText  : "마지막",
		pagerFormat : "{prev} {pages} {next}",
		rowClick : function(){return;},
		noDataContent: '데이터가 없습니다.',
		loadMessage: '조회 중...',
		deleteConfirm: '정말 삭제하시겠습니까?\n삭제하시면 복구는 불가능 합니다.',
		fields: [
			{name: 	'SEQ', 	title: '번호', 	type: 'text', 	editing: false, width:80, align: "center"},
		 	{name: 	'VENUE', 	title: '대관 장소', 	type: 'text', 	editing: false, width: 230, align: "center"},
		 	{name: 	'USE_ROOM', 	title: '대관 시설', 	type: 'text', 	editing: false, width: 120, align: "center"},
		 	{name: 	'USE_DATE', 	title: '대관 일', 	type: 'text', 	editing: false, width: 120, align: "center"},
		 	{name: 	'CREATE_DT', title: '등록일', 	type: 'text', editing: false, width: 110, align: "center"},
		 	{type: 'control', editButton: false, deleteButton: true, width: 70,deleteButtonTooltip: "삭제",cancelEditButtonTooltip: "취소"}
		]
	});

};
function searchAction() {
	
	$("#jsGrid").jsGrid("loadData", { SEQ: $("#seq").val() });
}
</script>
<div class="board">
	<h1>대관 날짜 수정</h1>
	<div class="search_box">
		<ul>
			<li><!-- 상태-->
		        대관 번호  &nbsp;&nbsp;
				<input class="s_input" id="seq" type="text"/>
				<input type="button" class="s_btn" value="검색" onclick="searchAction();"/>
			</li>
		</ul>
	</div>
	<div id="jsGrid"></div>
</div>	