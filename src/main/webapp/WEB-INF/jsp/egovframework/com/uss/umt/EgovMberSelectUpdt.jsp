<%
 /**
  * @Class Name : EgovMberSelectUpdt.jsp
  * @Description : 일반회원상세조회, 수정 JSP
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2009.03.02    조재영          최초 생성
  * @ 2015.06.16	조정국		  password 중복필드 정리
  * @ 2016.06.13    장동한          표준프레임워크 v3.6 개선
  * @ 2017.07.21  장동한 			로그인인증제한 작업
  *
  *  @author 공통서비스 개발팀 조재영
  *  @since 2009.03.02
  *  @version 1.0
  *  @see
  *
  */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<c:set var="pageTitle"><spring:message code="comUssUmt.userManage.title"/></c:set>
<!DOCTYPE html>
<html>
<head>
<title>${pageTitle} <spring:message code="title.update" /></title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<script src="<c:url value='/js/egovframework/com/cmm/jquery.js' />"></script>
<script src="<c:url value='/js/egovframework/com/cmm/jqueryui.js' />"></script>
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/com.css' />">
<script type="text/javascript" src="<c:url value="/validator.do"/>"></script>
<link href="<c:url value="/css/egovframework/com/cmm/jqueryui.css"/>" rel="stylesheet" type="text/css">
<validator:javascript formName="mberManageVO" staticJavascript="false" xhtml="true" cdata="false"/>
<script type="text/javaScript" language="javascript" defer="defer">

function fnListPage(){
    document.mberManageVO.action = "<c:url value='/uss/umt/EgovMberManage.do'/>";
    document.mberManageVO.submit();
}
function fnDeleteMber(checkedIds) {
	if(confirm("<spring:message code="common.delete.msg" />")){
	    document.mberManageVO.checkedIdForDel.value=checkedIds;
	    document.mberManageVO.action = "<c:url value='/uss/umt/EgovMberDelete.do'/>";
	    document.mberManageVO.submit();
	}
}
function fnPasswordMove(){
    document.mberManageVO.action = "<c:url value='/uss/umt/EgovMberPasswordUpdtView.do'/>";
    document.mberManageVO.submit();
}

function fnLockIncorrect(){
	if(confirm("<spring:message code="comUssUmt.common.lockAtConfirm" />")){
	    document.mberManageVO.action = "<c:url value='/uss/umt/EgovMberLockIncorrect.do'/>";
	    document.mberManageVO.selectedId.value=document.mberManageVO.uniqId.value;
	    document.mberManageVO.submit();
	}
}

function fnUpdate(form){
	if(confirm("<spring:message code="common.save.msg" />")){
		if(validateMberManageVO(form)){
			document.mberManageVO.submit();
			return true;
	    }else{
	    	return false;
	    }
	}
}

$(function(){
	
	
	$("#membershipStartDt").datepicker( 
	        {dateFormat:'yy-mm-dd'
	         , showOn: 'button'
	         , buttonImage: '<c:url value='/images/egovframework/com/cmm/icon/bu_icon_carlendar.gif'/>'  
	         , buttonImageOnly: true
	         
	         , showMonthAfterYear: true
	         , showOtherMonths: true
		     , selectOtherMonths: true
				
	         , changeMonth: true // 월선택 select box 표시 (기본은 false)
	         , changeYear: true  // 년선택 selectbox 표시 (기본은 false)
	         , showButtonPanel: true // 하단 today, done  버튼기능 추가 표시 (기본은 false)
	});
	
	$("#membershipExpireDt").datepicker( 
	        {dateFormat:'yy-mm-dd'
	         , showOn: 'button'
	         , buttonImage: '<c:url value='/images/egovframework/com/cmm/icon/bu_icon_carlendar.gif'/>'  
	         , buttonImageOnly: true
	         
	         , showMonthAfterYear: true
	         , showOtherMonths: true
		     , selectOtherMonths: true
				
	         , changeMonth: true // 월선택 select box 표시 (기본은 false)
	         , changeYear: true  // 년선택 selectbox 표시 (기본은 false)
	         , showButtonPanel: true // 하단 today, done  버튼기능 추가 표시 (기본은 false)
	});
	
});

function updateExpireDt(){
	let mType = $("#membershipType");
	
	let mbershipDuration = 2;
	
	if(mType.val() == 'M')
		mbershipDuration = 2;
	else if(mType.val() == 'N')
		mbershipDuration = 0;
	else
		mbershipDuration = 1;
	
	let startDt = new Date($("#membershipStartDt").val());
	let expireDt = startDt;
	
	expireDt.setFullYear(startDt.getFullYear() + mbershipDuration);

	$("#membershipExpireDt").datepicker("setDate", expireDt);
	
}
</script>
</head>
<body>


<!-- content start -->
<form:form commandName="mberManageVO" action="${pageContext.request.contextPath}/uss/umt/EgovMberSelectUpdt.do" name="mberManageVO"  method="post" onSubmit="fnUpdate(document.forms[0]); return false;"> 

<!-- 상세정보 사용자 삭제시 prameter 전달용 input -->
<input name="checkedIdForDel" type="hidden" />
<!-- 검색조건 유지 -->
<input type="hidden" name="searchCondition" value="<c:out value='${userSearchVO.searchCondition}'/>"/>
<input type="hidden" name="searchKeyword" value="<c:out value='${userSearchVO.searchKeyword}'/>"/>
<input type="hidden" name="sbscrbSttus" value="<c:out value='${userSearchVO.sbscrbSttus}'/>"/>
<input type="hidden" name="pageIndex" value="<c:out value='${userSearchVO.pageIndex}'/>"/>
<!-- 우편번호검색 -->
<input type="hidden" name="zip_url" value="<c:url value='/sym/ccm/zip/EgovCcmZipSearchPopup.do'/>" />
<!-- 사용자유형정보 : password 수정화면으로 이동시 타겟 유형정보 확인용, 만약검색조건으로 유형이 포함될경우 혼란을 피하기위해 userTy명칭을 쓰지 않음-->
<input type="hidden" name="userTyForPassword" value="<c:out value='${mberManageVO.userTy}'/>" />
<!-- for validation -->
<input type="hidden" name="password" id="password" value="Test#$123)"/>
<input type="hidden" name="selectedId" id="selectedId" value=""/>  

<div class="wTableFrm">
	<h2>${pageTitle} <spring:message code="title.update" /></h2>
			
	<!-- 수정폼 -->
	<table class="wTable" summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
	<caption>${pageTitle} <spring:message code="title.create" /></caption>
	<colgroup>
		<col style="width: 22%;"><col style="width: ;">
	</colgroup>
	<tbody>
		<!-- 입력/선택 -->
		<c:set var="inputTxt"><spring:message code="input.input" /></c:set>
		<c:set var="inputSelect"><spring:message code="input.cSelect" /></c:set>
		<!-- 일반회원아이디 -->
		<c:set var="title"><spring:message code="comUssUmt.userManageRegist.id"/></c:set>
		<tr>
			<th><label for="mberId">${title}</label> <span class="pilsu">*</span></th>
			<td class="left">
				<form:input path="mberId" id="mberId" title="${title} ${inputTxt}" size="20" readonly="true" maxlength="20" />
                <form:errors path="mberId" cssClass="error" />
                <form:hidden path="uniqId" />
			</td>
		</tr>
		<!-- 일반회원이름 -->
		<c:set var="title"><spring:message code="comUssUmt.userManageRegist.name"/></c:set>
		<tr>
			<th><label for="mberNm">${title}</label> <span class="pilsu">*</span></th>
			<td class="left">
				<form:input path="mberNm" title="${title} ${inputTxt}" size="50" maxlength="60" />
				<div><form:errors path="mberNm" cssClass="error" /></div> 
			</td>
		</tr>
		<!-- 비밀번호힌트 -->
		<c:set var="title"><spring:message code="comUssUmt.userManageRegist.passHit"/></c:set>
		<tr style="display:none;">
			<th><label for="passwordHint">${title}</label> <span class="pilsu">*</span></th>
			<td class="left">
				<%-- <form:select path="passwordHint" id="passwordHint" title="${title} ${inputSelect}">
					<form:option value="" label="--선택하세요--"/>
					<form:options items="${passwordHint_result}" itemValue="code" itemLabel="codeNm"/>
				</form:select> --%>
				<form:input type="text" path="passwordHint" value="P01"/> 
				<div><form:errors path="passwordHint" cssClass="error"/></div>
			</td>
		</tr>
		<!-- 비밀번호정답 -->
		<c:set var="title"><spring:message code="comUssUmt.userManageRegist.passOk"/></c:set>
		<tr style="display:none;">
			<th><label for="passwordCnsr">${title}</label> <span class="pilsu">*</span></th>
			<td class="left">
				<form:input path="passwordCnsr" id="passwordCnsr" title="${title} ${inputTxt}" size="50" maxlength="100" value="X"/>
				<div><form:errors path="passwordCnsr" cssClass="error"/></div>
			</td>
		</tr>
		<!-- 성별구분코드 -->
		<c:set var="title"><spring:message code="comUssUmt.userManageRegist.saxTypeCode"/></c:set>
		<tr style="display:none;">
			<th><label for="sexdstnCode">${title}</label></th>
			<td class="left">
				<%-- <form:select path="sexdstnCode" id="sexdstnCode" title="${title} ${inputSelect}">
					<form:option value="" label="--선택하세요--"/>
					<form:options items="${sexdstnCode_result}" itemValue="code" itemLabel="codeNm"/>
				</form:select> --%>
				<form:input path="sexdstnCode" id="sexdstnCode" value="X"/>
			</td>
		</tr>
		<!-- 전화번호 -->
		<c:set var="title"><spring:message code="comUssUmt.userManageRegist.tel"/></c:set>
		<tr style="display:none;">
			<th><label for="areaNo">${title}</label> <span class="pilsu">*</span></th>
			<td class="left">
                    <form:input path="areaNo" id="areaNo" title="전화번호" cssClass="txaIpUmt" size="5" maxlength="5" style="width:40px;" value="000"/>
                    - <form:input path="middleTelno" id="middleTelno" cssClass="txaIpUmt" size="5" maxlength="5" style="width:40px;" value="000"/>
                    - <form:input path="endTelno" id="endTelno" cssClass="txaIpUmt" size="5" maxlength="5" style="width:40px;" value="0000"/>
                    <div><form:errors path="areaNo" cssClass="error" /></div>
                    <div><form:errors path="middleTelno" cssClass="error" /></div>
                    <div><form:errors path="endTelno" cssClass="error" /></div>
			</td>
		</tr>
		<!-- 팩스번호 -->
		<c:set var="title"><spring:message code="comUssUmt.userManageRegist.fax"/></c:set>
		<tr style="display:none;">
			<th><label for="mberFxnum">${title}</label></th>
			<td class="left">
                    <form:input path="mberFxnum" id="mberFxnum" title="${title} ${inputTxt}" size="20"  maxlength="15" value="000000000"/>
                    <div><form:errors path="mberFxnum" cssClass="error" /></div>
			</td>
		</tr>
		<!-- 헨드폰번호 -->
		<c:set var="title"><spring:message code="comUssUmt.userManageRegist.phone"/></c:set>
		<tr>
			<th><label for="moblphonNo">${title}</label> <span class="pilsu">*</span></th>
			<td class="left">
                    <form:input path="moblphonNo" id="moblphonNo" title="${title} ${inputTxt}" size="20" maxlength="15" />
                    <div><form:errors path="moblphonNo" cssClass="error" /></div>
			</td>
		</tr>
		<!-- 이메일주소 -->
		<c:set var="title"><spring:message code="comUssUmt.userManageRegist.email"/></c:set>
		<tr>
			<th><label for="mberEmailAdres">${title}</label> <span class="pilsu">*</span></th>
			<td class="left">
                    <form:input path="mberEmailAdres" id="mberEmailAdres" title="${title} ${inputTxt}" size="30" maxlength="50" />
                    <div><form:errors path="mberEmailAdres" cssClass="error" /></div>
			</td>
		</tr>
		<!-- 유료멤버십 정보 -->
		<tr id="membershipTypeTr">
			<th>유료멤버십 등급</th>
			<td class="left">
				<form:select path="membershipType" id="membershipType">
					<form:option value="N" label="기본회원(유료멤버십 ×)"/>
<%-- 					<form:option value="B" label="일반회원"/> --%>
<%-- 					<form:option value="P" label="프리미엄회원"/> --%>
<%-- 					<form:option value="M" label="멤버십회원"/> --%>
					<form:options items="${membershipType_result}" itemValue="code" itemLabel="codeNm"/>
				</form:select>
			</td>
		</tr>
		<tr id="membershipTypeTr">
			<th>유료멤버십 가입일</th>
			<td class="left">
				<form:input path="membershipStartDt" id="membershipStartDt" onchange="updateExpireDt();"/>
			</td>
		</tr>
		<tr id="membershipTypeTr">
			<th>유료멤버십 만료일</th>
			<td class="left">
				<form:input path="membershipExpireDt" id="membershipExpireDt"/>
			</td>
		</tr>
		
		
		<!-- 아래는 사용하지 않음  -->
		<!-- 우번번호 -->
		<c:set var="title"><spring:message code="comUssUmt.userManageRegist.post"/></c:set>
		<tr style="display:none;">
			<th><label for="zip">${title}</label> <span class="pilsu">*</span></th>
			<td class="left">
                    <form:input path="zip" id="zip" title="${title} ${inputTxt}" readonly="true" size="70" maxlength="6" style="width:60px;" value="00000"/>
                    <!-- form:hidden path="zip" id="zip" --> 
                    <!-- <button class="btn_s2" onClick="fn_egov_ZipSearch(document.mberManageVO, document.mberManageVO.zip, document.mberManageVO.zip_view, document.mberManageVO.adres);return false;" title="<spring:message code="button.delete" /> <spring:message code="input.button" />">우번번호검색</button>  -->
                    <div><form:errors path="zip" cssClass="error" /></div>
			</td>
		</tr>
		<!-- 주소 -->
		<c:set var="title"><spring:message code="comUssUmt.userManageRegist.addr"/></c:set>
		<tr style="display:none;">
			<th><label for="adres">${title}</label> <span class="pilsu">*</span></th>
			<td class="left">
                    <form:input path="adres" id="adres" title="${title} ${inputTxt}" readonly="true" size="70" maxlength="100" value="X"/>
                    <div><form:errors path="adres" cssClass="error" /></div>
			</td>
		</tr>
		<!-- 상세주소 -->
		<c:set var="title"><spring:message code="comUssUmt.userManageRegist.addrDetail"/></c:set>
		<tr style="display:none;">
			<th><label for="detailAdres">${title}</label> </th>
			<td class="left">
                    <form:input path="detailAdres" id="detailAdres" title="${title} ${inputTxt}"  size="70" maxlength="100" value="X" />
                    <div><form:errors path="detailAdres" cssClass="error" /></div>
			</td>
		</tr>
		<!-- 그룹아이디 -->
		<c:set var="title"><spring:message code="comUssUmt.userManageRegist.groupId"/></c:set>
		<c:set var="inputSelect"><spring:message code="input.select"/></c:set>
		<tr>
			<th><label for="groupId">${title}</label></th>
			<td class="left">
                    <form:select path="groupId" id="groupId" title="${title} ${inputSelect}">
                        <form:option value="" label="${inputSelect}"/>
                        <form:options items="${groupId_result}" itemValue="code" itemLabel="codeNm"/>
                    </form:select>
                    <div><form:errors path="groupId" cssClass="error"/></div>
			</td>
		</tr>
		<!-- 일반회원상태코드 -->
		<c:set var="title"><spring:message code="comUssUmt.userManageRegist.status"/></c:set>
		<tr style="display:none;">
			<th><label for="mberSttus">${title}</label> <span class="pilsu">*</span></th>
			<td class="left">
                    <form:select path="mberSttus" id="mberSttus" title="${title} ${inputSelect}">
                        <form:option value="" label="${inputSelect}"/>
                        <form:options items="${mberSttus_result}" itemValue="code" itemLabel="codeNm"/>
                    </form:select>
                    <div><form:errors path="mberSttus" cssClass="error"/></div>
			</td>
		</tr>
		<!-- 로그인인증제한여부 -->
		<c:set var="title"><spring:message code="comUssUmt.common.lockAt"/></c:set>
		<tr style="display:none;">
			<th><label for="lockAt">${title}</label></th>
			<td class="left">
			<c:if test="${mberManageVO.lockAt eq 'Y'}">예</c:if>
			<c:if test="${mberManageVO.lockAt == null || mberManageVO.lockAt eq '' || mberManageVO.lockAt eq 'N'}">아니오</c:if>
			</td>
		</tr>
	</tbody>
	</table>			


	<!-- 하단 버튼 -->
	<div class="btn">
		<input type="submit" class="s_submit" value="<spring:message code="button.save" />" title="<spring:message code="button.save" /> <spring:message code="input.button" />" />
		<%-- <button class="btn_s2" onClick="fnDeleteMber('<c:out value='${mberManageVO.userTy}'/>:<c:out value='${mberManageVO.uniqId}'/>'); return false;" title="<spring:message code="button.delete" /> <spring:message code="input.button" />"><spring:message code="button.delete" /></button> --%>
		<span class="btn_s"><a href="<c:url value='/uss/umt/EgovMberManage.do' />"  title="<spring:message code="button.list" /> <spring:message code="input.button" />"><spring:message code="button.list" /></a></span>
		<%-- <button class="btn_s2" onClick="fnPasswordMove(); return false;" title="<spring:message code="comUssUmt.userManageModifyBtn.passwordChange" /> <spring:message code="input.button" />"><spring:message code="comUssUmt.userManageModifyBtn.passwordChange" /></button>
		<button class="btn_s2" onClick="fnLockIncorrect(); return false;" title="<spring:message code="comUssUmt.common.lockAtBtn" /> <spring:message code="input.button" />"><spring:message code="comUssUmt.common.lockAtBtn" /></button> --%>
		<button class="btn_s2" onClick="document.mberManageVO.reset(); return false;" title="<spring:message code="button.reset" /> <spring:message code="input.button" />">변경 취소</button>
	</div><div style="clear:both;"></div>
</div>
</form:form>
<!-- content end -->
</body>
</html>
