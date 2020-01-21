<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isErrorPage="true" import="java.io.*" %>
<c:set var="pageTitle"><spring:message code="comCmmErr.runtimeException.code"/></c:set><!-- 시스템 에러 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title><spring:message code="title.html"/></title>
<link href="<c:url value='/css/egovframework/com/com.css' />" rel="stylesheet" type="text/css" />

<script language="javascript">
function fncGoAfterErrorPage(){
    history.back(-2);
}
</script>
</head>
<body>
<div style="width: 1000px; margin: 50px auto 50px;">
	
	<div style="border: ppx solid #666; padding: 20px;">
		

		<div class="boxType1" style="width: 700px;">
			<div class="box">
				<div class="error">
					<p class="title"><spring:message code="comCmmErr.runtimeException.title" /></p><!-- 알 수 없는 오류가 발생했습니다! -->
					<p class="cont mb20">${pageTitle}<br /></p>
					<span class="btn_style1 blue"><a href="javascript:fncGoAfterErrorPage();"><spring:message code="comCmmErr.button" /><!-- 이전 페이지 --></a></span>
				</div>
			</div>
		</div>
	</div>
</div>
<%=exception %>
<%
  out.println("<pre>");
  StringWriter sw = new StringWriter();
  PrintWriter pw = new PrintWriter(sw);
  exception.printStackTrace(pw);
  out.print(sw.toString());
  sw.close();
  pw.close();
  out.println("</pre>");
%>
</body>
</html>
