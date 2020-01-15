<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR&display=swap" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/main.css' />">
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.1/jquery-ui.min.js"></script>
<title>(재)포항문화재단 웹사이트 관리시스템 (CMS)</title>
</head>

<div id="header">
	<div class="header_box"> 
	 	<li>
	 	<h1>
		<a href="<c:url value='/EgovContent.do' />" target="_content">
		<img src="<c:url value='/images/egovframework/com/cmm/main/top_logo.png' />" alt="eGovframe">
		</a>
		</h1>
		<div class="top_logo_title"><spring:message code="comCmm.top.title"/></div>
		</li>
		<li class="header_quickmenu">
			<div><span>포항문화재단</span> <span>문화공간</span> <span>축제</span></div>
			<div><span class="quickmenu_down">메뉴얼 다운로드</span></div>
			<div>2020/01/02 12:00</div>
			<div>  
			<img src="<c:url value='/images/egovframework/com/cmm/main/ic_icon01.png' />">(공연전시팀) 
			<c:if test="${loginVO != null}">
				${loginVO.name }<spring:message code="comCmm.unitContent.2"/> <a href="${pageContext.request.contextPath }/uat/uia/actionLogout.do" target="_parent">
				<img src="<c:url value='/images/egovframework/com/cmm/main/ic_logout.png' />" title="로그아웃">
				<%-- <spring:message code="comCmm.unitContent.3"/> --%>
				</a>
			</c:if>
			<c:if test="${loginVO == null }">
				<jsp:forward page="/uat/uia/egovLoginUsr.do"/>
			</c:if>
			</div>
		</li>
	</div>	
</div>