<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>eGovFrame 공통 컴포넌트</title>
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR&display=swap" rel="stylesheet">
<link href="<c:url value='/css/egovframework/com/cmm/main.css' />" rel="stylesheet" type="text/css">
<script src="http://code.jquery.com/jquery-1.8.2.min.js"></script>
<style>
    .menu li {cursor:pointer;}
    .menu .hide{display:none;}
</style>

<script>
    $(document).ready(function(){
        // menu 클래스 바로 하위에 있는 a 태그를 클릭했을때
        $(".menu>a").click(function(){
            var submenu = $(this).next("ul"); 
            // submenu 가 화면상에 보일때는 위로 보드랍게 접고 아니면 아래로 보드랍게 펼치기
            if( submenu.is(":visible") ){
                submenu.slideUp();
            }else{
                submenu.slideDown();
            }
        });
    });
</script>


<div id="lnb">
<c:set var="isMai" value="false"/>
<c:set var="isUat" value="false"/>
<c:set var="isSec" value="false"/>
<c:set var="isSts" value="false"/>
<c:set var="isCop" value="false"/>
<c:set var="isUss" value="false"/>
<c:set var="isSym" value="false"/>
<c:set var="isSsi" value="false"/>
<c:set var="isDam" value="false"/>
<c:set var="isCom" value="false"/>
<c:set var="isExt" value="false"/>

    <ul class="lnb_title">
		<li class="menu noicon" >
            <a href="${pageContext.request.contextPath}/EgovContent.do" target="_content" class="noicon" >Dashboard</a>
        </li>
		<c:forEach var="menu1" items="${resultList}" varStatus="status">
			<c:if test="${menu1.depth2 == 0 && menu1.depth3 == 0}">
				<li class="menu">
				<a>${menu1.menuNm}</a>
					<ul class="hide">
					<c:forEach var="menu2" items="${resultList}" varStatus="status">
					<c:if test="${menu1.depth1 == menu2.depth1 && menu2.depth2 > 0}">
						<li>
							<a href="${menu2.link }" target="${menu2.target }">${menu2.menuNm }</a>							
						</li>
					</c:if>
					</c:forEach>
					</ul>
				</li>
			</c:if>
		</c:forEach>
        
        <p style="padding: 20px 0px; text-align: center;">
            <a href="http://hubizict.com/new/bbs/board.php?bo_table=mn05_2" target="_blank"><img src="<c:url value='/images/egovframework/com/cmm/main/cms_banner01.png' />" alt="휴비즈아이씨티 작업요청게시판으로 이동합니다." /></a>
        </p>
        
    </ul>

