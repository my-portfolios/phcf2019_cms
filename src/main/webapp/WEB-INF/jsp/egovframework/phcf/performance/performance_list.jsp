<%
 /**
  * @Class Name : EgovMberManage.jsp
  -> performance_list.jsp by 김경민
  * @Description : 사용자관리(조회,삭제) JSP
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2009.03.02    조재영          최초 생성
  *   2016.06.13    장동한          표준프레임워크 v3.6 개선
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
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="pageTitle">공연조회</c:set>
<!DOCTYPE html>
<html>
<head>
<title>${pageTitle} <spring:message code="title.list" /></title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/com.css' />">
<script type="text/javaScript" language="javascript" defer="defer">
function fnCheckAll() {
    var checkField = document.listForm.checkField;
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
//사용하지 않음.
function fnDeleteUser() {
    var checkField = document.listForm.checkField;
    var id = document.listForm.checkId;
    var checkedIds = "";
    var checkedCount = 0;
    if(checkField) {
        if(checkField.length > 1) {
            for(var i=0; i < checkField.length; i++) {
                if(checkField[i].checked) {
                    checkedIds += ((checkedCount==0? "" : ",") + id[i].value);
                    checkedCount++;
                }
            }
        } else {
            if(checkField.checked) {
                checkedIds = id.value;
            }
        }
    }
    if(checkedIds.length > 0) {
    	//alert(checkedIds);
        if(confirm("<spring:message code="common.delete.msg" />")){
        	document.listForm.checkedIdForDel.value=checkedIds;
            document.listForm.action = "<c:url value='/uss/umt/EgovMberDelete.do'/>";
            document.listForm.submit();
        }
    }
}
function fnSelectArticle(id) {
	document.listForm.selectedId.value = id;
	array = id.split(":");
	if(array[0] == "") {
	} else {
	    bbsId = array[0];
	    nttId = array[1];    
	} 
	document.listForm.bbsId.value = bbsId;
	document.listForm.selectedId.value = nttId;
    document.listForm.action = "<c:url value='/performance/writeMail.do'/>";
    document.listForm.submit();
}

function fnSelectArticleForMessage(id) {
	document.listForm.selectedId.value = id;
	array = id.split(":");
	if(array[0] == "") {
	} else {
	    bbsId = array[0];
	    nttId = array[1];    
	} 
	document.listForm.bbsId.value = bbsId;
	document.listForm.selectedId.value = nttId;
    document.listForm.action = "<c:url value='/performance/writeMessage.do'/>";
    document.listForm.submit();
}
//사용하지 않음.
function fnAddUserView() {
    document.listForm.action = "<c:url value='/uss/umt/EgovMberInsertView.do'/>";
    document.listForm.submit();
}
function fnLinkPage(pageNo){
    document.listForm.pageIndex.value = pageNo;
    document.listForm.action = "<c:url value='/performance/articleInfos.do'/>";
    document.listForm.submit();
}
function fnSearch(){
	document.listForm.pageIndex.value = 1;
	document.listForm.action = "<c:url value='/performance/articleInfos.do'/>";
    document.listForm.submit();
}
<c:if test="${!empty resultMsg}">alert("<spring:message code="${resultMsg}" />");</c:if>
</script>
</head>
<body>
<!-- javascript warning tag  -->
<noscript class="noScriptTitle"><spring:message code="common.noScriptTitle.msg" /></noscript>

<form name="listForm" action="<c:url value='/performance/articleInfos.do'/>" method="post"> 
<div class="board">
	<h1>${pageTitle} <spring:message code="title.list" /></h1>
	
	<!-- 검색영역 -->
	<div class="search_box" title="<spring:message code="common.searchCondition.msg" />">
		<ul>
			<%-- <li><!-- 상태-->
                <select name="sbscrbSttus" id="sbscrbSttus" title="<spring:message code="comUssUmt.userManageSsearch.sbscrbSttusTitle" />">
                    <option value="0" <c:if test="${empty mberVO.sbscrbSttus || mberVO.sbscrbSttus == '0'}">selected="selected"</c:if> ><spring:message code="comUssUmt.userManageSsearch.sbscrbSttusAll" /></option><!-- 상태(전체) -->
                    <option value="A" <c:if test="${mberVO.sbscrbSttus == 'A'}">selected="selected"</c:if> ><spring:message code="comUssUmt.userManageSsearch.sbscrbSttusA" /></option><!-- 가입신청 -->
                    <option value="D" <c:if test="${mberVO.sbscrbSttus == 'D'}">selected="selected"</c:if> ><spring:message code="comUssUmt.userManageSsearch.sbscrbSttusD" /></option><!-- 삭제 -->
                    <option value="P" <c:if test="${mberVO.sbscrbSttus == 'P'}">selected="selected"</c:if> ><spring:message code="comUssUmt.userManageSsearch.sbscrbSttusP" /></option><!-- 승인 -->
                </select>
			</li> --%>
			<li><!-- 조건 -->
                <select name="searchCnd" id="searchCondition" title="<spring:message code="comUssUmt.userManageSsearch.searchConditioTitle" />"><!--  -->
<%--                     <option value="0" <c:if test="${mberVO.searchCondition == '0'}">selected="selected"</c:if> ><spring:message code="comUssUmt.userManageSsearch.searchConditionId" /></option><!-- ID  --> --%>
                    <option value="0" selected ><spring:message code="comCopBbs.articleVO.list.nttSj" /></option><!-- Name -->
                </select>
			</li>
			<!-- 검색키워드 및 조회버튼 -->
			<li>
				<input class="s_input" name="searchWrd" type="text"  size="35" title="<spring:message code="title.search" /> <spring:message code="input.input" />" value='<c:out value="${boardVO.searchWrd}"/>'  maxlength="255" >
				<input type="submit" class="s_btn" onclick="fnSearch()" value="<spring:message code="button.inquire" />" title="<spring:message code="title.inquire" /> <spring:message code="input.button" />" />
<%-- 				<input type="button" class="s_btn" onClick="fnDeleteUser(); return false;" value="<spring:message code="title.delete" />" title="<spring:message code="title.delete" /> <spring:message code="input.button" />" /> --%>
<%-- 				<span class="btn_b"><a href="<c:url value='/uss/umt/EgovMberInsertView.do'/>" onClick="fnAddUserView(); return false;"  title="<spring:message code="button.create" /> <spring:message code="input.button" />"><spring:message code="button.create" /></a></span> --%>
<%-- 				<span class="btn_b"><a href="<c:url value='/uss/umt/exportExcelMberList.do'/>">Excel Download</a></span> --%>
			</li>
		</ul>
	</div>


<table class="board_list" summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">
	<caption>${pageTitle} <spring:message code="title.list" /></caption>
	<colgroup>
<%-- 		<col style="width: 5%;"> --%>
<%-- 		<col style="width: 3%;"> --%>
		
		<col style="width: 10%;">
		<col style="width: ;">
		<col style="width: 10%;">
		<col style="width: 10%;">
<%-- 		<col style="width: 20%;"> --%>
<%-- 		<col style="width: 13%;"> --%>
<%-- 		<col style="width: 10%;"> --%>
		<col style="width: 10%;">
	</colgroup>
	<thead>
	<tr>
		<th><spring:message code="table.num" /></th><!-- 번호 -->
<%-- 		<th><input type="checkbox" name="checkAll" class="check2" onclick="javascript:fncCheckAll()" title="<spring:message code="input.selectAll.title" />"></th><!-- 전체선택 --> --%>
		
		<th class="board_th_link"><spring:message code="comCopBbs.articleVO.list.nttSj" /></th><!--제목 -->
<%-- 		<th><spring:message code="comCopBbs.articleVO.regist.cateName" /></th><!-- 카테고리 이름 --> --%>
<%-- 		<th><spring:message code="comUssUmt.userManageList.email" /></th><!-- 사용자이메일 --> --%>
<%-- 		<th><spring:message code="comUssUmt.userManageList.phone" /></th><!-- 전화번호 --> --%>
		<th><spring:message code="table.regdate" /></th><!-- 등록일 -->
<!-- 		<th>유료 멤버십 상태</th>가입상태 -->
		<th>메일 발송</th>
		<th>문자 발송</th>

	</tr>
	</thead>
	<tbody class="ov">
	<c:if test="${fn:length(resultList) == 0}">
	<tr>
		<td colspan="8"><spring:message code="common.nodata.msg" /></td>
	</tr>
	</c:if>
	<c:forEach var="result" items="${resultList}" varStatus="status">
	<tr>
		<c:set var="nttId">${result.nttId}</c:set>
	    <td>
	    	<fmt:formatNumber value="${nttId}" type="number" groupingUsed="false" var="nttId"/>
	    	<c:out value="${nttId}"/>
	    </td>
<!-- 	    <td> -->
<%-- 	        <input name="checkField" title="checkField <c:out value="${status.count}"/>" type="checkbox"/> --%>
<%-- 	        <input name="checkId" type="hidden" value="<c:out value='${result.userTy}'/>:<c:out value='${result.uniqId}'/>"/> --%>
<!-- 	    </td> -->
<%-- 	    <td><a href="<c:url value='/uss/umt/EgovMberSelectUpdtView.do'/>?selectedId=<c:out value="${result.uniqId}"/>"  onclick="javascript:fnSelectUser('<c:out value="${result.userTy}"/>:<c:out value="${result.uniqId}"/>'); return false;"><c:out value="${result.userId}"/></a></td> --%>
	    <td>
	    <c:out value="${result.nttSj}"/>
	    </td>
<%-- 	    <td><c:out value="${result.nttSj}"/></td> --%>
<%-- 	    <td><c:out value="${result.emailAdres}"/></td> --%>
<%-- 	    <td><c:out value="${result.moblphonNo}"/></td> --%>
	    <td><c:out value="${result.frstRegisterPnttm}"/></td>
<!-- 	    <td> -->
<%-- 	    	<c:forEach var="membershipCodeItems" items="${membershipCode}" varStatus="status"> --%>
<%-- 	    		<c:if test="${result.membershipType == membershipCodeItems.code}"> --%>
<!-- 	    			<span class="btn_comom"> -->
<%-- 	            		<c:out value="${membershipCodeItems.codeNm}"/> --%>
<!-- 	            	</span> -->
<%-- 	            </c:if> --%>
<%-- 	        </c:forEach> --%>
<!-- 	    </td>  -->
		<!-- 메일 발송 -->
		<td><a href="<c:url value='/performance/writeMail.do'/>?selectedId=<c:out value="${result.nttId}"/>" 
		onclick="javascript:fnSelectArticle('<c:out value="${result.bbsId}" />:<c:out value="${result.nttId}"/>'); return false;">메일 발송</a></td>
		
		<!-- 문자 발송 -->
		<td><a href="<c:url value='/performance/writeMessage.do'/>?selectedId=<c:out value="${result.nttId}"/>" 
		onclick="javascript:fnSelectArticleForMessage('<c:out value="${result.bbsId}" />:<c:out value="${result.nttId}"/>'); return false;">문자 발송</a></td>
	</tr>
	</c:forEach>
	</tbody>
	</table>
	
	<!-- paging navigation -->
	<div class="pagination">
		<ul><ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fnLinkPage"/></ul>
	</div>

<input name="bbsId" type="hidden" />
<input name="selectedId" type="hidden" />
<input name="checkedIdForDel" type="hidden" />
<input name="pageIndex" type="hidden" value="<c:out value='${boardVO.pageIndex}'/>"/>
</div>
</form>

</body>
</html>
