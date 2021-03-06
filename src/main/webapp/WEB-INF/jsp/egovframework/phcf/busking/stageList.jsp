<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>무대 신청자 리스트</title>
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
	var resultCode =  ${approveCodeList};
	var placeCode = ${palceCodeList};
	var timeCode = ${timeCodeList};
	
	$(function(){
		
		$('#jsGrid').jsGrid({
			width: '100%',
			height: 'auto',
			autoload: true,
			editing: true,
			paging: true,
			selecting: true,
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
					searchFilter.pageCheck = 1;
					$.ajax({
						type: 'POST',
						url: '/busking/selectStageListToJson.do',
						dataType: 'JSON',
						data: searchFilter,
						success : function(data){
							try {
								console.log(data);
								jsonString = data.stageListJson;
								jsonString = JSON.parse(jsonString);
								
								
								var list = {
									data: jsonString,
									itemsCount : jsonString == 0 ? 0 : JSON.parse(data.stageListCnt)
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
						, url: '/busking/deleteBusking.do'
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
			rowClick : function(item){
				var index = item.itemIndex;
				var checkBoxObj=$("table.jsgrid-table:eq(1) tbody input:checkbox").eq(index);
				if($(checkBoxObj).prop("checked")==true){
					$(checkBoxObj).prop("checked",false);
				}else{
					$(checkBoxObj).prop("checked",true);
				};
			},
			rowDoubleClick : function(item){
				about(item.item.SEQ);
			},
			noDataContent: '데이터가 없습니다.',
			loadMessage: '조회 중...',
			fields: [
				{name:   'CHECK', type: 'checkbox', editing: false, readOnly: false, headerTemplate: function() { return $("<input></input>").attr("type","checkbox").attr("id","allCheck") } },
				{name: 	'TEAM_NM', 	title: '단체명', 	type: 'text', 	editing: false, width:100, align: "center"},
			 	{name: 	'HEAD_NM', 	title: '대표자명', 	type: 'text', 	editing: false, width: 150, align: "center"},
			 	{name: 	'PHONE', 	title: '연락처', 	type: 'text', 	editing: false, width: 120, align: "center"},
			 	{name: 	'REG_DATE', 	title: '등록일', 	type: 'text', 	editing: false, width: 110, align: "center"},
			 	{name: 	'PLACE', 	title: '공간명', 	type: 'select', items: placeCode, readOnly: false, valueType: "string",valueField: "Id", textField: "Name", editing: true, width: 110, align: "center"},
			 	{name: 	'DATE_BGN', 	title: '날짜', 	type: 'text', 	editing: true, width: 110, align: "center"},
			 	{name: 	'TIME', 	title: '사용기한', 	type: 'select', items: timeCode, readOnly: false, valueType: "string",valueField: "Id", textField: "Name", editing: true, width: 110, align: "center"},
			 	{name: 	'APPROVE_YN', title: '상태', 	type: 'select', items: resultCode, readOnly: false,valueType: "string",valueField: "Id", textField: "Name", editing: true,width: 110, align: "center"},
			 	{type: 'control', editButton: true, deleteButton: true, width: 80,updateButtonTooltip: "수정",cancelEditButtonTooltip: "취소"}
			]
		});
		
		$("#allCheck").change(function(){
			if($("#allCheck").is(":checked")){
	            $(".jsgrid-cell input:checkbox").prop("checked",true);
	        }else{
	        	$(".jsgrid-cell input:checkbox").prop("checked",false);
	        }
		})
		var dateBgn;
	    /* var dateEnd; */
	    
		var searchDateBgn = $("#searchDateBgn").datepicker({
	        language: 'ko',
	        dateFormat: 'yy-mm-dd',
	        showOtherMonths: true,
	        selectOtherMonths: true,
	        showMonthAfterYear: true,
	        dayNamesMin: ['일','월', '화', '수', '목', '금', '토'],
	        monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
	        monthNames: ['년 1월','년 2월','년 3월','년 4월','년 5월','년 6월','년 7월','년 8월','년 9월','년 10월','년 11월','년 12월'],
	        nextText: '다음 달',
	        prevText: '이전 달',
       	   onSelect:function(formattedDate,date,inst){
       			dateBgn=date;
            	if(dateBgn==null ||dateBgn == '' /* || dateEnd==null  || dateEnd == ''  */) {
            		return; 
            	}
        		/* if( dateBgn > dateEnd ) {
        			alert('시작날짜가 끝날짜 보다 큽니다. 다시 선택해주세요');
        			inst.el.value='';
        			return;
        		} */
            }
	    });
		var searchDateEnd = $("#searchDateEnd").datepicker({
	        language: 'ko',
	        dateFormat: 'yy-mm-dd',
	        showOtherMonths: true,
	        selectOtherMonths: true,
	        showMonthAfterYear: true,
	        dayNamesMin: ['일','월', '화', '수', '목', '금', '토'],
	        monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
	        monthNames: ['년 1월','년 2월','년 3월','년 4월','년 5월','년 6월','년 7월','년 8월','년 9월','년 10월','년 11월','년 12월'],
	        nextText: '다음 달',
	        prevText: '이전 달',
       	   onSelect:function(formattedDate,date,inst){
            	dateEnd=date;
            	if(dateBgn==null || dateEnd==null ||dateBgn == '' || dateEnd == '') {
            		return; 
            	}
            	console.log(dateBgn)
            	console.log(dateEnd)
        		if( dateBgn > dateEnd ) {
        			alert('시작날짜가 끝날짜 보다 큽니다. 다시 선택해주세요');
        			inst.el.value='';
        			return;
        		}
            }
	    });
		
	});
	
	
	
	/* ROW더블클릭시  */
	function about(seq){
		var fileId;

		$.each(jsonString, function(index, item){
			if(item.SEQ == seq){
				$("#about_table td").each(function(index2, item2){
					var jsonId = $(item2).attr("id");
					console.log("jsonId:"+jsonId)
					var jsonText = item[jsonId];
					console.log("jsonText"+jsonText)
					if(jsonId != "FILE"){
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
						else if(jsonId=="APPROVE_YN"){
							switch(jsonText){
								case 'Y': jsonText='승인완료'; break;
								case 'D': jsonText='보류'; break;
								case 'N': jsonText='반려'; break;
								case 'I': jsonText='처리중'; break;
								case 'C': jsonText='접수완료'; break;
							}
						}
						if(jsonId != "closebtn"){
							if(jsonText == "" || jsonText == null || typeof jsonText == "undefined" || jsonText == undefined) $(item2).text(""); 
							else $(item2).text(jsonText);
						}
					}
				
					else {
						fileId = jsonText;
					}
				});
			}
		});
		$.ajax({
			type: 'POST',
			url: '${externalPageUrl[0]}/cmm/fms/selectFileInfs.do',
			data: {
				"param_atchFileId" : fileId 
			},
			dataType: 'html',
			success : function(data){
				$("#FILE").html(data);
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
		var place = $("#searchPlace").val();
		var approveYN = $("#serachApprove").val();
		var searchCondition = $("#searchCondition").val();
		var searchKeyword = $("#searchKeyword").val();
		var searchDateBgn=$("#searchDateBgn").val();
		var searchDateEnd=$("#searchDateEnd").val();
		var searchTime=$("#searchTime").val();
	 	searchFilter = new Object();
		searchFilter.place = place;
		searchFilter.approveYN = approveYN;
		searchFilter.searchDateBgn = searchDateBgn;
		searchFilter.searchDateEnd = searchDateEnd;
		searchFilter.searchTime = searchTime;
		searchFilter.searchCondition = searchCondition;
		searchFilter.searchKeyword = searchKeyword;
	}
	
	function search(){
		searchFilterSet();
		$("#jsGrid").jsGrid("loadData");
		/* $("#jsGrid").jsGrid("search",{ genre:genre, area:area, searchKeyword:searchKeyword }).done(function(){
			console.log("?")
		}); */
	}
	function updateSearchCount(obj){
		var editPageSize = $(obj).val();
		if($("#jsGrid").jsGrid("option","pageSize")==editPageSize){
			$("#jsGrid").jsGrid("option","pageSize",10 );	
		}else{
			$("#jsGrid").jsGrid("option","pageSize", editPageSize);
		}
	}
	function fn_updateMulti(item){
		
		var arrayParam = new Array();
		var items = $("#jsGrid").jsGrid("option", "data");
		var checkedObj = $("table.jsgrid-table:eq(1) tbody input:checkbox:checked");
		
		if(checkedObj!=null && checkedObj.length!=0){
			$(checkedObj).each(function(){
				var index = $("table.jsgrid-table:eq(1) tbody input:checkbox").index(this);
				arrayParam.push(items[index].SEQ);
			});
		}else{
			return;
		}
		
		$.ajax({
			type: 'POST'
			, url: '/busking/updateApproveMulti.do'
			, dataType:'json'
			, data: {
				 "arrayParam" : arrayParam ,
				 "approveYN":item
			}
			, success: function(result) {
				alert('변경에 성공하였습니다.!');
				$("#jsGrid").jsGrid("loadData");
			}
			, error: function(e) {
				alert('변경에 실패했습니다!');
			}
		});
	}
	function fn_update(item){
		var arrayParam = new Array();
		arrayParam.push($("#SEQ").text());
		$.ajax({
			type: 'POST'
			, url: '/busking/updateApproveMulti.do'
			, dataType:'json'
			, data: {
				"arrayParam":arrayParam,
				 "approveYN":item
			}
			, success: function(result) {
				alert('변경에 성공하였습니다.!');
				$("#jsGrid").jsGrid("loadData");
			}
			, error: function(e) {
				alert('변경에 실패했습니다!');
			}
		});
		
		$('.popup_modal').css('display','none');$('.popup_bg').css('display','none');
	}

	/* excel download source  */
	/* excel match */
	var excelHandler = {
			/* file name */
	        getExcelFileName : function(){
	            return '무대 신청자 리스트.xlsx';
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
	        }
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
		
		searchFilter.pageCheck = 0;
		
		$.ajax({
			type: 'POST',
			url: '/busking/selectStageListToJson.do',
			dataType: 'JSON',
			data: searchFilter,
			success : function(data){
				try {
					jsonString = data.stageListJson;
					jsonString = JSON.parse(jsonString);
					
					var list = {
						data: jsonString,
						itemsCount : jsonString == 0 ? 0 : JSON.parse(data.stageListCnt)
					}	
					excelJsonArray = new Array();
					$.each(jsonString, function(index, item){
						excelJson = new Object();
						excelJson.번호=index+1;
						excelJson.프로그램명=item.PROG_NM;
						excelJson.팀명=item.TEAM_NM;
						excelJson.대표명=item.HEAD_NM;
						excelJson.휴대폰=item.PHONE;
						switch(item.APPROVE_YN){
							case 'Y': excelJson.승인여부='승인완료'; break;
							case 'D': excelJson.승인여부='보류'; break;
							case 'N': excelJson.승인여부='반려'; break;
							case 'I': excelJson.승인여부='처리중'; break;
							case 'C': excelJson.승인여부='접수완료'; break;
						}
						excelJson.장소=item.PLACE;
						excelJson.날짜=item.DATE_BGN;
						excelJson.시간=item.TIME;
						excelJson.등록일=item.REG_DATE;
						excelJson.장비=item.EQUIPMENT;
						
						excelJsonArray.push(excelJson);
					})
					exportExcel();
				}
				catch(e){
					alert("오류 발생! \n"+e);
				}
			}
		});
		
		
	}
	
	
</script>

</head>

<div class="board">
	<h1>무대 신청자 리스트</h1>
	<c:import url="/busking/searchStageView.do"/>
	<div id="jsGrid"></div>
	<div class="buttonarea floatright" style="text-align: right; margin-top:20px;">
		<input type="button" id="insert_btn" class="" onclick="location.href='/busking/insertStage.do'" value="등록">
		<input type="button" id="insert_btn" class="" onclick="fn_updateMulti('승인완료')" value="승인">
		<input type="button" id="insert_btn" class="" onclick="fn_updateMulti('보류')" value="보류">
		<input type="button" id="insert_btn" class="" onclick="fn_updateMulti('반려')" value="반려">
		<input type="button" id="insert_btn" class="" onclick="fn_updateMulti('처리중')" value="처리중">
		<input type="button" id="excel_btn" class="" onclick="fn_excelDownload()" value="Excel Download">
	</div>
</div>	
<div style="text-align:center;">
	<div class="popup_modal" style="display:none;">
		<table id="about_table" class="wTable" style="margin:auto;width:100%;min-width:850px;">
			
			<tr>
				<th width="100px">번호</th>
				<td id="SEQ"></td>
				
				<th width="100px">팀명</th>
				<td id="TEAM_NM" colspan="2"></td>
			</tr>
			<tr>
				<th>대표자</th>
				<td id="HEAD_NM"></td>
				
				<th>연락처</th>
				<td id="PHONE" colspan="2"></td>
			</tr>
			<tr>
				<th>신청일시</th>
				<td id="REG_DATE"></td>
				
				<th>날짜</th>
				<td id="DATE_BGN" colspan="2"></td>
			</tr>
			<tr>
				<th>프로그램명</th>
				<td id="PROG_NM"></td>
					
				<th>장소</th>
				<td id="PLACE" colspan="2"></td>
			</tr>
			
			<tr>
				<th>상태</th>
				<td id="APPROVE_YN" ></td>
				
				<th>시간</th>
				<td id="TIME" colspan="2" ></td>
			</tr>
			<tr>
				<th>소개</th>
				<td id="INTRO"></td>
				
				<th>사용장비</th>
				<td id="EQUIPMENT" colspan="2" ></td>
			</tr>
			<tr>
				<th>대표사진</th>
				<td id="FILE" colspan="3" align="left"></td>
			</tr>
			<tr>
				<td colspan="4" id="closebtn" >
					<input type="button" style="margin: 1%;"onclick="$('.popup_modal').css('display','none');$('.popup_bg').css('display','none');" value="닫기"/>
					<input type="button" id="insert_btn" class="" onclick="fn_update('승인완료')" value="승인">
					<input type="button" id="insert_btn" class="" onclick="fn_update('보류')" value="보류">
					<input type="button" id="insert_btn" class="" onclick="fn_update('반려')" value="반려">
					<input type="button" id="insert_btn" class="" onclick="fn_update('처리중')" value="처리중">
				</td>
			</tr>
		</table>
	</div>
	<div class="popup_bg" style="display:none;"></div>
</div>
</body>
</html>


