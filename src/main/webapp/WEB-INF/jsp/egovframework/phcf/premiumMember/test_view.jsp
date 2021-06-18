<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>request, response test</title>
		<link href="<c:url value='/css/egovframework/com/com.css' />" rel="stylesheet" type="text/css">
		<script src="<c:url value='/js/egovframework/com/cmm/jquery.js' />"></script>
		
		<link type="text/css" rel="stylesheet" href="/js/egovframework/phcf/jsgrid-1.5.3/jsgrid.min.css" />
		<link type="text/css" rel="stylesheet" href="/js/egovframework/phcf/jsgrid-1.5.3/jsgrid-theme.min.css" />
		<script type="text/javascript" src="<c:url value='/js/egovframework/phcf/jsgrid-1.5.3/jsgrid.min.js'/>"></script>
		
		<script type="text/javascript">
			var user = {};
			
			console.log("here");
			function submit(){
				user.id = "hkimkm1";
				user.name = "김경민";
// 				console.log("data: " + JSON.stringify(user));
				$.ajax({
					url: "/premiumMember/testPage.do",
					data: user,
					type: "POST",
					dataType: "json",
// 					contentType: "application/json",
					success: function(data){
						console.log(JSON.parse(data));
						if(!data){
							alert("아이디가 존재하지 않습니다!");
							return false;
						}
						
						$("#print").text(data.mbershipType);
// 						location.href="/premiumMember/testPage.do";
						alert("데이터 전송 성공");
							
					},
					error : function(){
						alert("에러");
					}
				});
				
				
			}
		</script>
</head>
<body>
					<form id="frm" action="<c:url value='/premiumMember/test.do'/>" name="frm" method="post">
					    <input type="hidden" name="test_id" value="hkimkm1" class="t_id"/>
					    <input type="hidden" name="test_name" value="김경민" class="t_name"/>
					    <input type="submit" value="test" onclick="submit()"/>
					</form>
					<div id="print">
<%-- 						<c:out value="${user_info.mberId}" /> --%>
<%-- 						<c:out value="${user_info.mberNm}" /> --%>
<%-- 						<c:out value="${user_info.membershipType}" /> --%>
					</div>
</body>
</html>