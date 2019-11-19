<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>

<form name="frm" id="frm" action="/cms/support/status_edit.do" method="post">
<input type="hidden" name="sp_id" value="${supportDetail.sp_id}">
<input type="hidden" name="user_tp_cd" value="${supportDetail.user_tp_cd }">
<table class="tablestyle_basic">
    <caption class="dpnone">후원정보 상세보기</caption>
    <tbody>
    <tr>
        <th class="table_id">순번</th>
        <td colspan="3"><c:out value="${supportDetail.sp_id}" /></td>
    </tr>
    <tr>
    	<th>후원방식</th>
        <td><c:out value="${supportDetail.sp_mh_tp}" /></td>
		<th>후원종류</th>
        <td><c:out value="${supportDetail.sp_tp_arr}" /></td>    
    </tr>
    <tr>
    	<th>회원 아이디</th>
        <td><c:out value="${supportDetail.user_id}" /></td>
		<th>회원 구분</th>
        <td><c:out value="${supportDetail.user_tp}" /></td>    
    </tr>
    <tr>
    	<th>회원 이름</th>
        <td><c:out value="${supportDetail.user_nm}" /></td>
		<th>회원 성별</th>
		<td>
			<input type="radio" id="user_mf_m" name="user_mf" value="M" <c:if test="${supportDetail.user_mf eq 'M'}">checked</c:if>><label for="user_mf_m">남자</label>
			<input type="radio" id="user_mf_f" name="user_mf" value="F" <c:if test="${supportDetail.user_mf eq 'F'}">checked</c:if>><label for="user_mf_f">여자</label>
		</td>
    </tr>
    <tr>
    	<th>회원 생년월일</th>
        <td><input type="text" name="user_birth" style="width: 200px;" value="<c:out value='${supportDetail.user_birth}' />" /></td>
		<th>회원 주민번호</th>
        <td><input type="text" name="user_number" style="width: 200px;" value="<c:out value='${supportDetail.user_number}' />" /></td>    
    </tr>
    <tr>
		<th>회원 연락처</th>
        <td><input type="text" name="user_phone1" style="width: 50px;" value="<c:out value='${supportDetail.user_phone1}' />" />
        	 - <input type="text" name="user_phone2" style="width: 50px;" value="<c:out value='${supportDetail.user_phone2}' />" />
        	 - <input type="text" name="user_phone3" style="width: 50px;" value="<c:out value='${supportDetail.user_phone3}' />" /> 
       	</td>    
       	 <th>회원 이메일</th>
        <td><input type="text" name="user_email1" style="width: 100px;" value="<c:out value='${supportDetail.user_email1}' />" />
        	 @ <input type="text" name="user_email2" style="width: 100px;" value="<c:out value='${supportDetail.user_email2}' />" />
       	</td>
    </tr>
    <tr>
		<th>회원 주소</th>
        <td colspan="3"><input type="text" name="user_post" style="width: 100px;" value="<c:out value='${supportDetail.user_post}' />" />
        	 <br> <input type="text" name="user_add" style="width: 200px;" value="<c:out value='${supportDetail.user_add}' />" />
        	 <input type="text" name="user_add_detail" style="width: 150px;" value="<c:out value='${supportDetail.user_add_detail}' />" /> 
       	</td>    
    </tr>
    <tr>
    	<th>수신동의 항목</th>
    	<td colspan="3">
    		<c:out value="${supportDetail.user_revinfo_tp_arr }" />
    	</td>
    </tr>
    <tr>
    	<th>기업/단체 이름</th>
    	<td><input type="text" name="user_comp_nm" value="<c:out value='${supportDetail.user_comp_nm }'/>" <c:if test="${supportDetail.user_tp eq '개인'}">readonly</c:if> /><c:if test="${supportDetail.user_tp eq '개인'}"> *개인회원 입니다.</c:if></td>
    	<th>기업/단체 번호</th>
    	<td><input type="text" name="user_comp_number" value="<c:out value='${supportDetail.user_comp_number }'/>" <c:if test="${supportDetail.user_tp eq '개인'}">readonly</c:if> /><c:if test="${supportDetail.user_tp eq '개인'}"> *개인회원 입니다.</c:if></td>
    </tr>
    <tr>
    	<th>후원 종목</th>
    	<td><c:out value="${supportDetail.sc_tp }"/></td>
    	<th>영수증신청여부</th>
    	<td><c:out value="${supportDetail.user_tax_yn }"/></td>
    </tr>
    <tr>
    	<th>후원 금액(원)</th>
    	<td><c:out value="${supportDetail.sc_price }"/></td>
    	<th>결제 방식</th>
    	<td><c:out value="${supportDetail.sc_price_tp }"/></td>
    </tr>
    <tr>
    	<th>후원 메시지</th>
    	<td><input type="text" name="support_msg" style="width: -webkit-fill-available;" value="<c:out value='${supportDetail.support_msg }'/>" /></td>
    	<th>관리자 메시지</th>
    	<td><input type="text" name="admin_msg" style="width: -webkit-fill-available;" value="<c:out value='${supportDetail.admin_msg }'/>" /></td>
    </tr>
    <tr>
    	<th>주문번호</th>
    	<td><c:out value="${supportDetail.order_num }"/></td>
    	<th>결제모드</th>
    	<td><c:out value="${supportDetail.browser }"/></td>
    </tr>
    <tr>
    	<th>정보생성일</th>
    	<td colspan="3"><c:out value="${supportDetail.create_dt }"/></td>
    </tr>
    </tbody>
</table> 

<!--button-->
<div class="boxstyle1 floatright margintop10">
	<input type="button" name="list_btn" class="button btn_middle btn_white" value="목록" />
    <input type="button" name="modify_btn" class="button btn_middle btn_blue" value="수정" />
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="button" name="delete_btn" class="button btn_middle btn_gray" value="삭제" />
</div>
<!--button_end-->
</form>	

<script type="text/javascript">
$(document).ready(function() {

	$('input[name="modify_btn"]').click(function() {
		if(!confirm("[수정] 하시겠습니까?")) { return; }

		$.post('/cms/support/update_detailInfo.do', $('#frm').serialize(), function(result) {

			if(!result.state) { alert('후원 정보 수정 중 이상 발생.'); return; }

			alert('수정이 완료 되었습니다.');
			location.href = '/cms/support/list.do';
			
		}).fail(function() {
			alert('후원 정보 수정 중 오류 발생.');
			return;
		});
	});
	
	// 목록 보기 버튼
	$('input[name="list_btn"]').click(function() {
		location.href = '/cms/support/list.do';
	});

	// 삭제 버튼 클릭
	$('[name="delete_btn"]').click(function() {
		if(!confirm("[삭제] 하시겠습니까?")) { return; }

		$.post('/cms/support/delete_detailInfo.do'
				, {sp_id: '${supportDetail.sp_id}'
					, user_tp: '${supportDetail.user_tp_cd }'
					, user_comp_nm: '${supportDetail.user_comp_nm }'}
				, function(result) {

			if(!result.state) { alert('후원 정보 삭제 중 이상 발생.'); return; }

			alert('삭제가 완료 되었습니다.');
			location.href = '/cms/support/list.do';
			
		}).fail(function() {
			alert('후원 정보 삭제 중 오류 발생.');
			return;
		});
	});
	
});
</script>