<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>신청자 리스트</title>
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
	var globalFilter = new Object();
	var jsonString;
	var articleUrl;
	var resultCode = [
			{Name : "전체", Id: ""},
			{Name : "신청 완료", Id: "R"},
 			{Name : "입금 확인중", Id: "C"},
 			{Name : "입금 확인", Id: "S"},
 			{Name : "접수 완료", Id: "A"},
 			{Name : "취소 신청", Id: "B"},
 			{Name : "취소 완료", Id: "D"},
 			{Name : "환불 완료", Id: "O"}
 		];
	
	$(function(){
		
		$('#jsGrid').jsGrid({
			width: '100%',
			height: 'auto',
			autoload: true,
			editing: true,
			filtering: true,
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
					globalFilter = filter;
					var d = $.Deferred();
					$.ajax({
						type: 'POST',
						url: '/performance/selectPerformanceApplierListJson.do',
						dataType: 'JSON',
						data: filter,
						success : function(data){
							try {
								jsonString = data.performanceAndApllierList;
								
								var list = {
									data: jsonString,
									itemsCount : data.performanceAndApllierListCnt
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
						, url: '/performance/updatePerformanceApplierItem.do'
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
				{name: 	'SEQ', 	title: '번호', 	type: 'text', 	editing: false, width:50, align: "center"},
			 	{name: 	'category', 	title: '신청 종류', 	type: 'text', 	editing: false, width: 70, align: "center"},
			 	{name: 	'subject', 	title: '제목', 	type: 'text', 	editing: false, width: 450, align: "center"},
			 	{name: 	'APL_DATE', 	title: '일시', 	type: 'text', 	editing: false, width: 150, align: "center", filtering: false},
			 	{name: 	'APL_NAME', 	title: '신청자명', 	type: 'text', 	editing: false, width: 80, align: "center"},
			 	{name: 	'APL_PHONE', 	title: '연락처', 	type: 'text', 	editing: false, width: 100, align: "center"},
			 	{name: 	'APL_EMAIL', 	title: '이메일', 	type: 'text', 	editing: false, width: 200, align: "center"},
			 	{name: 	'CREATE_DT', 	title: '신청일', 	type: 'text', 	editing: false, width: 180, align: "center", filtering: false},
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
						
						if(jsonId == "RESULT"){
							$.each(resultCode, function(index3, item3){
								if(item3.Id == jsonText) {
									jsonText = item3.Name;
								}
							});
						}
						
						if(jsonId == "visitorInfo") {
							$(item2).html(jsonText);
						}
						else if(jsonId != "btn"){
							if(jsonText == "" || jsonText == null || typeof jsonText == "undefined" || jsonText == undefined) $(item2).text(""); 
							else $(item2).text(jsonText);
						}
						else {
							console.log(item);
							var bbsId = item.BBS_ID;
							var nttId = item.NTT_ID;
							articleUrl = '${externalPageUrl[2]}/cop/bbs/selectArticleDetail.do?bbsId=' + bbsId + '&nttId=' + nttId;
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
		$(".popup_modal").css("width","80%");
		
		$(".popup_bg").css("width","100%");
		$(".popup_bg").css("height","100%");
		$(".popup_bg").css("background","rgb(234,236,238,0.5)");
		$(".popup_bg").css("position","absolute");
		$(".popup_bg").css("top","0");
		$(".popup_bg").css("display","");
	}
	
	function articleMove(){
		window.open(articleUrl,"","");
	}
	
	
	/* excel download source  */
	/* excel match */
	var excelHandler = {
			/* file name */
	        getExcelFileName : function(){
	            return '공연전시체험행사 신청 리스트.xlsx';
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
		alert('다운로드 완료될때 까지 기다려주세요.')
		delete globalFilter.pageIndex;
		delete globalFilter.pageSize;
		$.ajax({
			type: 'POST',
			url: '/performance/selectPerformanceApplierListJson.do',
			dataType: 'JSON',
			data: globalFilter,
			success : function(data){
				try {
					jsonString = data.performanceAndApllierList;
					
					var list = {
						data: jsonString,
						itemsCount : JSON.parse(data.performanceAndApllierListCnt)
					}	
					excelJsonArray = new Array();
					$.each(jsonString, function(index, item){
					 	excelJson = new Object();
						excelJson.번호=index+1;
						excelJson.신청종류=item.category;
						excelJson.행사명=item.subject;
						excelJson.일시=item.APL_DATE;
						excelJson.장소=item.place;
						excelJson.상태=item.RESULT;
						excelJson.신청아이디=item.MBER_ID;
						excelJson.신청자명=item.APL_NAME;
						excelJson.연락처=item.APL_PHONE;
						excelJson.이메일=item.APL_EMAIL;
						excelJson.참석자=item.visitorInfo.replace(/\<br\/\>/g, ", ");
						excelJson.신청일시=item.CREATE_DT;
						excelJson.수정일시=item.UPDATE_DT;
						
						switch(item.RESULT){
							case "R" : excelJson.상태="신청 완료"; break;
							case "C" : excelJson.상태="입금 확인중"; break;
							case "S" : excelJson.상태="입금 확인"; break;
							case "A" : excelJson.상태="접수 완료"; break;
							case "D" : excelJson.상태="취소 완료"; break;
							case "O" : excelJson.상태="환불 완료"; break;
							case "B" : excelJson.상태="취소 신청"; break;
						}

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
	<h1>신청자 리스트</h1>
	<div id="jsGrid"></div>
</div>	

<div style="text-align:center;">
	<div class="popup_modal" style="display:none;">
		<table id="about_table" class="wTable" style="margin:auto;width:100%">
			
			<tr>
				<th>번호</th>
				<td id="SEQ"></td>
				
				<th>게시물 번호</th>
				<td id="NTT_ID"></td>
			</tr>
			<tr>
				<th>행사명</th>
				<td id="subject"></td>
				
				<th>일시</th>
				<td id="APL_DATE"></td>
			</tr>
			<tr>
				<th>장소</th>
				<td id="place"></td>
				
				<th>상태</th>
				<td id="RESULT"></td>
			</tr>
			<tr>
				<th>신청아이디</th>
				<td id="MBER_ID"></td>
				
				<th>신청자 이름</th>
				<td id="APL_NAME"></td>
			</tr>
			<tr>
				<th>연락처</th>
				<td id="APL_PHONE"></td>
				
				<th>이메일</th>
				<td id="APL_EMAIL"></td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td id="FILE_ID"></td>
				
				<th>참석자</th>
				<td id="visitorInfo"></td>
			</tr>
			<tr>
				<th>신청일시</th>
				<td id="CREATE_DT"></td>
				
				<th>수정일시</th>
				<td id="UPDATE_DT" colspan="2"></td>
			</tr>
			<tr>
				<td colspan="5" id="btn" >
					<input type="button" onclick="articleMove();" value="게시물 바로가기"/>
					<input type="button" onclick="$('.popup_modal').css('display','none');$('.popup_bg').css('display','none');" value="닫기"/>
				</td>
			</tr>
			
		</table>
	</div>
	<div class="popup_bg" style="display:none;"></div>
</div>
<div class="buttonarea floatright" style="text-align: right; margin-top:20px; margin-right:20px;">
		<input type="button" id="excel_btn" class="" onclick="fn_excelDownload()" value="Excel Download">
	</div>
</body>
</html>