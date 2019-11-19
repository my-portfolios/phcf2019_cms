<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>포항문화재단 CMS</title>
<style type="text/css">
	.grid-container {
	  display: grid;
	  grid-template-columns: 1fr 1fr 1fr 1fr;
	  grid-template-rows: 1fr 1fr 1fr;
	  grid-template-areas: "headerUnit headerUnit headerUnit headerUnit" "leftUnit ContentsUnit ContentsUnit ContentsUnit" "leftUnit ContentsUnit ContentsUnit ContentsUnit";
	}
	
	.headerUnit { grid-area: headerUnit; }
	
	.leftUnit { grid-area: leftUnit; }
	
	.ContentsUnit { grid-area: ContentsUnit; }
</style>
</head>
<body>
	<script type="text/javascript">
// 		console.log("resultList :", ${resultList});
	</script>
	<div class="body">
<!-- 		 여기에 left, top, bottom 페이지 인클루드해서 끼워준다. 프레임셋을 할 지 말 지 생각 중 -->
<!-- 		 left에서  forEach로 사용 가능 메뉴만 셀렉해서 돌릴거임. -->
		<div class="grid-container">
		  <div class="headerUnit" align="center">
		  	<h1>포항문화재단 헤더</h1>
		  </div>
		  <div class="leftUnit">좌측메뉴
		  <%@ include file="PhcfUnitLeft.jsp" %>
		  </div>
		  <div class="ContentsUnit">컨텐츠
		  </div>
		</div>
	</div>
</body>
</html>

