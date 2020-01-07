<%
 /**
  * @Class Name : EgovArticleRegist.jsp
  * @Description : EgovArticleRegist 화면
  * @Modification Information
  * @
  * @  수정일             수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2009.02.01   박정규              최초 생성
  *   2016.06.13   김연호              표준프레임워크 v3.6 개선
  *   2018.06.05   신용호              CK Editor V4.9.2 Upgrade
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

	<div id="main" class="board">
		
		<!--list 메인-->
		<c:choose>
		<c:when test="${boardMasterVO.useAt == 'Y'}">
			<c:import url="../../../../template/${boardMasterVO.tmplatId}/write.jsp" />
		</c:when>
		<c:otherwise>
			사용하지 않는 게시판입니다.
		</c:otherwise>
		</c:choose>
		
	</div>