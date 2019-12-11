<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="egovframework.com.cmm.service.EgovProperties" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%
 /**
  * @Class Name : EgovCommentList.jsp
  * @Description : 댓글
  * @Modification Information
  * @
  * @  수정일      수정자            수정내용
  * @ -------        --------    ---------------------------
  * @ 2009.06.29   한성곤          최초 생성
  *
  *  @author 공통컴포넌트개발팀 한성곤
  *  @since 2009.06.29
  *  @version 1.0
  *  @see
  *
  */
%>


<c:if test="${type == 'body'}">
<%pageContext.setAttribute("crlf", "\r\n"); %>
<!-- 댓글 내용  -->
	<c:import url="../../../../template/${param.tmplatId}/comment.jsp" />

</c:if>
