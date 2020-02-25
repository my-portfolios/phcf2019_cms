<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>단체 접수 관리</title>
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
	var searchFilter = new Object();
	var jsonString;
	var resultCode =  [
			{Name : "승인완료", Id: "Y"},
 			{Name : "반려", Id: "N"},
 			{Name : "처리중", Id: "I"},
 			{Name : "보류", Id: "D"},
 			{Name : "접수완료", Id: "C"},
 		]; 
	
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
					
					$.ajax({
						type: 'POST',
						url: '/busking/selectGroupListToJson.do',
						dataType: 'JSON',
						data: searchFilter,
						success : function(data){
							try {
								console.log(data);
								jsonString = data.groupListJson;
								jsonString = JSON.parse(jsonString);
								
								var list = {
									data: jsonString,
									itemsCount : jsonString == 0 ? 0 : JSON.parse(data.groupListCnt)
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
						, url: '/busking/updateApprove.do'
						, data: item
						, success: function(result) {
							$("#jsGrid").jsGrid("loadData");
						}
						, error: function(e) {
							alert('변경에 실패했습니다!');
						}
					});
				}, 
				deleteItem: function(item) {
					return $.ajax({
						type: 'POST'
						, url: '/busking/deleteGroup.do'
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
				{name: 	'TEAM_NAME', 	title: '팀이름', 	type: 'text', 	editing: false, width:80, align: "center"},
			 	{name: 	'HEAD_NAME', 	title: '대표자명', 	type: 'text', 	editing: false, width: 230, align: "center"},
			 	{name: 	'PHONE', 	title: '연락처', 	type: 'text', 	editing: false, width: 120, align: "center"},
			 	{name: 	'REG_DATE', 	title: '등록일', 	type: 'text', 	editing: false, width: 110, align: "center"},
			 	{name: 	'AREA', 	title: '지역', 	type: 'text', 	editing: false, width: 110, align: "center"},
			 	{name: 	'GENRE', 	title: '장르', 	type: 'text', 	editing: false, width: 110, align: "center"},
			 	{name: 	'APPROVE_YN', title: '상태', 	type: 'select', items: resultCode, readOnly: false,valueType: "string",valueField: "Id", textField: "Name", editing: true,width: 110, align: "center"},
			 	{type: 'control', editButton: true, deleteButton: true, width: 80,updateButtonTooltip: "수정",cancelEditButtonTooltip: "취소"}
			]
		});
		
	});
	/* ROW더블클릭시  */
	function about(seq){
		var fileId;

		$.each(jsonString, function(index, item){
			if(item.SEQ == seq){
				$("#about_table td").each(function(index2, item2){
					var jsonId = $(item2).attr("id");
					var jsonText = item[jsonId];
					console.log(jsonId);
					if(jsonId != "T_FILE"){
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
						
						if(jsonId != "closebtn"){
							if(jsonText == "" || jsonText == null || typeof jsonText == "undefined" || jsonText == undefined) $(item2).text(""); 
							else $(item2).text(jsonText);
						}
					}
					else {
						fileId = jsonText;
						console.log("bb  " + fileId);
					}
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
				$("#T_FILE").html(data);
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
	
	function search(){
		var genre = $("#genre").val();
		var area = $("#areas").val();
		var approveYN = $("#approve").val();
		var searchCondition = $("#searchCondition").val();
		var searchKeyword = $("#searchKeyword").val();
		
	 	searchFilter = new Object();
		searchFilter.genre = genre;
		searchFilter.area = area;
		searchFilter.approveYN = approveYN;
		searchFilter.searchCondition = searchCondition;
		searchFilter.searchKeyword = searchKeyword;
		$("#jsGrid").jsGrid("loadData");
		/* $("#jsGrid").jsGrid("search",{ genre:genre, area:area, searchKeyword:searchKeyword }).done(function(){
			console.log("?")
		}); */
	}
	function updateSearchCount(obj){
		var editPageSize = $(obj).text();
		if($("#jsGrid").jsGrid("option","pageSize")==editPageSize){
			$("#jsGrid").jsGrid("option","pageSize",10 );	
		}else{
			$("#jsGrid").jsGrid("option","pageSize", editPageSize);
		}
		
	}
	
</script>

</head>

<div class="board">
	<h1>단체 접수 관리</h1>
	<c:import url="/busking/searchView.do"/>
	<div id="jsGrid"></div>
</div>	
<div style="text-align:center;">
	<div class="popup_modal" style="display:none;">
		<table id="about_table" class="wTable" style="margin:auto;width:100%">
			
			<tr>
				<th>번호</th>
				<td id="SEQ"></td>
				
				<th>팀명</th>
				<td id="TEAM_NAME" colspan="2"></td>
			</tr>
			<tr>
				<th>대표자</th>
				<td id="HEAD_NAME"></td>
				
				<th>연락처</th>
				<td id="PHONE" colspan="2"></td>
			</tr>
			<tr>
				<th style="text-align:center;"></th>
				<th style="text-align:center;"></th>
				<th style="text-align:center;"></th>
				<th style="text-align:center;"></th>
				<th style="text-align:center;"></th>
			</tr>
			<tr>
			</tr>	
			<tr>
				<th>신청일시</th>
				<td id="REG_DATE"></td>
				
				<th>상태</th>
				<td id="APPROVE_YN" colspan="2"></td>
			</tr>
			<tr>
				<th>장르</th>
				<td id="GENRE"></td>
					
				<th>지역</th>
				<td id="AREA" colspan="2"></td>
			</tr>
			
			<tr>
				<th>인원</th>
				<td id="PERSONNEL"></td>
				
				<th>프로필</th>
				<td id="PROFILE" colspan="2" ></td>
			</tr>
			<tr>
				<th>멤버</th>
				<td id="MBERS"></td>
				
				<th>사용장비</th>
				<td id="EQUIPMENT" colspan="2" ></td>
			</tr>
			<tr>
				<th>팀 관련 링크</th>
				<td id="SNS_LINK"></td>
				
				<th>팀 영상 링크</th>
				<td id="SNS_VIDEO" colspan="2" ></td>
			</tr>
			<tr>
				<th>대표사진</th>
				<td id="T_FILE"></td>
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
</body>
</html>