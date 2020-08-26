<%--
/**
 * @Class Name  : EgovMainImageRegist.jsp
 * @Description : EgovMainImageRegist.jsp
 * @Modification Information
 * @
 * @  수정일         수정자          수정내용
 * @ -------    --------    ---------------------------
 * @ 2009.02.01    lee.m.j          최초 생성
 * @ 2018.08.30    이정은               공통컴포넌트 3.8 개선
 *
 *  @author lee.m.j
 *  @since 2009.03.11
 *  @version 1.0
 *  @see
 *
 *  Copyright (C) 2009 by MOPAS  All right reserved.
 */
 --%>

<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><spring:message code="uss.ion.msi.mainImageRegist.mainImageRegist" /></title><!-- 메인화면이미지 등록 -->
<link href="<c:url value="/css/egovframework/com/com.css"/>" rel="stylesheet" type="text/css">
<link href="<c:url value="/css/egovframework/com/button.css"/>" rel="stylesheet" type="text/css">
<link href="<c:url value="/css/egovframework/com/cmm/jqueryui.css"/>" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<c:url value='/js/egovframework/com/cmm/fms/EgovMultiFile.js'/>"></script>
<script type="text/javascript" src="<c:url value="/validator.do"/>"></script>
<script src="<c:url value='/js/egovframework/com/cmm/jquery.js' />"></script>
<script src="<c:url value='/js/egovframework/com/cmm/jqueryui.js' />"></script>

<link rel="stylesheet" href="/css/egovframework/phcf/datepicker.css" /> 		
<script src="/js/egovframework/phcf/datepicker.js"></script>
<script src="/js/egovframework/phcf/datepicker.ko.js"></script>
<validator:javascript formName="mainImage" staticJavascript="false" xhtml="true" cdata="false"/>
<script type="text/javaScript" language="javascript">


$(function(){
	$("#displayPage").on("change", function(){
		if($(this).val()!="문화공간"){
			$(".mainTitle").css("display","");
			if($(this).val() == "축제"){
				$(".dday").css("display","");
			}
			else {
				$(".dday").css("display","none");
			}
		}
		else {
			$(".mainTitle").css("display","none");
		}
	});
	
	$("#dday").datepicker({
		language: 'ko',
		position: 'top right',
		clearButton: true,
		showOtherMonths: false,
		moveToOtherMonthsOnSelect: false,
		selectOtherMonths: false,
		navTitles: {
			days: 'yyyy 년 MM'
		}
	});
});

function fncSelectMainImageList() {
    var varFrom = document.getElementById("mainImage");
    varFrom.action = "<c:url value='/uss/ion/msi/selectMainImageList.do'/>";
    varFrom.submit();
}

function fncMainImageInsert() {

    var varFrom = document.getElementById("mainImage");
    varFrom.action = "<c:url value='/uss/ion/msi/addMainImage.do'/>";

    if(confirm("<spring:message code="uss.ion.msi.mainImageRegist.saveImage" />")){/* 저장 하시겠습니까? */
        if(!validateMainImage(varFrom)){
            return;
        }
        else if($("#popupImage").val() == ''){
        	alert("이미지를 선택하세요!");
        	return;
        }
        else{
            if(varFrom.mainImage.value != '') {
                varFrom.submit();
            } else {
                alert("<spring:message code="uss.ion.msi.mainImageRegist.ImageReq" />");/* 이미지는 필수 입력값입니다. */
                return;
            }
        }
    }
}

function fncOnChangeImage() {
    var varFrom = document.getElementById("mainImage");
    varFrom.mainImage.value = varFrom.file_1.value;
}

</script>
</head>

<body>
<noscript class="noScriptTitle"><spring:message code="common.noScriptTitle.msg" /></noscript><!-- 자바스크립트를 지원하지 않는 브라우저에서는 일부 기능을 사용하실 수 없습니다. -->

<div class="wTableFrm">
	<!-- 타이틀 -->
	<h2><spring:message code="uss.ion.msi.mainImageRegist.mainImageRegist" /></h2><!-- 메인화면이미지 등록 -->

	<!-- 등록폼 -->
<form:form commandName="mainImage" method="post" action="${pageContext.request.contextPath}/uss/ion/msi/addMainImage.do' />" enctype="multipart/form-data">
<input type="hidden" name="posblAtchFileNumber" value="1" >
<input type="hidden" name="image" >
	<table class="wTable">
		<colgroup>
			<col style="width:16%" />
			<col style="" />
		</colgroup>
		<%-- <tr>
			<th><spring:message code="uss.ion.msi.mainImageRegist.mainImageId" /> <span class="pilsu">*</span></th><!-- 이미지ID -->
			<td class="left">
			    <input name="imageId" id="imageId" title="<spring:message code="uss.ion.msi.mainImageRegist.mainImageId" />" type="text" value="<c:out value='${mainImage.imageId}'/>" class="readOnlyClass" readonly="readonly" style="width:188px" />
			</td>
		</tr> --%>
	 	<tr>
			<th>적용 사이트 <span class="pilsu">*</span></th><!-- 팝업창표시페이지 -->
			<td class="left">
				<select name="displayPage" id="displayPage">
					<option value="메인">메인</option>
					<option value="문화공간">문화공간</option>
					<option value="축제">축제</option>
				</select>			
			</td>
		</tr>
		<tr class="mainTitle">
			<th>주 제목</th>
			<td class="left">
			    <input name="mainSubject" title="주 제목" type="text"  maxLength="100" /> 큰 텍스트 (미입력시 노출안함)
			</td>
		</tr>
		<tr class="mainTitle">
			<th>부 제목</th>
			<td class="left">
			    <input name="subSubject" title="부 제목" type="text"  maxLength="100" /> 상단 작은 텍스트 (미입력시 노출안함)
			</td>
		</tr>
	 	<tr>
			<th><spring:message code="uss.ion.msi.mainImageRegist.mainImageNm" /> <span class="pilsu">*</span></th><!-- 이미지명 -->
			<td class="left">
			    <input name="imageNm" id="imageNm" title="<spring:message code="uss.ion.msi.mainImageRegist.mainImageNm" />" type="text" value="<c:out value='${mainImage.imageNm}'/>" maxLength="10" style="width:188px" />&nbsp;<form:errors path="imageNm" />
			</td>
		</tr> 
		<tr>
			<th><spring:message code="uss.ion.msi.mainImageRegist.mainImage" /> <span class="pilsu">*</span></th><!-- 이미지 -->
			<td class="left">
				<input type="button" onclick="window.open('/common/imageCropper.do?ratio=2.80','mainImage','width=1000,height=640,resizable=no');" value="선택"/>
	            <input type="hidden" id="popupImage" name="popupImage" readOnly/><br /><br />
	            <img src="/images/egovframework/phcf/admin/cms_banner02.png" alt=""><br />	
	            (메인 이미지 사이즈 : 1920px * 937px, 문화공간  이미지 사이즈 : 1920px * 685px, 축제  이미지 사이즈 : 1920px * 685px)
			</td>
			    <%-- <div class="egov_file_box" style="display:inline-block">
				<label for="egovfile_0" id="file_label"><spring:message code="title.attachedFileSelect"/></label> <!-- 파일선택 -->
				<input type="file" name="file_1" id="egovfile_0" title="<spring:message code="uss.ion.msi.mainImageRegist.mainImage" />" onchange="fncOnChangeImage();" /> 
				</div><input name="mainImage" id="mainImage" type="text" title="<spring:message code="uss.ion.msi.mainImageRegist.mainImage"/>" value="<c:out value="${mainImage.image}"/>" maxLength="30" readonly="readonly" style="width:525px" /> --%>
		</tr>
		<tr>
			<th>적용 이미지 미리보기</th>
			<td class="left"><img id="popupImageView" style="width:80%;" class="max-img"/></td>
		</tr>
		
		<tr class="mainTitle">
			<th>연결될 페이지</th>
			<td class="left">
			    주소: <input name="connectPage" title="연결될 페이지" type="text" maxLength="100" />
			    페이지 이동을 하지 않으려면 #을 입력하십시오.
			    
			    <br/><br/>
				표시 될 창 : 
				<select name="target">
					<option value="_blank" >새 창으로 열기</option>
					<option value="_self" >현재 창으로 열기</option>
				</select>
			</td>
		</tr>
		<tr class="mainTitle dday" <c:if test="${mainImage.displayPage != '축제'}">style="display:none;"</c:if>>
			<th>D-Day 설정</th>
			<td class="left">
			    <input name="dday" id="dday" title="D-Day" type="text" maxLength="100" readOnly/>			    
			</td>
		</tr>
		<tr>
			<th><spring:message code="ussIonBnr.bannerRegist.sortOrdr"/> <span class="pilsu">*</span></th><!-- 정렬순서 -->
			<td class="left">
				<input id="sortOrdr" type="text" name="sortOrdr" title="<spring:message code="ussIonBnr.bannerRegist.sortOrdr"/>" value="<c:out value='${banner.sortOrdr}'/>" maxLength="5" style="width:68px" />
				<form:errors path="sortOrdr" />
			</td>
		</tr>
		<%-- <tr>
			<th><spring:message code="uss.ion.msi.mainImageRegist.mainImageDc" /></th><!-- 이미지설명 -->
			<td class="left">
			    <input name="imageDc" id="imageDc" title="<spring:message code="uss.ion.msi.mainImageRegist.mainImageDc" />" type="text" value="<c:out value='${mainImage.imageDc}'/>" maxLength="100" />
			</td>
		</tr> --%>
		<tr>
			<th><spring:message code="uss.ion.msi.mainImageRegist.mainImageReflctAt" /> <span class="pilsu">*</span></th><!-- 반영여부 -->
			<td class="left">
			    <select name="reflctAt" id="reflctAt" title="<spring:message code="uss.ion.msi.mainImageRegist.mainImageReflctAt" />">
					<option value="Y" <c:if test="${mainImage.reflctAt == 'Y'}">selected</c:if> >Y</option>
					<option value="N" <c:if test="${mainImage.reflctAt == 'N'}">selected</c:if> >N</option>
				</select>
				Y : 선택시 노출, N : 선택시 미노출
			</td>
		</tr>
		<tr style="display:none;">
			<th><spring:message code="uss.ion.msi.mainImageRegist.mainImageregDate" /></th><!-- 등록일시 -->
			<td class="left">
			    <input name="regDate" id="regDate" type="text" value="<c:out value="${loginScrinImage.regDate}"/>" maxLength="20" size="20" readonly="readonly" title="<spring:message code="uss.ion.msi.mainImageRegist.mainImageregDate" />" style="width:188px" />
			</td>
		</tr>
	</table>

	<!-- 하단 버튼 -->
	<div class="btn">
		<input class="s_submit" type="submit" value='<spring:message code="button.save" />' onclick="fncMainImageInsert(); return false;" />
		<span class="btn_s"><a href="<c:url value='/uss/ion/msi/selectMainImageList.do'/>?pageIndex=<c:out value='${mainImageVO.pageIndex}'/>&amp;searchKeyword=<c:out value="${mainImageVO.searchKeyword}"/>&amp;searchCondition=1" onclick="fncSelectMainImageList(); return false;"><spring:message code="button.list" /></a></span>
	</div>
	<div style="clear:both;"></div>
</div>

<!-- 검색조건 유지 -->
<input type="hidden" name="searchCondition" value="<c:out value='${mainImageVO.searchCondition}'/>">
<input type="hidden" name="searchKeyword" value="<c:out value='${mainImageVO.searchKeyword}'/>">
<input type="hidden" name="pageIndex" value="<c:out value='${mainImageVO.pageIndex}'/>">
<!-- 검색조건 유지 -->
</form:form>
</body>
</html>

