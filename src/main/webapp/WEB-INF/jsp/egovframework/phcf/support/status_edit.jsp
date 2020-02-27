<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="<c:url value='/css/egovframework/com/com.css' />" rel="stylesheet" type="text/css">
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/jqueryui.css' />">
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/Chart.min.css' />">
<script src="<c:url value='/js/egovframework/com/cmm/jquery.js' />"></script>
<script src="<c:url value='/js/egovframework/com/cmm/jqueryui.js' />"></script>
<form name="frm" id="frm" action="/cms/support/status_edit.do" method="post">

<div class="board">
	<h1>CMS전송현황 상세보기</h1>

	<table id="about_table" class="wTable">
	    <caption class="dpnone">CMS전송현황 상세보기</caption>
	    <tbody>
	    <tr>
	        <th class="table_id">순번</th>
	        <td><c:out value="${statusDetailInfo.cms_id}" /></td>
	        <th>등록회원 이름</th>
	        <td><c:out value="${statusDetailInfo.user_name}" /></td>
	    </tr>
	    <tr>
			<th>등록회원 연락처</th>
	        <td><c:out value="${statusDetailInfo.user_phone}" /></td>    
	        <th>등록회원 이메일</th>
	        <td><c:out value="${statusDetailInfo.user_email}" /></td>
	    </tr>
	    <tr>
	        <th class="table_id">등록회원ID</th>
	        <td><c:out value="${statusDetailInfo.user_id}" /></td>
	        <th class="table_id">후원종류</th>
	        <td><c:out value="${statusDetailInfo.acc_tp}" /></td>
	    </tr>
	    <tr>
	        <th>CMS이체동의서 전송여부</th>
	        <td colspan="3"><c:out value="${statusDetailInfo.agree_send_yn}" /></td>
	    </tr>
	    <tr>
			<th>회원정보 등록여부</th>
	        <td><c:out value="${statusDetailInfo.user_send_yn}" /></td>
	        <th>회원정보 등록파일명</th>
	        <td><c:out value="${statusDetailInfo.user_send_file}" /></td>    
	    </tr>
	    <tr>
			<th>출금정보 등록여부</th>
	        <td><c:out value="${statusDetailInfo.pay_send_yn}" /></td>
	        <th>출금 승인번호</th>
	        <td><c:out value="${statusDetailInfo.agree_num}" /></td>    
	    </tr>
	    <tr>
			<th>CMS이체 은행</th>
	        <td><c:out value="${statusDetailInfo.bank_code}" /></td>
	        <th>CMS이체 계좌주명</th>
	        <td><c:out value="${statusDetailInfo.bank_acc_user_nm}" /></td>
	    </tr>
	    <tr>
			<th>CMS이체 계좌번호</th>
	        <td colspan="3"><c:out value="${statusDetailInfo.bank_acc_num}" /></td>
	    </tr>
	    <tr>
			<th>카드후원 카드번호</th>
	        <td><c:out value="${statusDetailInfo.card_num}" /></td>
	        <th>카드후원 카드 년/월</th>
	        <td><c:out value="${statusDetailInfo.card_year_month}" /></td>
	    </tr>
	    <tr>
			<th>정기후원 금액</th>
	        <td><c:out value="${statusDetailInfo.sc_price}" /></td>
	        <th>정기후원 이체일</th>
	        <td><c:out value="${statusDetailInfo.sc_req_dd}" /></td>
	    </tr>
	    <tr>
			<th>정기후원 출금 횟수</th>
	        <td><c:out value="${statusDetailInfo.sc_cnt}" /></td>
	        <th>데이터 사용유무</th>
	        <td>
	        	<select name="use_yn">
	        		<option value="Y" <c:if test="${statusDetailInfo.use_yn eq 'Y'}">selected</c:if>>Y</option>
	        		<option value="N" <c:if test="${statusDetailInfo.use_yn eq 'N'}">selected</c:if>>N</option>
	        	</select>
	        
	        </td>
	    </tr>
	    <tr>
			<th>데이터 생성일</th>
	        <td><c:out value="${statusDetailInfo.create_dt}" /></td>
	        <th>데이터 등록자</th>
	        <td><c:out value="${statusDetailInfo.ins_id}" /></td>
	    </tr>
	    </tbody>
	</table> 
</div>	
<!--button-->
<div class="btn">
	<input type="button" name="list_btn" class="btn_s" value="목록" />
    <input type="button" name="modify_btn" class="btn_s" value="수정" />
    <input type="button" name="del_btn" class="" value="삭제" />
</div>
<!--button_end-->
</form>	

<script type="text/javascript">
$(document).ready(function() {

	// 데이터 삭제 처리
	$('[name="del_btn"]').click(function() {
		if(!confirm("데이터를 삭제 하시겠습니까?")) { return; }

		$.post('/cms/support/status_delete.do', {cms_id: '${statusDetailInfo.cms_id}'}, function(result) {
			var jsonStr=JSON.parse(result);
			if(!jsonStr.state) { alert('CMS전송현황 삭제 중 이상 발생.'); return; }
			
			alert('삭제 처리 되었습니다.');
			location.href = '/cms/support/status.do';
			
		}).fail(function() {
			alert('CMS전송현황 삭제 중 오류 발생.');
			return;
		});
	});

	$('input[name="modify_btn"]').click(function() {
		if(!confirm("[수정] 하시겠습니까?")) { return; }

		$.post('/cms/support/status_modify.do', {cms_id: '${statusDetailInfo.cms_id}'
																	, use_yn: $('select[name="use_yn"] option:selected').val()}, function(result) {
			var jsonStr=JSON.parse(result);
			
			if(!jsonStr.state) { alert('CMS전송현황 수정 중 이상 발생.'); return; }

			alert('수정이 완료 되었습니다.');
			location.href = '/cms/support/status.do';
			
		}).fail(function() {
			alert('CMS전송현황 수정 중 오류 발생.');
			return;
		});
	});
	
	// 목록 보기 버튼
	$('input[name="list_btn"]').click(function() {
		location.href = '/cms/support/status.do';
	});
	
});
</script>