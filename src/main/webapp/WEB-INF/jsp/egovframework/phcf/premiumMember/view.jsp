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
			var searchFilter = new Object();
			var resultCode = [{Name : "접수 요청", Id: ""},
				 			{Name : "접수 취소", Id: "C"},
				 			{Name : "승인", Id: "Y"},
				 			{Name : "반려", Id: "N"}];
			var checkCode = [{Name: "승인 대기", Id: "N"},{Name: "승인 완료", Id: "Y"}];
			var premiumCode = [{Name: "프리미엄회원", Id: "P"},{Name: "일반회원", Id: "B"}];
	
			$(function(){
				$('#jsGrid').jsGrid({
					width: '100%',
					height: 'auto',
					autoload: true,
					editing: true,
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
							
							searchFilter.pageIndex = filter.pageIndex;
							searchFilter.pageSize = filter.pageSize;
							
							console.log(filter);
							$.ajax({
								type: 'POST',
								url: '/premiumMember/selectMembershipRegListJson.do',
								dataType: 'JSON',
								data: searchFilter,
								success : function(data){
									try {
										var jsonString = data.payListJson;
										console.log(jsonString);
										jsonString = JSON.parse(jsonString);
										
										console.log(jsonString);
										var list = {
											data: jsonString,
											itemsCount : jsonString == 0 ? 0 : JSON.parse(data.payListCnt)
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
						 updateItem: function(item) {
							return $.ajax({
								type: 'POST'
								, url: '/premiumMember/updateMembershipStatus.do'
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
						{name: 	'SEQ', 	title: '번호', 	type: 'text', 	editing: false, align: "center"},
					 	{name: 	'MEM_ID', 	title: '회원 아이디', 	type: 'text', 	editing: false, align: "center"},
					 	{name: 	'MEM_NAME', 	title: '입금자 명', 	type: 'text', 	editing: false,  align: "center"},
					 	{name: 	'PRE_TYPE', 	title: '멤버십 회원 유형', 	type: 'select', editing: false, items: premiumCode, valueType: "string",valueField: "Id", textField: "Name", align: "center"},
					 	{name: 	'PAY_PRICE', 	title: '금액', 	type: 'text', 	editing: false,  align: "center"},
					 	{name: 	'CREATE_DT', 	title: '신청 일시', 	type: 'text', 	editing: false, align: "center"},
					 	{name: 	'UPDATE_DT', 	title: '수정 일시', 	type: 'text', 	editing: false,  align: "center"},
					 	{name: 	'RESULT', title: '상태', 	type: 'select', items: resultCode, readOnly: false,valueType: "string",valueField: "Id", textField: "Name", editing: true, align: "center"},
					 	{name: 	'CHECK_YN', 	title: '관리자 확인 여부', 	type: 'select', editing: false, items: checkCode, valueType: "string",valueField: "Id", textField: "Name",  align: "center"},
					 	{type: 'control', editButton: true, deleteButton: false, updateButtonTooltip: "수정",cancelEditButtonTooltip: "취소"}
					]
				});
			});
			
			function search(){
				var searchType = $("#search_type").val();
				var type = $("#type").val();
				var check_yn = $("#check_yn").val();
				var search_data = $("#search_data").val();
				
				searchFilter = new Object();
				searchFilter.searchType = searchType;
				searchFilter.type = type;
				searchFilter.check_yn = check_yn;
				searchFilter.search_data = search_data;
				
				$("#jsGrid").jsGrid("loadData");
			}
		</script>
	</head>
	<body>
	
	<div class="board">
		<h1>유료멤버십 리스트 조회</h1>
		
		<!-- 검색영역 -->
		<div class="search_box" >
			<ul>
				<li>
					<select class="floatleft" id="type">
						<option value="">전체</option>
						<option value="B">일반회원</option>
						<option value="P">프리미엄회원</option>
					</select>
				</li>
				<li>
					<select class="floatleft" id="check_yn">
						<option value="">전체</option>
						<option value="N">승인 대기</option>
						<option value="Y">승인 완료</option>
					</select>
				</li>
				<li>
					<select class="floatleft" id="search_type">
						<option value="MEM_NAME">입금자명</option>
						<option value="MEM_ID">아이디</option>
						<option value="PAY_PRICE">금액</option>
					</select>
				</li>
				<li>
					<input type="text" id="search_data" class="s_input" />
					<input type="button" onclick="search();" value="검색" class="s_btn" />
				</li>
			</ul>
		</div>
		
		<div id="jsGrid"></div>
		
		
		</div>
	</body>
</html>