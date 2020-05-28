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
<!-- excel download -->
<!-- 필수, SheetJS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.14.3/xlsx.full.min.js"></script>
<!--필수, FileSaver savaAs 이용 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/FileSaver.js/1.3.8/FileSaver.min.js"></script>
<script>
	var searchFilter = new Object();
	var jsonString;
	var resultCode = [
			{Name : "접수 요청", Id: "R"},
 			{Name : "접수 취소", Id: "C"},
 			/*{Name : "접수 완료", Id: "S"},*/
 			{Name : "승인 완료", Id: "A"},
 			{Name : "승인 거절", Id: "D"},
 			{Name : "승인 취소", Id: "O"},
 			{Name : "취소 요청", Id: "B"}
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
						url: '/venueReservation/selectReservationListToJson.do',
						dataType: 'JSON',
						data: searchFilter,
						success : function(data){
							try {
								jsonString = data.venueReservationRegJson;
								jsonString = JSON.parse(jsonString);
								
								var list = {
									data: jsonString,
									itemsCount : JSON.parse(data.venueReservationRegListCnt)
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
			 	{name: 	'EVENT_NAME', 	title: '행사 명', 	type: 'text', 	editing: false, width: 250, align: "center" },
			 	{name: 	'ORGAN_NAME', 	title: '업체 및 단체명', 	type: 'text', 	editing: false, width: 200, align: "center"},
			 	{name: 	'RESULT', title: '상태', 	type: 'select', items: resultCode, readOnly: false,valueType: "string",valueField: "Id", textField: "Name", editing: true,width: 110, align: "center"},
			 	{name: 	'CREATE_DT', title: '등록일', 	type: 'text', editing: false, width: 110, align: "center"},
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
						if(jsonId == "RESULT"){
							$.each(resultCode, function(index3, item3){
								if(item3.Id == jsonText) {
									jsonText = item3.Name;
								}
							});
						}
						
						if(jsonId != "closebtn"){
							if(jsonText == "" || jsonText == null || typeof jsonText == "undefined" || jsonText == undefined) $(item2).text(""); 
							else $(item2).html(jsonText);
						}
					}
					else fileId = jsonText;
				});
			}
		});
		
		$.ajax({
			type: 'POST',
			url: '${externalPageUrl[2]}/cmm/fms/selectFileInfs.do',
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
		$(".popup_modal").css("padding","1%");
		
		$(".popup_bg").css("width","100%");
		$(".popup_bg").css("height","100%");
		$(".popup_bg").css("background","rgb(234,236,238,0.5)");
		$(".popup_bg").css("position","absolute");
		$(".popup_bg").css("top","0");
		$(".popup_bg").css("display","");
	}
	
	function searchFilterSet(){
		var searchType = $("#searchType").val();
		var venue = $("#VENUE").val();
		var result = $("#RESULT").val();
		var keyword = $("#keyword").val();
		
		searchFilter = new Object();
		searchFilter.searchCnd = searchType;
		searchFilter.venueCnd = venue;
		searchFilter.resultCnd = result;
		searchFilter.keyword = keyword;
	}
	function search(){
		searchFilterSet();
		$("#jsGrid").jsGrid("loadData");
		$("#jsGrid").jsGrid("openPage", 1);
	}
	
	/* excel download source  */
	/* excel match */
	var excelHandler = {
			/* file name */
	        getExcelFileName : function(){
	            return '대관 신청 리스트.xlsx';
	        },
	        /* sheet name */
	        getSheetName : function(){
	            return 'sheet1';
	        },
	        /* excel data */
	        getExcelData : function(){
	            return excelJsonArray; 
	        },
	        getWorksheet : function(){
	            return XLSX.utils.json_to_sheet(this.getExcelData());
	        },
	     
		    
	}
	/* excel setting */
	function s2ab(s) { 
	    var buf = new ArrayBuffer(s.length); //convert s to arrayBuffer
	    var view = new Uint8Array(buf);  //create uint8array as viewer
	    for (var i=0; i<s.length; i++) view[i] = s.charCodeAt(i) & 0xFF; //convert to octet
	    return buf;    
	}
	/* excel implement */
	function exportExcel(){ 
	    // step 1. workbook 생성
	    var wb = XLSX.utils.book_new();
		
	    // step 2. 시트 만들기 
	    var newWorksheet = excelHandler.getWorksheet();
	    
	    // step 3. workbook에 새로만든 워크시트에 이름을 주고 붙인다.  
	    XLSX.utils.book_append_sheet(wb, newWorksheet, excelHandler.getSheetName());

	    // step 4. 엑셀 파일 만들기 
	    var wbout = XLSX.write(wb, {bookType:'xlsx',  type: 'binary'});

	    // step 5. 엑셀 파일 내보내기 
	    saveAs(new Blob([s2ab(wbout)],{type:"application/octet-stream"}), excelHandler.getExcelFileName());
	}
	var excelJsonArray = new Array();
	var excelJson = new Object();

	function fn_excelDownload(){
		searchFilterSet();
		
		$.ajax({
			type: 'POST',
			url: '/venueReservation/selectReservationListToJson.do',
			dataType: 'JSON',
			data: searchFilter,
			success : function(data){
				try {
					jsonString = data.venueReservationRegJson;
					jsonString = JSON.parse(jsonString);
					
					var list = {
						data: jsonString,
						itemsCount : JSON.parse(data.venueReservationRegListCnt)
					}	
					excelJsonArray = new Array();
					$.each(jsonString, function(index, item){
					 	excelJson = new Object();
						excelJson.번호=index+1;
						excelJson.행사명=item.EVENT_NAME;
						excelJson.담당자이름=item.MANAGER_NAME;
						excelJson.연락처=item.TELNUMBER;
						excelJson.이메일=item.EMAIL;
						//상태 / 접수 요청: R / 접수 취소: C / 접수 완료: S / 승인 완료: A / 승인 거절: D / 승인 취소: O / 취소 요청 : B
						switch(item.RESULT){
							case "R" : excelJson.상태="접수 요청"; break;
							case "C" : excelJson.상태="접수 취소"; break;
							case "S" : excelJson.상태="접수 완료"; break;
							case "A" : excelJson.상태="승인 완료"; break;
							case "D" : excelJson.상태="승인 거절"; break;
							case "O" : excelJson.상태="승인 취소"; break;
							case "B" : excelJson.상태="취소 요청"; break;
						}
						
						excelJson.대관장소=item.VENUE;
						excelJson.대관시설=item.USE_ROOM;
						excelJson.대관일시=item.useDateTimeLine;
						excelJson.신청일시=item.CREATE_DT;
						excelJson.수정일=item.UPDATE_DT;
						excelJsonArray.push(excelJson);
					})
					exportExcel();
				}
				catch(e){
					alert("오류 발생! \n"+e);
				}
				d.resolve(list);
			}
		});
	}
	
	
</script>

</head>

<div class="board">
	<h1>대관신청 리스트</h1>
	<c:import url="/venueReservation/searchView.do"/>
	<div id="jsGrid"></div>
</div>	

<div style="text-align:center;"> 
	<div class="popup_modal" style="display:none;width:80%;">
		<table id="about_table" class="wTable" style="margin:auto;">
			
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
				<th colspan="2">대관일시</th>
				<td id="useDateTime" colspan="2"></td>				
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
				<th>업체 및 단체명</th>
				<td id="ORGAN_NAME"></td>
				
				<th>연락처</th>
				<td id="TELNUMBER" colspan="2" ></td>
			</tr>
			<tr>
				<th>담당자 이름</th>
				<td id="MANAGER_NAME"></td>
				
				<th>연락처 2</th>
				<td id="TELNUMBER2" colspan="2" ></td>
			</tr>
			<tr>
				<th>신청아이디</th>
				<td id="USER_ID"></td>
				
				<th>이메일</th>
				<td id="EMAIL" colspan="2" ></td>
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

	<div class="buttonarea floatright" style="text-align: right; margin-top:20px; margin-right:20px;">
		<input type="button" id="excel_btn" class="" onclick="fn_excelDownload()" value="Excel Download">
		<input type="button" id="modify_dates" onclick="location.href='/venueReservation/modifyDates.do'" value="날짜 수정"/> 
	</div>
</body>
</html>