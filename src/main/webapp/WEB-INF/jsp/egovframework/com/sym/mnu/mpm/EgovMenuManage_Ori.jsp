<!DOCTYPE html>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
 /**
  * @Class Name : EgovMenuManage.jsp
  * @Description : 메뉴관리 조회 화면
  * @Modification Information
  * @
  * @  수정일             수정자          수정내용
  * @ -------     --------  ---------------------------
  * @ 2009.03.10   이용            최초 생성
  	  2011.07.27     서준식          메뉴 삭제 자바스크립트 오류 수정
  	  2018.08.09     신용호          삭제시 목록이 1개인경우 예외처리 수정
  *
  *  @author 공통서비스 개발팀 이용
  *  @since 2009.03.10
  *  @version 1.0
  *  @see
  *
  */
  /* Image Path 설정 */
  String imagePath_icon   = "/images/egovframework/com/sym/mnu/mpm/icon";
  String imagePath_button = "/images/egovframework/com/sym/mnu/mpm/button";
%>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" >
<title><spring:message code="comSymMnuMpm.menuManage.title"/></title><!-- 메뉴관리리스트 -->
<link href="<c:url value="/css/egovframework/com/com.css"/>" rel="stylesheet" type="text/css">
<link href="<c:url value="/css/egovframework/com/button.css"/>" rel="stylesheet" type="text/css">
<script
  src="https://code.jquery.com/jquery-3.4.1.min.js"
  integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
  crossorigin="anonymous"></script>
<script language="javascript1.2" type="text/javaScript">
<!--
$(function(){
	refreshSelectBox();
});

/* ********************************************************
 * 모두선택 처리 함수
 ******************************************************** */
function fCheckAll() {
    var checkField = document.menuManageForm.checkField;
    if(document.menuManageForm.checkAll.checked) {
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
/* ********************************************************
 * 멀티삭제 처리 함수
 ******************************************************** */
function fDeleteMenuList() {
    var checkField = document.menuManageForm.checkField;
    var menuNo = document.menuManageForm.checkMenuNo;
    var checkMenuNos = "";
    var checkedCount = 0;
    if(checkField) {

    	if(typeof(checkField.length) != "undefined") {
            for(var i=0; i < checkField.length; i++) {
                if(checkField[i].checked) {
                    checkMenuNos += ((checkedCount==0? "" : ",") + menuNo[i].value);
                    checkedCount++;
                }
            }
        } else {
            if(checkField.checked) {
                checkMenuNos = menuNo.value;
                checkedCount = 1;
            }
        }
    }
    if(checkedCount ==0){
		alert("선택된 메뉴가 없습니다.");
		return false;
    }
	
    document.menuManageForm.checkedMenuNoForDel.value=checkMenuNos;
    document.menuManageForm.action = "<c:url value='/sym/mnu/mpm/EgovMenuManageListDelete.do'/>";
    document.menuManageForm.submit();
}

/* ********************************************************
 * 페이징 처리 함수
 ******************************************************** */
function linkPage(pageNo){
//	document.menuManageForm.searchKeyword.value =
	document.menuManageForm.pageIndex.value = pageNo;
	document.menuManageForm.action = "<c:url value='/sym/mnu/mpm/EgovMenuManageSelect.do'/>";
   	document.menuManageForm.submit();
}

/* ********************************************************
 * 조회 처리 함수
 ******************************************************** */
function selectMenuManageList() {
	document.menuManageForm.pageIndex.value = 1;
	document.menuManageForm.action = "<c:url value='/sym/mnu/mpm/EgovMenuManageSelect.do'/>";
	document.menuManageForm.submit();
}

/* ********************************************************
 * 입력 화면 호출 함수
 ******************************************************** */
function insertMenuManage() {
   	document.menuManageForm.action = "<c:url value='/sym/mnu/mpm/EgovMenuRegistInsert.do'/>";
   	document.menuManageForm.submit();
}

/* ********************************************************
 * 일괄처리 화면호출 함수
 ******************************************************** */
/* function bndeInsertMenuManage() {
	   	document.menuManageForm.action = "<c:url value='/sym/mnu/mpm/EgovMenuRegistInsert.do'/>";
	   	document.menuManageForm.submit();
	}
 */
function bndeInsertMenuManage() {
   	document.menuManageForm.action = "<c:url value='/sym/mnu/mpm/EgovMenuBndeRegist.do'/>";
   	document.menuManageForm.submit();
}
/* ********************************************************
 * 상세조회처리 함수
 ******************************************************** */
function selectUpdtMenuManageDetail(menuNo) {
	document.menuManageForm.req_menuNo.value = menuNo;
   	document.menuManageForm.action = "<c:url value='/sym/mnu/mpm/EgovMenuManageListDetailSelect.do'/>";
   	document.menuManageForm.submit();
}
/* ********************************************************
 * 최초조회 함수
 ******************************************************** */
function fMenuManageSelect(){
    document.menuManageForm.action = "<c:url value='/sym/mnu/mpm/EgovMenuManageSelect.do'/>";
    document.menuManageForm.submit();
}
<c:if test="${!empty resultMsg}">alert("${resultMsg}");</c:if>

function refreshSelectBox(){
	$.ajax({
		url: "/sym/mnu/mpm/AjaxSelectAllContentsDidntMapped.do",
		type: "post",
		dataType: "json",
		success: function(data){
			$(".selectContentsDidntMapped").empty();
			$(".selectContentsDidntMapped").append("<option value='0'>맵핑하지 않음</option>");
			for(var i=0; i< data.result.length; i++){
				$(".selectContentsDidntMapped").append("<option value='"+data.result[i].nttId+"'>"+data.result[i].nttJs+"</option>");
			}
		},
		error: function(request, status, error) {
			console.log("error");
		}
	});
}

function menuBttClick(id){
	$.ajax({
		url: "/sym/mnu/mpm/AjaxEgovContentsMenuMapping.do",
		type: "post",
		dataType: "json",
		data: {menuNo:id, nttId:$("#menuSelectNo"+id+" option:selected").val(), nttSj:$("#menuSelectNo"+id+" option:selected").text()},
		success: function(data){
			console.log(data);
			refreshSelectBox();
			if(data.param.nttId != 0) $('#menuNttSj'+id).html(data.param.nttSj);
			else $('#menuNttSj'+id).html('');
		},
		error: function(request, status, error) {
			console.log("error");
		}
	});
}

function contentpageYn(id){
	console.log($('#contentpageYn'+id).val());
	$.ajax({
		url: "/sym/mnu/mpm/AjaxEgovContentsMenuYN.do",
		type: "post",
		dataType: "json",
		data: {menuNo:id, contentpageYn:$('#contentpageYn'+id).val()},
		success: function(data){
			console.log(data);
			if(data.param.contentpageYn=='N') {
				$('#menuDivNo'+data.param.menuNo).hide();
				$('#menuNttSj'+id).html('');
			}
			else $('#menuDivNo'+data.param.menuNo).show();
		},
		error: function(request, status, error) {
			console.log("error");
		}
	});
}

-->
</script>
</head>
<body>

<noscript class="noScriptTitle"><spring:message code="common.noScriptTitle.msg" /></noscript><!-- 자바스크립트를 지원하지 않는 브라우저에서는 일부 기능을 사용하실 수 없습니다. -->

<div class="board">
	<h1><spring:message code="comSymMnuMpm.menuManage.pageTop.title"/></h1><!-- 메뉴관리리스트 -->

	<form name="menuManageForm" action ="<c:url value='/sym/mnu/mpm/EgovMenuManageSelect.do'/>" method="post">
	<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>
	<input name="checkedMenuNoForDel" type="hidden" />
	<input name="req_menuNo" type="hidden"  />

	<div class="search_box" title="<spring:message code="common.searchCondition.msg" />"><!-- 이 레이아웃은 하단 정보를 대한 검색 정보로 구성되어 있습니다. -->
		<ul>
			<li>
				<form:select path="searchVO.pageNm" title="${title} ${inputTxt}" cssClass="txt">
					<form:option value="" label="전체" />
					<form:options items="${pageList}"/>
				</form:select>
				<label for=""><spring:message code="comSymMnuMpm.menuManage.menuNm"/> : </label><!-- 메뉴명 -->
				<input class="s_input2 vat" name="searchKeyword" type="text" value="${searchVO.searchKeyword }" size="25" title="<spring:message code="title.searchCondition"/>" /><!-- 검색조건 -->
				
				<input class="s_btn" type="submit" value='<spring:message code="button.inquire" />' title='<spring:message code="button.inquire" />' onclick="selectMenuManageList(); return false;" /><!-- 조회 -->
<%-- 				<span class="btn_b"><a href="<c:url value='/sym/mnu/mpm/EgovMenuRegistInsert.do'/>" onclick="bndeInsertMenuManage(); return false;" title="<spring:message code="button.bulkUpload" />"><spring:message code="button.bulkUpload" /></a></span><!-- 일괄등록 --> --%>
<%-- 				<span class="btn_b"><a href="<c:url value='/sym/mnu/mpm/EgovMenuRegistInsert.do'/>" onclick="insertMenuManage(); return false;" title='<spring:message code="button.create" />'><spring:message code="button.create" /></a></span><!-- 등록 --> --%>
<%-- 				<span class="btn_b"><a href="#" onclick="fDeleteMenuList(); return false;" title='<spring:message code="button.delete" />'><spring:message code="button.delete" /></a></span><!-- 삭제 --> --%>
			</li>
		</ul>
	</div>
	
	</form>

	<table class="board_list">
		<caption></caption>
		<colgroup>
			<%-- <col style="width:30px" /> --%>
			<col style="width:100px" />
			<col style="width:120px" />
			<col style="width:200px" />
			<col style="width:167px" />
			<col style="width:50px" />
			<col style="width:167px" />
			<col style="width:100px" />
		</colgroup>
		<thead>
			<tr>
			   <!-- <th scope="col"><input type="checkbox" name="checkAll" class="check2" onclick="fCheckAll();" title="전체선택"/></th>전체선택 -->
			   <th scope="col">사이트</th><!-- 메뉴ID -->
			   <th scope="col">메뉴명</th><!-- 메뉴한글명 -->
			   <th scope="col">주소</th><!-- 프로그램파일명 -->
			   <th scope="col">구조</th><!-- 메뉴설명 -->
			   <th scope="col">컨텐츠여부</th><!-- 메뉴설명 -->
			   <th scope="col">현재맵핑</th><!-- 메뉴설명 -->
			   <th scope="col">컨텐츠맵핑</th><!-- 상위메뉴ID -->
			</tr>
		</thead>
		<tbody>
			<c:forEach var="result" items="${list_menumanage}" varStatus="status">
				<c:set var="menuNo">${result.pageNm}_${result.depth1}-${result.depth2}-${result.depth3}</c:set>
			  <tr>
			    <td><c:out value="${result.pageNm}"/></td>
			    <td style="cursor:hand;">
			       <%-- <span class="link"><a href="<c:url value='/sym/mnu/mpm/EgovMenuManageListDetailSelect.do?req_menuNo='/>${menuNo}" onclick="selectUpdtMenuManageDetail('<c:out value="${menuNo}"/>'); return false;"><c:out value="${result.menuNm}"/></a></span> --%>
			       <c:out value="${result.menuNm}"/>
			    </td>
			    <td><c:out value="${result.link}"/></td>
			    <td><c:out value="${result.depth1}"/><c:out value="${result.depth2}"/><c:out value="${result.depth3}"/></td>
			    <td>
			    	<select id="contentpageYn${menuNo}" name="contentpageYn" onchange="javascript:contentpageYn('${menuNo}');">
			    		<option <c:if test="${result.contentpageYn=='Y'}">selected</c:if>>Y</option>
			    		<option <c:if test="${result.contentpageYn!='Y'}">selected</c:if>>N</option>
			    	</select>
			    </td>
			    <td id="menuNttSj${menuNo}"><c:out value="${result.nttSj}"/></td>
			    <td>
			    	<div id="menuDivNo${menuNo}" <c:if test="${result.contentpageYn!='Y'}">style="display:none;"</c:if>>
				    	<select class="selectContentsDidntMapped" id="menuSelectNo${menuNo}">
						</select>
						<input type="button" id="menuBttNo${menuNo}" onclick="javascript:(menuBttClick('${menuNo}'));" value="설정"/>
					</div>
			    </td>
			  </tr>
			 </c:forEach>
		</tbody>
	</table>

	<!-- paging navigation -->
	<div class="pagination">
		<ul>
			<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="linkPage"/>
		</ul>
	</div>
</div>

</body>
</html>

