<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>▒▒▒  eGovFrame Potal 온라인 지원 포탈  ▒▒▒</title>
<link href="<c:url value='/css/egovframework/com/com.css' />" rel="stylesheet" type="text/css" />

<script language="javascript">
Document.prototype.ready = function(callback) {
	  if(callback && typeof callback === 'function') {
	    document.addEventListener("DOMContentLoaded", function() {
	      if(document.readyState === "interactive" || document.readyState === "complete") {
	        return callback();
	      }
	    });
	  }
	};

	document.ready(function() {
		
		<c:if test="${msg != null && msg != ''}">
			alert('${msg}');
		</c:if>
		<c:choose>
		<c:when test="${url != null && url != ''}">
			<c:choose>
			<c:when test="${target != null && target != ''}">
				window.open('${url}','${target}');
			</c:when>
			<c:otherwise>
				location.href='${url}';
			</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			history.back();
		</c:otherwise>
		</c:choose>
		
	});
</script>
</head>

<body>

</body>
</html>
