<%
 /**
  * @Class Name : EgovArticleList.jsp
  * @Description : EgovArticleList 화면
  * @Modification Information
  * @
  * @  수정일             수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2009.02.01   박정규              최초 생성
  *   2016.06.13   김연호              표준프레임워크 v3.6 개선
  *   2018.06.15   신용호              페이징 처리 오류 개선
  *  @author 공통서비스팀
  *  @since 2009.02.01
  *  @version 1.0
  *  @see
  *
  */

%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>문화재단</title>

<link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'/>
<link href='http://fonts.googleapis.com/css?family=Titillium+Web:400,600,700' rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="<c:url value='/css/egovframework/phcf/main/style.css' />">
<link rel="stylesheet" href="<c:url value='/css/egovframework/phcf/main/menu.css' />">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/egovframework/phcf/slick/slick.css' />"/>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/egovframework/phcf/slick/slick-theme.css' />"/>
<link rel="stylesheet" href="<c:url value='/css/egovframework/phcf/main/reset.css' />"> <!-- CSS reset -->
<style type="text/css">

</style>

<script src="https://code.jquery.com/jquery-2.2.0.min.js" type="text/javascript"></script>
<script src="<c:url value='/js/egovframework/phcf/slick/slick.js' />" type="text/javascript" charset="utf-8"></script>
<script src="<c:url value='/js/egovframework/phcf/main/modernizr.js' />"></script> <!-- Modernizr -->
<script>
/* $(function(){
	var boardUseAt = '${boardMasterVO.useAt}';
	if(boardUseAt == 'N') {
		alert('사용하지 않는 게시판입니다.');
		history.back();
	}
}); */
</script>
</head>
<body>
<c:if test="${loginVO != null}">
	${loginVO.name }<spring:message code="comCmm.unitContent.2"/> <a href="${pageContext.request.contextPath }/uat/uia/actionLogout.do"><spring:message code="comCmm.unitContent.3"/></a>
</c:if>
<c:if test="${loginVO == null }">
	<jsp:forward page="/uat/uia/egovLoginUsr.do"/>
</c:if>
	<!--헤더시작-->
	<header style="position:fixed; width: 100%; top: 0;">
		<c:import url="${pageContext.request.contextPath}/EgovTop.do" />
	</header>
	<script src="<c:url value='/js/egovframework/phcf/main/main.js'/>"></script> <!-- Resource jQuery -->
	<!--헤더끝-->
	
	<div id="main" class="scroll-container">
		
		<c:choose>
		<c:when test="${contentYN == 'Y'}">
			<c:out value="${content}" escapeXml="false"/>
		</c:when>
		<c:when test="${contentYN != 'Y'}">
			<c:import url="${jspPath}" />
		</c:when>
		<c:otherwise>
			페이지에 문제가 있습니다.
		</c:otherwise>
		</c:choose>
		
	</div>
	<div class="bottom">
			<c:import url="${pageContext.request.contextPath}/EgovBottom.do" />
	</div>

</body>
</html>