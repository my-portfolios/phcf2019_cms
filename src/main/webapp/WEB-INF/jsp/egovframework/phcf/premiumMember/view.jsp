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
			var premiumCode = [{Name: "프리미엄회원", Id: "P"},{Name: "일반회원", Id: "B"}, {Name: "멤버십회원", Id: "M"}, {Name: "무료회원", Id: "N"}];
			var ynCode = [{Name: "예", Id: "Y"},{Name: "아니오", Id: "N"}];
			var jsonString;
			
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
										jsonString = data.payListJson;
										
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
					rowDoubleClick : function(item){
						about(item.item.SEQ);
					},
					noDataContent: '데이터가 없습니다.',
					loadMessage: '조회 중...',
					fields: [
						{name: 	'SEQ', 	title: '번호', 	type: 'text', 	editing: false, align: "center"},
					 	{name: 	'MEM_ID', 	title: '회원 아이디', 	type: 'text', 	editing: false, align: "center"},
					 	{name: 	'MBER_NM', 	title: '회원 명', 	type: 'text', 	editing: false, align: "center"},
// 					 	{name: 	'MEMBERSHIP_TYPE', 	title: '현재 회원 유형', 	type: 'select', editing: false, items: premiumCode, valueType: "string", valueField: "Id", textField: "Name", align: "center"},
					 	{name: 	'PRE_TYPE', 	title: '멤버십 회원 유형', 	type: 'select', editing: false, items: premiumCode, valueType: "string", valueField: "Id", textField: "Name", align: "center"},
					 	{name: 	'PAY_PRICE', 	title: '금액', 	type: 'text', 	editing: false,  align: "center"},
					 	{name: 	'SEND_SMS', title: '문자 수신', 	type: 'select', items: ynCode, editing: false,valueType: "string",valueField: "Id", textField: "Name", align: "center"},
					 	{name: 	'SEND_MAIL', title: '메일 수신', 	type: 'select', items: ynCode, editing: false,valueType: "string",valueField: "Id", textField: "Name", align: "center"},
					 	{name: 	'SEND_POST', title: '우편물 수신', 	type: 'select', items: ynCode, editing: false,valueType: "string",valueField: "Id", textField: "Name", align: "center"},
					 	{name: 	'CREATE_DT', 	title: '신청 일시', 	type: 'text', 	editing: false, align: "center"},
					 	{name: 	'UPDATE_DT', 	title: '수정 일시', 	type: 'text', 	editing: false,  align: "center"},
					 	{name: 	'MEMBERSHIP_START_DT', 	title: '시작 일', 	type: 'text', 	editing: false, align: "center"},
					 	{name: 	'MEMBERSHIP_EXPIRE_DT', 	title: '종료 일', 	type: 'text', 	editing: false, align: "center"},
					 	{name: 	'RESULT', title: '상태', 	type: 'select', items: resultCode, readOnly: false,valueType: "string",valueField: "Id", textField: "Name", editing: true, align: "center"},
					 	/* {name: 	'CHECK_YN', 	title: '관리자 확인 여부', 	type: 'select', editing: false, items: checkCode, valueType: "string",valueField: "Id", textField: "Name",  align: "center"}, */
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
				$("#jsGrid").jsGrid("reset");
			}
			
			function about(seq){
				var fileId;

				$.each(jsonString, function(index, item){
					if(item.SEQ == seq){
						$("#about_table td").each(function(index2, item2){
							var jsonId = $(item2).attr("id");
							var jsonText = item[jsonId];
							
							if(jsonId == "RESULT"){jsonText = codeToString(resultCode, jsonText);}
							else if(jsonId == "PRE_TYPE"){jsonText = codeToString(premiumCode, jsonText);}
							else if(jsonId == "SEND_SMS"){jsonText = codeToString(ynCode, jsonText);}
							else if(jsonId == "SEND_MAIL"){jsonText = codeToString(ynCode, jsonText);}
							else if(jsonId == "SEND_POST"){jsonText = codeToString(ynCode, jsonText);}
							else if(jsonId == "CHECK_YN"){jsonText = codeToString(checkCode, jsonText);}
							
							if(jsonId != "closebtn"){
								if(jsonText == "" || jsonText == null || typeof jsonText == "undefined" || jsonText == undefined) $(item2).text(""); 
								else $(item2).text(jsonText);
							}
							
						});
					}
				});
						
				$(".popup_modal").css("left","10%");
				$(".popup_modal").css("display","");
				$(".popup_modal").css("padding","1%");
				
				$(".popup_bg").css("width","100%");
				$(".popup_bg").css("height","100%");
				$(".popup_bg").css("background","rgb(234,236,238,0.5)");
				$(".popup_bg").css("position","absolute");
				$(".popup_bg").css("top","0");
				$(".popup_bg").css("display","");
			}
			
			function codeToString(codeList, codeid){
				var result = "null";
				$.each(codeList, function(index, item){
					if(item.Id == codeid) {
						result = item.Name;
					}
				});
				
				return result;
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
						<option value="M">멤버십회원</option>
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
						<!-- <option value="MEM_NAME">입금자명</option> -->
						<option value="MEM_ID">아이디</option>
						<option value="MBER_NM">이름</option>
						<option value="PAY_PRICE">금액</option>
					</select>
				</li>
				<li>
					<input type="text" id="search_data" class="s_input" />
					<input type="button" onclick="search();" value="검색" class="s_btn" />
					<span class="btn_b"><a href="<c:url value='/premiumMember/exportExcelMberList.do'/>">Excel Download</a></span>
				</li>
			</ul>
		</div>
		
		<div id="jsGrid"></div>
		
		<div style="text-align:center;">
	<div class="popup_modal" style="display:none;">
		<table id="about_table" class="wTable" style="margin:auto;width:1300px">
			
			<tr>				
				<th>회원아이디</th>
				<td id="MEM_ID"></td>
			
				<th>생년월일</th>
				<td id="BIRTH_DATE"></td>
			</tr>
			<tr>
				<th>기본 주소</th>
				<td id="ADDRESS1"></td>
				
				<th>상세 주소</th>
				<td id="ADDRESS2"></td>
			</tr>	
			<tr>
				<th>회원 유형</th>
				<td id="PRE_TYPE"></td>
				
				<th>결제 금액</th>
				<td id="PAY_PRICE"></td>
			</tr>	
			<tr>
				<th>문자 수신</th>
				<td id="SEND_SMS"></td>
				
				<th>메일 수신</th>
				<td id="SEND_MAIL"></td>
			</tr>
			<tr>
				<th>우편물 수신</th>
				<td id="SEND_POST"></td>
			</tr>	
			<tr>
				<th>상태</th>
				<td id="RESULT"></td>
				
				<th>관리자 확인 여부</th>
				<td id=CHECK_YN></td>
			</tr>	
			<tr>
				<th>신청일시</th>
				<td id="CREATE_DT"></td>
				
				<th>수정일시</th>
				<td id="UPDATE_DT" colspan="2"></td>
			</tr>
			<tr>
				<td colspan="5" id="closebtn" >
					<input type="button" style="margin: 1%;"onclick="$('.popup_modal').css('display','none');$('.popup_bg').css('display','none');" value="닫기"/>
				</td>
			</tr>
		</table>
	</div>
	<div class="popup_bg" style="display:none;"></div>
</div>
		</div>
	</body>
</html>