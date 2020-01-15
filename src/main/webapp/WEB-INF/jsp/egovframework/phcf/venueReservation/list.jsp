<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

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
<link rel="stylesheet" type="text/css" href="/css/egovframework/phcf/popup.css"/>
<script src="<c:url value='/js/egovframework/com/cmm/Chart.min.js' />"></script>
<script src="<c:url value='/js/egovframework/com/cmm/Chart.bundle.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/phcf/jsgrid-1.5.3/jsgrid.min.js'/>"></script>
ss
<script>

	var jsonString;
	$(function(){
		$('#jsGrid').jsGrid({
			width: '100%',
			height: 'auto',
			autoload: true,
			editing: true,
			paging: true,
			pageLoading: true,
			pageSize: 5,
			pageIndex: 1,
			pageNextText : "다음",
			pagePrevText : "이전",
			pageFirstText : "처음",
			pageLastText  : "마지막",
			controller: {
				loadData: function(filter) {			
					var d = $.Deferred();
					
					if(filter == null) filter = "";
					$.ajax({
						type: 'POST',
						url: '/venueReservation/selectReservationListToJson.do',
						dataType: 'json',
						data: filter,
						success : function(data){
							jsonString = data.venueReservationRegJson;
							jsonString = JSON.parse(jsonString);
							var list = {
								data: jsonString,
								itemsCount : jsonString.length
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
				 	{name: 	'VENUE', 	title: '대관장소', 	type: 'text', 	editing: false, width: 180, align: "center"},
				 	{name: 	'USE_ROOM', 	title: '대관시설', 	type: 'text', 	editing: false, width: 120, align: "center"},
				 	{name: 	'USE_DATE1', 	title: '1일차대관일시', 	type: 'text', 	editing: false, width: 120, align: "center"},
				 	{name: 	'USE_DATE2', 	title: '2일차대관일시', 	type: 'text', 	editing: false, width: 120, align: "center"},
				 	{name: 	'USE_DATE3', 	title: '3일차대관일시', 	type: 'text', 	editing: false, width: 120, align: "center"},
				 	{name: 	'USE_DATE4', 	title: '4일차대관일시', 	type: 'text', 	editing: false, width: 120, align: "center"},
				 	{name: 	'USE_DATE5', 	title: '5일차대관일시', 	type: 'text', 	editing: false, width: 120, align: "center"},
				 	{name: 	'EVENT_NAME', 	title: '행사명', 	type: 'text', 	editing: false, width: 250, align: "center" },
				 	{name: 	'ORGAN_NAME', 	title: '업체 및 단체명', 	type: 'text', 	editing: false, width: 200, align: "center"},
				 	{name: 	'RESULT', 	
				 		title: '상태', 	
				 		type: 'select', 
				 		items: [
				 			{Name : "접수 요청", Id: "R"},
				 			{Name : "접수 취소", Id: "C"},
				 			{Name : "접수 완료", Id: "S"},
				 			{Name : "접수 취소", Id: "C"},
				 			{Name : "승인 완료", Id: "A"},
				 			{Name : "승인 거절", Id: "D"},
				 			{Name : "승인 취소", Id: "O"}
				 		], 
				 		readOnly: false,
				 		valueType: "string",
				 		valueField: "Id", 
				 		textField: "Name", 
				 		editing: true,
				 		width: 100, 
				 		align: "center"
				 	},
				 	{type: 'control', 
				 		editButton: true, 
				 		deleteButton: false,
				 		width: 50,
				 		updateButtonTooltip: "수정",
				 		cancelEditButtonTooltip: "취소",
				 		searchButtonTooltip: "검색",
				 		clearFilterButtonTooltip: "검색 취소"
				 	}
			]
		});
		
	})
	function about(seq){
		var fileId;
		$.each(jsonString, function(index, item){
			if(item.SEQ == seq){
				$("#about_table tr td").each(function(index2, item2){
					var jsonId = $(item2).attr("id");
					var jsonText = item[jsonId];
					if(jsonId != "FILE_ID"){
						if(!Number.isInteger(jsonText) && jsonText != null && jsonText != '' && jsonText.includes("<br/>")) {
							jsonText = jsonText.replace("<br/>", " ");
						}
						$(item2).text(jsonText);
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
		
		$(".popup_modal").css("top","30%");
		$(".popup_modal").css("left","38%");
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
<h1>대관신청 리스트</h1>
		<div class="search_box">
			<ul>
				<li><!-- 상태-->
	                <select class="floatleft" id="RESULT">
						<option value="">상태</option>
						<option value="R">접수요청</option>
						<option value="C">접수취소</option>
						<option value="S">접수완료</option>
						<option value="A">승인완료</option>	
						<option value="D">승인거절</option>	
						<option value="O">승인취소</option>
					</select>
				</li>
				<li><!-- 조건 -->
	                <select class="floatleft" id="VENUE">
						<option value="">대관 장소</option>					
							<option value="포항문화예술회관">포항문화예술회관</option>					
							<option value="중앙아트홀">중앙아트홀</option>					
							<option value="대잠홀">대잠홀</option>					
							<option value="구룡포생활문화센터(아라예술촌)">구룡포생활문화센터(아라예술촌)</option>					
							<option value="귀비고">귀비고</option>					
							<option value="기타">기타</option>					
					</select>
				</li>
				<li>
	                <select id="searchType"><!--  -->
	                    <option value="USER_ID">아이디</option>
	                    <option value="MANAGER_NAME">이름</option>
	                </select>
				</li>
				<!-- 검색키워드 및 조회버튼 -->
				<li>
					<input class="s_input" id="keyword" type="text"/>
					<input type="submit" class="s_btn" value="검색" onclick="search();"/>
				</li>
			</ul>
		</div>
	
	<div id="jsGrid"></div>
</div>	

<div style="text-align:center;">
	<div class="popup_modal" style="display:none;">
		<table id="about_table" border="">
			<tr>
				<th>번호</th>
				<td id="SEQ"></td>
			</tr>
			<tr>
				<th>대관장소</th>
				<td id="VENUE"></td>
			</tr>
			<tr>
				<th>대관시설</th>
				<td id="USE_ROOM"></td>
			</tr>
			<tr>
				<th>1일차대관일시</th>
				<td id="USE_DATE1"></td>
			</tr>
			<tr>
				<th>2일차대관일시</th>
				<td id="USE_DATE2"></td>
			</tr>
			<tr>
				<th>3일차대관일시</th>
				<td id="USE_DATE3"></td>
			</tr>
			<tr>
				<th>4일차대관일시</th>
				<td id="USE_DATE4"></td>
			</tr>
			<tr>
				<th>5일차대관일시</th>
				<td id="USE_DATE5"></td>
			</tr>
			<tr>
				<th>담당자 이름</th>
				<td id="MANAGER_NAME"></td>
			</tr>
			<tr>
				<th>담당자 직위</th>
				<td id="MANAGER_GRADE"></td>
			</tr>
			<tr>
				<th>행사명</th>
				<td id="EVENT_NAME"></td>
			</tr>
			<tr>
				<th>업체 및 단체명</th>
				<td id="ORGAN_NAME"></td>
			</tr>
			<tr>
				<th>연락처</th>
				<td id="TELNUMBER"></td>
			</tr>
			<tr>
				<th>이메일</th>
				<td id="EMAIL"></td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td id="FILE_ID">
					
				</td>
			</tr>
			<tr>
				<th>신청아이디</th>
				<td id="USER_ID"></td>
			</tr>
			<tr>
				<th>상태</th>
				<td id="RESULT"></td>
			</tr>
			<tr>
				<th>신청일시</th>
				<td id="CREATE_DT"></td>
			</tr>
			<tr>
				<th>수정일시</th>
				<td id="UPDATE_DT"></td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="button" onclick="$('.popup_modal').css('display','none');" value="닫기"/>
				</td>
			</tr>
		</table>
	</div>
</div>
</body>
</html>