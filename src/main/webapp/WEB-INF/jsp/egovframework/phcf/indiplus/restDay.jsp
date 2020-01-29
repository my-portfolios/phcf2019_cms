<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html> 
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>유료멤버십 신청</title>
		<link href="<c:url value='/css/egovframework/com/com.css' />" rel="stylesheet" type="text/css">
		<script src="<c:url value='/js/egovframework/com/cmm/jquery.js' />"></script>
		
		<link type="text/css" rel="stylesheet" href="/js/egovframework/phcf/jsgrid-1.5.3/jsgrid.min.css" />
		<link type="text/css" rel="stylesheet" href="/js/egovframework/phcf/jsgrid-1.5.3/jsgrid-theme.min.css" />
		<script type="text/javascript" src="<c:url value='/js/egovframework/phcf/jsgrid-1.5.3/jsgrid.min.js'/>"></script>
		
		<script>
			var jsonString;
			
			$(function(){
				$('#jsGrid').jsGrid({
					width: '100%',
					height: 'auto',
					autoload: true,
					editing: true,
					inserting: true,
					paging: true,
					pageLoading: true,
					pageSize: 10,
					pageIndex: 1,
					pageNextText : "다음",
					pagePrevText : "이전",
					pageFirstText : "처음",
					pageLastText  : "마지막",
					pagerFormat : "{prev} {pages} {next}",
					controller: {
						loadData: function(filter) {			
							var d = $.Deferred();
							
							console.log(filter);
							$.ajax({
								type: 'POST',
								url: '/indiplus/selectRestDayListJson.do',
								dataType: 'JSON',
								data: filter,
								success : function(data){
									try {
										jsonString = data.restDayList;
										var list = {
											data: jsonString,
											itemsCount : data.restDayListCnt
										}	
									}
									catch(e){
										alert("오류 발생! \n"+e);
									}
									d.resolve(list);
								}
							});
							
							return d.promise();
						},
						insertItem: function(item) {
							return $.ajax({
								type: 'POST'
								, url: '/indiplus/insertRestDay.do'
								, data: item
								, success: function(result) {
									$("#jsGrid").jsGrid("loadData");
								}
								, error: function(e) {
									alert('등록에 실패했습니다!');
								}
							});
						}, 
						 updateItem: function(item) {
							return $.ajax({
								type: 'POST'
								, url: '/indiplus/updateRestDay.do'
								, data: item
								, success: function(result) {
									$("#jsGrid").jsGrid("loadData");
								}
								, error: function(e) {
									alert('변경에 실패했습니다!');
								}
							});
						}  
					},
					rowClick : function(){return;},
					noDataContent: '데이터가 없습니다.',
					loadMessage: '조회 중...',
					fields: [
						{name: 	'SEQ', 	title: '번호', 	type: 'text', inserting:false,	editing: false, align: "center"},
					 	{name: 	'DATE', 	title: '날짜', 	type: 'text', 	editing: true, readOnly: false, align: "center"},
					 	{name: 	'CREATE_DT', 	title: '생성일시', 	type: 'text', inserting:false,editing: false, align: "center"},
					 	{name: 	'UPDATE_DT', 	title: '수정일시', 	type: 'text', inserting:false,	editing: false, align: "center"},
					 	{type: 'control', editButton: true, deleteButton: false, updateButtonTooltip: "수정",cancelEditButtonTooltip: "취소"}
					]
				});
			});
		</script>
	</head>
	<body>
	
		<div class="board">
			<h1>인디플러스 포항 휴관일 리스트 조회</h1>
		
			<div id="jsGrid"></div>
		</div>
	</body>
</html>