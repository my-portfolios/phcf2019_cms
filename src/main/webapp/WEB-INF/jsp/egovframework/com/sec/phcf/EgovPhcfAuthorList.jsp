<%
 /**
  * @Class Name : EgovPhcfAuthor.java
  * @Description : EgovPhcfAuthorList List 화면
  * @Modification Information
  * @
  * @ 수정일         수정자               수정내용
  * @ ----------    ---------    ---------------------------
  * @ 2020. 1. 15    윤병훈            최초생성
  *
  *  @author 대외사업부
  *  @since 2020.01.15
  *  @version 1.0
  *  @see
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
<c:set var="pageTitle"><spring:message code="comCopSecDrm.title"/></c:set>
<!DOCTYPE html>
<html>
<head>
<title>${pageTitle} <spring:message code="title.list" /></title><!-- 부서권한관리 목록 -->
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/com.css' />">

<script type="text/javascript" src="<c:url value="/validator.do"/>"></script>
<validator:javascript formName="userManageVO" staticJavascript="false" xhtml="true" cdata="false"/>
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/jqueryui.css' />">
<script src="<c:url value='/js/egovframework/com/cmm/jquery.js' />"></script>
<script src="<c:url value='/js/egovframework/com/cmm/jqueryui.js' />"></script>

<script type="text/javaScript" language="javascript" defer="defer">

function fncCheckAll() {
    var checkField = document.listForm.delYn;
    if(document.listForm.checkAll.checked) {
        if(checkField) {
            if(checkField.length > 1) {
                for(var i=0; i < checkField.length; i++) {
                    checkField[i].checked = true;
                }
            } else {
                checkField.checked = true;
            }
        }
    } else {
        if(checkField) {
            if(checkField.length > 1) {
                for(var j=0; j < checkField.length; j++) {
                    checkField[j].checked = false;
                }
            } else {
                checkField.checked = false;
            }
        }
    }
}

function fncSelectphcfAuthorList(pageNo) {
	if(document.listForm.deptCode.value == '') {
		alert("<spring:message code="comCopSecDrm.validate.deptSelect" />"); //부서를 선택하세요.
		return;
	}

    document.listForm.searchCondition.value = "1";
    document.listForm.pageIndex.value = pageNo;
    document.listForm.action = "<c:url value='/sec/phcf/'/>";
    document.listForm.submit();
}

function fncAddphcfAuthorInsert() {

    if(!fncManageChecked()) return;

    if(confirm("<spring:message code="common.regist.msg" />")){	//등록하겠습니까?
        document.listForm.action = "<c:url value='/phcf/drm/'/>";
        document.listForm.submit();
    }
}

function fncphcfAuthorDeleteList() {

    if(!fncManageChecked()) return;

    if(confirm("<spring:message code="common.delete.msg" />")){	//삭제하시겠습니까?
        document.listForm.action = "<c:url value='/sec/drm/EgovphcfAuthorDelete.do'/>";
        document.listForm.submit();
    }
}

function linkPage(pageNo){
    document.listForm.pageIndex.value = pageNo;
    document.listForm.action = "<c:url value='/sec/phcf/EgovPhcfAuthorList.do'/>";
    document.listForm.submit();
}


/*
function fncSelectphcfAuthorPop() {

    var url = "<c:url value='/sec/drm/EgovDeptSearchView.do'/>";
    var varParam = new Object();
    var openParam = "dialogWidth:500px;dialogHeight:485px;scroll:no;status:no;center:yes;resizable:yes;";

    var retVal = window.showModalDialog(url, varParam, openParam);
    if(retVal) {
        document.listForm.deptCode.value = retVal.substring(0, retVal.indexOf("|"));
        document.listForm.deptNm.value = retVal.substring(retVal.indexOf("|")+1, retVal.length);
    }
}
*/
function fncSelectphcfAuthorPop() {


    var url = "<c:url value='/sec/drm/EgovDeptSearchList.do'/>";
    var openParam = "dialogWidth:500px;dialogHeight:485px;scroll:no;status:no;center:yes;resizable:yes;";
    /*
    var retVal = window.showModalDialog(url, varParam, openParam);
    if(retVal) {
        document.listForm.deptCode.value = retVal.substring(0, retVal.indexOf("|"));
        document.listForm.deptNm.value = retVal.substring(retVal.indexOf("|")+1, retVal.length);
    }
    */

    window.open(url,"<spring:message code="comCopSecDrm.list.searchDept" />",'width=500,height=485,scrollbars=no,resizable=no,status=no,center:yes'); //부서검색

}

function press() {

    if (event.keyCode==13) {
    	fncSelectphcfAuthorList('1');
    }
}

function phcfAuthorClick(seq){
	console.log(seq);
}

</script>

</head>

<body>
<!-- javascript warning tag  -->
<noscript class="noScriptTitle"><spring:message code="common.noScriptTitle.msg" /></noscript>
<form:form name="listForm" action="${pageContext.request.contextPath}/sec/phcf/EgovPhcfAuthorList.do" method="post">
<div class="board">
	<h1>문화재단권한관리</h1><!-- 부서권한관리 목록 -->
	<!-- 검색영역 -->
	<div class="search_box" title="<spring:message code="common.searchCondition.msg" />">
		<ul>
			<li><div style="line-height:4px;">&nbsp;</div></li><!-- 부서권한관리 -->
			<!-- 검색키워드 및 조회버튼 -->
			<li>
				<input name="authNm" type="text" value="<c:out value='${authManageVO.authNm}'/>" size="15" title="<spring:message code="comCopSecDrm.list.deptNm" />" onkeypress="press();" readonly="readonly" /><!-- 부서명 -->
				<input type="submit" class="s_btn" value="<spring:message code="button.inquire" />" title="<spring:message code="button.inquire" /> <spring:message code="input.button" />" /><!-- 조회 -->
			</li>
		</ul>
	</div>

	<!-- 목록영역 -->
	<table class="board_list" summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
	<caption>${pageTitle} <spring:message code="title.list" /></caption>
	<colgroup>
		<col style="width: 3%;">
		<col style="width: 5%;">
		<col style="width: 10%;">
		<col style="width: %;">
		<col style="width: %;">
		<col style="width: 10%;">
		<col style="width: 5%;">
		<col style="width: 10%;">
	</colgroup>
	<thead>
	<tr>
		<th><input type="checkbox" name="checkAll" class="check2" onclick="javascript:fncCheckAll()" title="<spring:message code="input.selectAll.title" />"></th><!-- 번호 -->
		<th class="board_th_link">페이지</th><!--사용자 ID -->
		<th>권한이름</th><!--사용자 명 -->
		<th>조직이름</th><!-- 권한 -->
		<th>회원그룹이름</th><!-- 등록 여부 -->
		<th>우선순위</th>
		<th>사용유무</th>
		<th>등록시점</th>
	</tr>
	</thead>
	<tbody class="ov">
	<c:if test="${fn:length(authorManageList) == 0}">
	<tr>
		<td colspan="5"><spring:message code="common.nodata.msg" /></td>
	</tr>
	</c:if>
	<c:forEach var="phcfAuthor" items="${authorManageList}" varStatus="status">
	<tr onclick="javascript:phcfAuthorClick('${phcfAuthor.seq}');" style="cursor:pointer">
		<td><input type="checkbox" name="delYn" class="check2" title="선택"><input type="hidden" name="checkId" value="<c:out value="${phcfAuthor.seq}"/>" /></td>
		<td><c:out value="${phcfAuthor.page}"/></td>
		<td><c:out value="${phcfAuthor.authNm}"/></td>
		<td><c:out value="${phcfAuthor.orgnztNm}"/></td>
		<td><c:out value="${phcfAuthor.groupId}"/></td>
		<td><c:out value="${phcfAuthor.authPriority}"/></td>
		<td><c:out value="${phcfAuthor.useYn}"/></td>
		<td><c:out value="${phcfAuthor.insDt}"/></td>
	</tr>
	</c:forEach>
	</tbody>
	</table>
	
	<c:if test="${!empty authManageVO.pageIndex }">
		<!-- paging navigation -->
		<div class="pagination">
			<ul><ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="linkPage"/></ul>
		</div>
	</c:if>

</div><!-- end div board -->

<input type="hidden" name="seq"/>
<input type="hidden" name="orgnztId"/>
<input type="hidden" name="orgnztNm"/>
<input type="hidden" name="groupId"/>
<input type="hidden" name="pageIndex" value="<c:out value='${authManageVO.pageIndex}'/>"/>
<input type="hidden" name="searchCondition"/>
</form:form>



</body>
</html>
