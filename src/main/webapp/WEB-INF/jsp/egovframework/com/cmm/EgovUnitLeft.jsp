<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
 
        <li class="menu">
            <a>CMS 관리</a>
            <ul class="hide">
                <li><a href="/uss/umt/dpt/selectDeptManageListView.do" target="_content">부서 관리</a></li>
                <!-- <li><a href="/sec/phcf/EgovPhcfAuthorList.do" target="_content">문화재단권한관리 </a></li> -->
                <li><a href="/sec/phcf/listView.do" target="_content">문화재단권한관리 </a></li>
                <li><a href="/uss/umt/EgovUserManage.do" target="_content">업무 사용자 관리</a></li>
                <!-- <li><a href="/sec/ram/EgovAuthorList.do" target="_content">권한 관리</a></li> -->
                <!-- <li><a href="/sec/gmt/EgovGroupList.do" target="_content">그룹 관리</a></li>
                <li><a href="/sec/rmt/EgovRoleList.do" target="_content">롤 관리</a></li> -->
                <li><a href="/sym/ccm/ccc/SelectCcmCmmnClCodeList.do" target="_content">공통 분류코드 관리</a></li>   
                <li><a href="/sym/ccm/cca/SelectCcmCmmnCodeList.do" target="_content">공통 분류코드 상세</a></li>   
                <li><a href="/sym/ccm/cde/SelectCcmCmmnDetailCodeList.do" target="_content">공통 코드상세 관리</a></li>             
                <li><a href="/cop/bbs/selectBBSMasterInfs.do" target="_content">게시판 관리</a></li>
                <li><a href="/cop/bbs/selectArticleList.do?bbsId=BBSMSTR_000000000002" target="_content">컨텐츠관리</a></li>
            </ul>
        </li>
        
        <li class="menu">
            <a>이미지 관리</a>
            <ul class="hide">
                <li><a href="/uss/ion/msi/selectMainImageList.do" target="_content">메인이미지 관리</a></li>
                <li><a href="/uss/ion/bnr/selectBannerList.do" target="_content">배너 관리</a></li>
            </ul>
        </li>
        
        <li class="menu">
            <a>메뉴 관리</a>
            <ul class="hide">
                <li><a href="/sym/mnu/mpm/EgovMenuManageSelect.do" target="_content">메뉴관리 리스트</a></li>
                <li><a href="/sym/mnu/bmm/selectBkmkMenuManageList.do" target="_content">바로가기 메뉴 관리</a></li>
            </ul>
        </li>
        
        <li class="menu">
            <a>팝업창 관리</a>
            <ul class="hide">
                <li><a href="/uss/ion/pwm/listPopup.do" target="_content">팝업창 리스트</a></li>
            </ul>
        </li>
        
        <li class="menu">
            <a>대관신청관리</a>
            <ul class="hide">
                <li><a href="/venueReservation/selectReservationManageList.do" target="_content">접수날짜 관리</a></li>
                <li><a href="/venueReservation/selectReservationCalendar.do" target="_content">대관신청 캘린더</a></li>
                <li><a href="/venueReservation/selectReservationList.do" target="_content">대관신청 리스트</a></li>
            </ul>
        </li>
        
         <li class="menu">
            <a>아트투어신청 관리</a>
            <ul class="hide">
                <li><a href="" target="_content">신청자 리스트</a></li>
            </ul>
        </li>
        
        <li class="menu">
            <a>버스킹 신청 관리</a>
            <ul class="hide">
                <li><a href="#" target="_content">무대 신청자 리스트</a></li>
                <li><a href="#" target="_content">단체 접수 관리</a></li>
            </ul>
        </li>
        
        <li class="menu">
            <a>회원정보 관리</a>
            <ul class="hide">
                <li><a href="/uss/umt/EgovMberManage.do" target="_content">회원정보 리스트</a></li>
                <li><a href="#" target="_content">휴먼회원 리스트</a></li>
                <li><a href="#" target="_content">휴먼회원 전환 관리</a></li>
            </ul>
        </li>
        
        <li class="menu">
            <a>유료멤버쉽 신청관리</a>
            <ul class="hide">
                <li><a href="/premiumMember/selectMembershipRegList.do" target="_content">유료회원 리스트</a></li>
            </ul>
        </li>
        
        <li class="menu">
            <a>후원 신청관리</a>
            <ul class="hide">
                <li><a href="/cms/support/listView.do" target="_content">후원 신청 리스트</a></li>
            </ul>
        </li>
        
        <li class="menu">
            <a>홈페이지 통계</a>
            <ul class="hide">
                <li><a href="/cms/statistic/phcfStatusReport.do" target="_content">게시물 통계</a></li>
                <!-- <li><a href="/sts/cst/selectConectStats.do" target="_content">접속 통계</a></li> -->
                <li><a href="/sts/ust/selectUserStats.do" target="_content">사용자 통계</a></li>
                <li><a href="/sym/log/lgm/SelectSysLogList.do" target="_content">로그 관리</a></li>
                <li><a href="/sym/log/wlg/SelectWebLogList.do" target="_content">웹로그 관리</a></li>
                <li><a href="/sym/log/ulg/SelectUserLogList.do" target="_content">사용자 로그 관리</a></li>
                <li><a href="/sym/log/ulg/SelectUserLogList.do" target="_content">접속 로그 관리</a></li>
            </ul>
        </li>
        
        <li class="menu">
            <a>업무관리</a>
            <ul class="hide">
                <li><a href="/cop/smt/djm/addDeptJob.do" target="_content">부서업무 등록</a></li>
                <li><a href="/cop/smt/djm/selectDeptJobList.do" target="_content">부서업무 목록</a></li>
                <li><a href="/cop/smt/sdm/EgovDeptSchdulManageList.do" target="_content">부서일정관리</a></li>
            </ul>
        </li>
        
        <p style="padding: 20px 0px; text-align: center;">
            <a href="http://hubizict.com/new/bbs/board.php?bo_table=mn05_2" target="_blank"><img src="<c:url value='/images/egovframework/com/cmm/main/cms_banner01.png' />" alt="휴비즈아이씨티 작업요청게시판으로 이동합니다." /></a>
        </p>
        
    </ul>

<br /><br />


<!-- 기존 메뉴 -->
<ul class="lnb_title" style="display: none;">
<li class="">

	<c:forEach var="result" items="${resultList}" varStatus="status">
	
		
		<c:if test="${isMai == 'false' && result.gid == '0'}">								
			<a><strong class="left_title_strong"><strong class="top_title_strong"><spring:message code="comCmm.mai.title"/></strong></strong></a><!-- 포털(예제) 메인화면 -->	
			<c:set var="isMai" value="true"/>
		</c:if>
		
		<c:if test="${isUat == 'false' && result.gid == '10'}">
			<a><strong class="left_title_strong"><strong class="top_title_strong"><spring:message code="comCmm.uat.title"/></strong></strong></a><!-- 사용자디렉토리/통합인증 -->		
			<c:set var="isUat" value="true"/>
		</c:if>
		
		<c:if test="${isSec == 'false' && result.gid == '20'}">
			<a><strong class="left_title_strong"><strong class="top_title_strong"><spring:message code="comCmm.sec.title"/></strong></strong></a><!-- 보안 -->
			<c:set var="isSec" value="true"/>
		</c:if>
		
		<c:if test="${isSts == 'false' && result.gid == '30'}">
			<a><strong class="left_title_strong"><strong class="top_title_strong"><spring:message code="comCmm.sts.title"/></strong></strong></a><!-- 통계/리포팅 -->
			<c:set var="isSts" value="true"/>
		</c:if>
		<c:if test="${isCop == 'false' && result.gid == '40'}">
			<a><strong class="left_title_strong"><strong class="top_title_strong"><spring:message code="comCmm.cop.title"/></strong></strong></a><!-- 협업 -->
			<c:set var="isCop" value="true"/>
		</c:if>
		<c:if test="${isUss == 'false' && result.gid == '50'}">
			<a><strong class="left_title_strong"><strong class="top_title_strong"><spring:message code="comCmm.uss.title"/></strong></strong></a><!-- 사용자지원 -->
			<c:set var="isUss" value="true"/>
		</c:if>
		<c:if test="${isSym == 'false' && result.gid == '60'}">
			<a><strong class="left_title_strong"><strong class="top_title_strong"><spring:message code="comCmm.sym.title"/></strong></strong></a><!-- 시스템관리 -->
			<c:set var="isSym" value="true"/>
		</c:if>
		<c:if test="${isSsi == 'false' && result.gid == '70'}">
			<a><strong class="left_title_strong"><strong class="top_title_strong"><spring:message code="comCmm.ssi.title"/></strong></strong></a><!-- 시스템/서비스연계  -->
			<c:set var="isSsi" value="true"/>
		</c:if>
		<c:if test="${isDam == 'false' && result.gid == '80'}">
			<a><strong class="left_title_strong"><strong class="top_title_strong"><spring:message code="comCmm.dam.title"/></strong></strong></a><!-- 디지털 자산 관리 -->
			<c:set var="isDam" value="true"/>
		</c:if>
		<c:if test="${isCom == 'false' && result.gid == '90'}">
			<a><strong class="left_title_strong"><strong class="top_title_strong"><spring:message code="comCmm.com.title"/></strong></strong></a> <!-- 요소기술 -->
			<c:set var="isCom" value="true"/>
		</c:if>
		<c:if test="${isExt == 'false' && result.gid == '100'}">
			<a><strong class="left_title_strong"><strong class="top_title_strong"><spring:message code="comCmm.ext.title"/></strong></strong></a><!-- 외부 추가 컴포넌트 -->
			<c:set var="isExt" value="true"/>
		</c:if>
		
		<ul class="hide">
		<c:set var="componentMsgKey">comCmm.left.${result.order}</c:set>				
		<li class="2depth"><a href="${pageContext.request.contextPath}<c:out value="${result.listUrl}"/>" target="_content" class="link"> <c:out value="${result.order}"/>. <spring:message code="${componentMsgKey}"/><!-- <c:out value="${result.name}"/> --></a>
		</li>
		</ul>
	
	</c:forEach>

</li>	
</ul>
<!-- 기존 메뉴 -->
