<%
 /**
  * @Class Name : EgovArticleReply.jsp
  * @Description : EgovArticleReply 화면
  * @Modification Information
  * @
  * @  수정일             수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2009.02.01   박정규              최초 생성
  *   2016.06.13   김연호              표준프레임워크 v3.6 개선
  *
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

<!--헤더시작-->
	<header style="position:fixed; width: 100%; top: 0;">
		<c:import url="${pageContext.request.contextPath}/EgovTop.do" />
	</header>
	<!-- 모바일 메뉴 시작-->
		<c:import url="../../../../pieces/mobile_menu.jsp" />
	<!-- 모바일 메뉴 끝-->
	<script src="<c:url value='/js/egovframework/phcf/main/main.js'/>"></script> <!-- Resource jQuery -->
	<!--헤더끝-->
	
	<div id="main" class="scroll-container cd-main-content">
		
		<!--list 메인-->
		<c:choose>
		<c:when test="${boardMasterVO.useAt == 'Y'}">
			<c:import url="../../../../template/${boardMasterVO.tmplatId}/reply.jsp" />
		</c:when>
		<c:otherwise>
			사용하지 않는 게시판입니다.
		</c:otherwise>
		</c:choose>
		
	</div>
	<div class="bottom">
			<c:import url="${pageContext.request.contextPath}/EgovBottom.do" />
	</div>

</body>
</html>