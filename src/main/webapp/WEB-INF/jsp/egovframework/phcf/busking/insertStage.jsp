<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>무대 신청자 등록</title>
<link href="<c:url value='/css/egovframework/com/com.css' />" rel="stylesheet" type="text/css">
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/jqueryui.css' />">
<script src="<c:url value='/js/egovframework/com/cmm/jquery.js' />"></script>
<script src="<c:url value='/js/egovframework/com/cmm/jqueryui.js' />"></script>
<link rel="stylesheet" type="text/css" href="/css/egovframework/phcf/popup.css"/>


<script src="/js/egovframework/phcf/CommonMethod.js"></script>
<style>
	label.error{
	  margin-left:10px;
	  color:red;
	}
</style>
</head>
<div class="board">
	<h1>관리자 무대 신청</h1>
	<div class="search_box">
		<li>포항문화재단 자체 행사 등 관리자 권한으로  해당 날짜 사용신청을 막을시 등록 바랍니다. </li>
	</div>
</div>
<form name="frm" id="frm" action="#" method="post"  enctype="multipart/form-data" onsubmit="return fn_qna()" >
	
	<input type="hidden" name="GROUP_SEQ" value="">

	<div class="wTableFrm">
	<!-- 등록폼 -->
	<table class="wTable" summary="버스킹 공간 신청" />
	<tbody>
		<tr>
			<th><label for="TEAM_NM">단체명</label> <span class="pilsu">*</span></th>
			<td class="left">
				<input id="TEAM_NM"  name="TEAM_NM" style="ime-mode:active;" type="text" class="text padding5 width20per" value="${applyGroupHistory.TEAM_NAME }" title="단체명을 입력하세요" />               
			</td>
		</tr>
		<tr>
			<th><label for="PROG_NM">프로그램명</label> <span class="pilsu">*</span></th>
			<td class="left">
			   <input id="PROG_NM"  name="PROG_NM" style="ime-mode:active;" type="text" class="text padding5 width20per" value="${contentInfo.MH_PROGRAM }" title="프로그램명을 입력하세요" />
			</td>
		</tr>
			<tr>
				<th><label for="PLACE">장소</label> <span class="pilsu">*</span></th>
				<td class="left" >
					<!-- onchange="javascript:changeDisabledDatesSet();" -->
					<select class="select" id="PLACE" name="PLACE" >
					<option value="">선택</option>
					<c:forEach var="placeCodeNm" items="${placeCodeNmList}" varStatus="i">
						<option value="${placeCodeNm}" <c:if test="${contentInfo.CD_VALUE eq placeCodeNm }">selected</c:if> >${placeCodeNm}</option>	
					</c:forEach>
				</select>
				</td>
			</tr>
			<tr>
				<th><label for="DATE">날짜</label> <span class="pilsu">*</span></th>
				<td class="left">				
				<input type="text" id="datepickerBgn" class="datepicker2"  name="DATE_BGN" value="" readonly="readonly" />
							~
				<input type="text" id="datepickerEnd" class="datepicker2"  name="DATE_END" value="" onchange="javascript:chkdate();" readonly="readonly" />
				<br>
                <span style=" margin-top:100px;color="#4da314"">
			              ※ 예시) 3월 매주 수요일 희망 :  3. 8 / 3. 15 / 3. 22 / 3. 29 (총 4번 신청) (O) &nbsp; / &nbsp; 3. 8 ~ 3. 29 (X) 
                </span>              
				</td>
			</tr>
			<tr>
				<th><label for="TIME">시간</label> <span class="pilsu">*</span></th>
				<td class="left" >
					<select class="select" id="TIME" name="TIME">
					<option value="">선택</option>
				<c:forEach var="timeCodeNm" items="${timeCodeNmList}" varStatus="i">
					<option value="${timeCodeNm}">${timeCodeNm}</option>	
				</c:forEach>
				</select>
				</td>
			</tr>
			<tr>
				<th><label for="HEAD_NM">대표자</label> <span class="pilsu">*</span></th>
				<td class="left">
					<input id="HEAD_NM"  name="HEAD_NM" style="ime-mode:active;" type="text" class="text padding5 width20per" value="" title="대표자을 입력하세요"/>
				</td>
			</tr>
			<tr>
				<th><label for="PHONE">연락처</label> <span class="pilsu">*</span></th>
				<td class="left">
					<input id="PHONE" name="PHONE" type="text" class="text padding5 width20per" value="" title="연락처를 입력하세요" />
			<span class="colorgray paddingleft5">연락이 가능하신 전화번호를 적어주세요</span>
				</td>
			</tr>
			<tr>
				<th><label for="INTRO">공연 프로그램 소개</label> <span class="pilsu">*</span></th>
				<td class="left">
					<textarea id="INTRO" name="INTRO"></textarea>
				</td>
			</tr>
			<tr>
				<th><label for="EQUIPMENT">사용장비 리스트</label> <span class="pilsu">*</span></th>
				<td class="left">
					<textarea id="EQUIPMENT" name="EQUIPMENT"></textarea>
				</td>
			</tr>
			<tr>
				<th><label for="FILE">자료 업로드</label></th>
				<td class="left">
					<input id="FILE" type="file" name="FILE" />
				</td>
			</tr>
		</tbody>
	</table>
	</div>

	<br /><br />
	

	<!-- 하단 버튼 -->
	<div class="btn">
		<c:if test="${contentInfo.RESULT == '4'}">
			<input type="submit" class="s_submit" onclick="fn_qna()" value="신청내역수정">
			<input type="submit" class="s_submit" onclick="fn_cancel()" value="신청내역취소">
		</c:if>		
		<input type="submit" class="s_submit"  value="신청">
		<span class="btn_s" ><a href="#" onclick="javascript:history.go(-1);" />목록</a></span>
	</div>
	</form>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/cmm/jquery.validate.min.js' />"></script>
<script>
//validation 체크
$("#frm").validate({
    onsubmit: true,
    onfocusout: false,
       rules: {
    	   TEAM_NM: "required",
    	   PROG_NM: "required",
    	   PLACE: "required",
    	   DATE: "required",
    	   TIME: "required",
    	   HEAD_NM: "required",
    	   PHONE: "required",
    	   //INTRO: "required",
    	   //EQUIPMENT: "required",
       },
       messages: {
    	   TEAM_NM: "단체명을 입력해주세요",
    	   PROG_NM	: "프로그램명을 입력해 주세요.",
    	   PLACE: "장소를 선택해 주세요",
    	   DATE: "날짜를 선택해 주세요",
    	   TIME: "시간을 입력해 주세요.",
    	   HEAD_NM: "대표자를 입력해 주세요.",
    	   PHONE:"휴대폰을 입력해 주세요",
    	   //INTRO: "공연 소개를 입력해 주세요.",
    	   //EQUIPMENT: "사용장비를 입력해 주세요.",
       }
});
function fn_qna(){
	var bgn = $("#datepickerBgn").val();
	var end = $("#datepickerEnd").val();
	if(bgn==null || end==null || bgn>end?1:0){
		alert("날짜를 확인해 주세요");
		return false;
	}
	if($( "#frm" ).valid()){
		if (confirm("신청 하시겠습니까??") == true){    //확인
 			return true;
		}else{
			return false;
		}
	}
}

var disabledDates = [/* "2020-02-25","2020-02-26","2020-02-28" */];

$(function(){
    var dateBgn;
    var dateEnd;
    
    var minimumDate = new Date(new Date().setMonth(new Date().getMonth()));
    var maximumDate = new Date(new Date().setMonth(new Date().getMonth()+3));
    
    var datepickerBgn = $("#datepickerBgn").datepicker({
        changeMonth:true,
        changeYear:true,
        language: 'ko',
        yearRange:"2019:2021",
        minDate:minimumDate,
        maxDate:maximumDate,
        showOn:"both",
        buttonImage:"/images/egovframework/com/cmm/icon/bu_icon_carlendar.gif",
        buttonImageOnly:true,
        dateFormat: 'yy-mm-dd',
        showOtherMonths: true,
        selectOtherMonths: true,
        showMonthAfterYear: true,
        dayNamesMin: ['일','월', '화', '수', '목', '금', '토'],
        monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
        monthNames: ['년 1월','년 2월','년 3월','년 4월','년 5월','년 6월','년 7월','년 8월','년 9월','년 10월','년 11월','년 12월'],
        nextText: '다음 달',
        prevText: '이전 달',
        //cell 그리기 (15일이 주말이면 월요일날/예약날 막기)
        onRenderCell: function(date, cellType) {
        	//
			for(var i=0;i<disabledDates.length;i++){
    	        if (dateToString(date)==disabledDates[i]) {
    	            return {disabled: true}
    	        }
    		}
        	var disabledDays = [0, 6];
        	 if (cellType == 'day') {
                 var day = date.getDay(),
                     isDisabled = disabledDays.indexOf(day) != -1;

                 return {
                     disabled: isDisabled
                 }
             }
        },
        onSelect:function(formattedDate,date,inst){
        	dateBgn=date;
        	if(dateBgn==null || dateEnd==null ||dateBgn == '' || dateEnd == '') {
        		return; 
        	}
    		if( dateBgn > dateEnd ) {
    			alert('시작날짜가 끝날짜 보다 큽니다. 다시 선택해주세요');
    			inst.el.value='';
    			return;
    		}
        }
    });
    
    
    var datepickerEnd = $("#datepickerEnd").datepicker({
        changeMonth:true,
        changeYear:true,
        language: 'ko',
        yearRange:"2019:2021",
        minDate:minimumDate,
        maxDate:maximumDate,
        showOn:"both",
        buttonImage:"/images/egovframework/com/cmm/icon/bu_icon_carlendar.gif",
        buttonImageOnly:true,
        dateFormat: 'yy-mm-dd',
        showOtherMonths: true,
        selectOtherMonths: true,
        showMonthAfterYear: true,
        dayNamesMin: ['일','월', '화', '수', '목', '금', '토'],
        monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
        monthNames: ['년 1월','년 2월','년 3월','년 4월','년 5월','년 6월','년 7월','년 8월','년 9월','년 10월','년 11월','년 12월'],
        nextText: '다음 달',
        prevText: '이전 달',
        onRenderCell: function(date, cellType) {
			for(var i=0;i<disabledDates.length;i++){
    			
    	        if (dateToString(date)==disabledDates[i]) {
    	            return {disabled: true}
    	        }
    		}
        	
        	var disabledDays = [0, 6];
        	 if (cellType == 'day') {
                 var day = date.getDay(),
                     isDisabled = disabledDays.indexOf(day) != -1;

                 return {
                     disabled: isDisabled
                 }
             }
        },
        onSelect:function(formattedDate,date,inst){
        	dateEnd=date;
        	if(dateBgn==null || dateEnd==null ||dateBgn == '' || dateEnd == '') {
        		return; 
        	}
    		if( dateBgn > dateEnd ) {
    			alert('시작날짜가 끝날짜 보다 큽니다. 다시 선택해주세요');
    			return;
    		}
        }
    });
});
</script>