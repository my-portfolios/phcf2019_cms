<%@ page contentType="text/html; charset=utf-8"%>
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

<script>
	var jsonString;
	var resultCode = [{Name : "접수 요청", Id: "R"},
 			{Name : "접수 취소", Id: "C"},
 			{Name : "접수 완료", Id: "S"},
 			{Name : "접수 취소", Id: "C"},
 			{Name : "승인 완료", Id: "A"},
 			{Name : "승인 거절", Id: "D"},
 			{Name : "승인 취소", Id: "O"}];
	
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
					
					if(filter == null) filter = "";
					$.ajax({
						type: 'POST',
						url: '/venueReservation/selectReservationListToJson.do',
						dataType: 'JSON',
						data: filter,
						success : function(data){
							try {
								jsonString = data.venueReservationRegJson;
								jsonString = JSON.parse(jsonString);
								console.log(jsonString);
								
								var list = {
									data: jsonString,
									itemsCount : jsonString == 0 ? 0 : JSON.parse(data.venueReservationRegListCnt)
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
						, url: '/venueReservation/updateReservationItem.do'
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
				{name: 	'SEQ', 	title: '번호', 	type: 'text', 	editing: false, width:80, align: "center"},
			 	{name: 	'VENUE', 	title: '대관 장소', 	type: 'text', 	editing: false, width: 230, align: "center"},
			 	{name: 	'USE_ROOM', 	title: '대관 시설', 	type: 'text', 	editing: false, width: 120, align: "center"},
			 	{name: 	'USE_DATE1', 	title: '1일차 대관 일시', 	type: 'text', 	editing: false, width: 110, align: "center"},
			 	{name: 	'USE_DATE2', 	title: '2일차 대관 일시', 	type: 'text', 	editing: false, width: 110, align: "center"},
			 	{name: 	'USE_DATE3', 	title: '3일차 대관 일시', 	type: 'text', 	editing: false, width: 110, align: "center"},
			 	{name: 	'USE_DATE4', 	title: '4일차 대관 일시', 	type: 'text', 	editing: false, width: 110, align: "center"},
			 	{name: 	'USE_DATE5', 	title: '5일차 대관 일시', 	type: 'text', 	editing: false, width: 110, align: "center"},
			 	{name: 	'EVENT_NAME', 	title: '행사 명', 	type: 'text', 	editing: false, width: 250, align: "center" },
			 	{name: 	'ORGAN_NAME', 	title: '업체 및 단체명', 	type: 'text', 	editing: false, width: 200, align: "center"},
			 	{name: 	'RESULT', title: '상태', 	type: 'select', items: resultCode, readOnly: false,valueType: "string",valueField: "Id", textField: "Name", editing: true,width: 110, align: "center"},
			 	{type: 'control', editButton: true, deleteButton: false, width: 70,updateButtonTooltip: "수정",cancelEditButtonTooltip: "취소"}
			]
		});
	});
	
	function about(seq){
		var fileId;

		$.each(jsonString, function(index, item){
			if(item.SEQ == seq){
				$("#about_table td").each(function(index2, item2){
					var jsonId = $(item2).attr("id");
					var jsonText = item[jsonId];
					if(jsonId != "FILE_ID"){
						console.log(jsonId);
						if(!Number.isInteger(jsonText) && jsonText != null && jsonText != '' && jsonText.includes("<br/>")) {
							jsonText = jsonText.replace("<br/>", " ");
						}
						else if(jsonId == "RESULT"){
							$.each(resultCode, function(index3, item3){
								if(item3.Id == jsonText) {
									jsonText = item3.Name;
								}
							});
						}
						
						console.log("aa");
						console.log(jsonId);
						console.log(jsonText);
						console.log("bb");
						
						if(jsonId != "closebtn"){
							if(jsonText == "" || jsonText == null || typeof jsonText == "undefined" || jsonText == undefined) $(item2).text(""); 
							else $(item2).text(jsonText);
						}
					}
					else fileId = jsonText;
				});
			}
		});
		
		$.ajax({
			type: 'POST',
			url: '/cmm/fms/selectFileInfs.do',
			data: {
				"param_atchFileId" : fileId 
			},
			dataType: 'html',
			success : function(data){
				$("#FILE_ID").html(data);
			}
		});
		
		
		$(".popup_modal").css("left","10%");
		$(".popup_modal").css("display","");
	}
	
	function search(){
		var searchType = $("#searchType").val();
		var venue = $("#VENUE").val();
		var result = $("#RESULT").val();
		var keyword = $("#keyword").val();
		
		var searchObject = new Object();
		searchObject.searchCnd = searchType;
		searchObject.venueCnd = venue;
		searchObject.resultCnd = result;
		searchObject.keyword = keyword;
		
		$("#jsGrid").jsGrid("loadData", searchObject);
	}
</script>

</head>

<div class="board">
	<h1>대관신청 리스트1</h1>
	<c:import url="/venueReservation/searchView.do"/>
	<div id="jsGrid"></div>
</div>	

<div style="text-align:center;">
	<div class="popup_modal" style="display:none;">
		<table id="about_table" class="wTable" style="margin:auto;width:1300px">
			
			<tr>
				<th>번호</th>
				<td id="SEQ"></td>
				
				<th>행사명</th>
				<td id="EVENT_NAME" colspan="2"></td>
			</tr>
			<tr>
				<th>대관장소</th>
				<td id="VENUE"></td>
				
				<th>대관시설</th>
				<td id="USE_ROOM" colspan="2"></td>
			</tr>
			<tr>
				<th style="text-align:center;">1일차대관일시</th>
				
				<th style="text-align:center;">2일차대관일시</th>
				
				<th style="text-align:center;">3일차대관일시</th>
				
				<th style="text-align:center;">4일차대관일시</th>
				
				<th style="text-align:center;">5일차대관일시</th>
			</tr>
			<tr>
				<td id="USE_DATE1"></td>
				<td id="USE_DATE2"></td>
				<td id="USE_DATE3"></td>
				<td id="USE_DATE4"></td>
				<td id="USE_DATE5"></td>
			</tr>	
			<tr>
				<th>신청일시</th>
				<td id="CREATE_DT"></td>
				
				<th>수정일시</th>
				<td id="UPDATE_DT" colspan="2"></td>
			</tr>
			<tr>
				<th>상태</th>
				<td id="RESULT"></td>
					
				<th>첨부파일</th>
				<td id="FILE_ID" colspan="2"></td>
			</tr>
			<tr>
				<th colspan="5" style="text-align:center;">신청자 정보</th>
			</tr>
			<tr>
				<th>업체 및 단체명</th>
				<td id="ORGAN_NAME"></td>
				
				<th>연락처</th>
				<td id="TELNUMBER" colspan="2" ></td>
			</tr>
			<tr>
				<th>담당자 이름</th>
				<td id="MANAGER_NAME"></td>
				
				<th>담당자 직위</th>
				<td id="MANAGER_GRADE" colspan="2" ></td>
			</tr>
			<tr>
				<th>신청아이디</th>
				<td id="USER_ID"></td>
				
				<th>이메일</th>
				<td id="EMAIL" colspan="2" ></td>
			</tr>
			<tr>
				<td colspan="5" id="closebtn" >
					<input type="button" onclick="$('.popup_modal').css('display','none');" value="닫기"/>
				</td>
			</tr>
		</table>
	</div>
</div>
</body>
</html>