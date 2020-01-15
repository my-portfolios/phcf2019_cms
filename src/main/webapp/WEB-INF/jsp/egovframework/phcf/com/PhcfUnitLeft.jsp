<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>    
<!DOCTYPE html> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/jqueryui.css' />">
<script src="<c:url value='/js/egovframework/com/cmm/jquery.js' />"></script>
<script src="<c:url value='/js/egovframework/com/cmm/jqueryui.js' />"></script>

<title>Insert title here</title>
</head>
<body>
	<script>
// 	console.log('resultList : ', ${resultList});
	$(document).ready(function(){
		getMenuList();
	});
	
	function getMenuList(){
		$.ajax({
			url:"/PhcfLeft.do"
			, dataType: "json"
			, success : function( data ) {
	            
				console.log('data : ', data);
			}
		});
	}
	</script>	
	<h3> 여기여기 </h3>
	<ul>
		<li>메뉴테스트</li>
	</ul>
</body>
</html>