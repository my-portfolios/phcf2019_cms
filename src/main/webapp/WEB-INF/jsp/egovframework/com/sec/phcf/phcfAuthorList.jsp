<%
 /**
  * @Class Name : EgovPhcfAuthor.java
  * @Description : EgovPhcfAuthorList List 화면
  * @Modification Information
  * @
  * @ 수정일         수정자               수정내용
  * @ ----------    ---------    ---------------------------
  * @ 2020. 1. 15    윤병훈            최초생성
  *
  *  @author 대외사업부
  *  @since 2020.01.15
  *  @version 1.0
  *  @see
  */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>문화재단권한관리</title><!-- 부서권한관리 목록 -->
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<style>
a:hover {
cursor: pointer;
}
@media all and (max-width: 1550px) {
	.table_scroll {   overflow-x: auto;   white-space: nowrap; }
}
</style>

<script type="text/javascript" src="<c:url value='/js/egovframework/com/cmm/jquery-1.12.4.min.js' />"></script>
<link type="text/css" rel="stylesheet" href="/js/egovframework/phcf/jsgrid-1.5.3/jsgrid.min.css" />
<link type="text/css" rel="stylesheet" href="/js/egovframework/phcf/jsgrid-1.5.3/jsgrid-theme.min.css" />
    
<script type="text/javascript" src="<c:url value='/js/egovframework/phcf/jsgrid-1.5.3/jsgrid.min.js'/>"></script>

<script type="text/javascript">
$(document).ready(function() {
	
	// 필터링 기능 때문에 전체를 조회 할 수 있는 코드값을 추가 함.
	var pageArr = ${pageList};
	pageArr.unshift({code: '', codeNm: '전체'});
	//var gradeListArr = '';
	//gradeListArr.unshift({code: '', code_nm: '전체'})
	//var spMhTpArr = '';
	//spMhTpArr.unshift({code: '', codeNm: '전체'});
	//var scPriceTpArr = '';
	//scPriceTpArr.unshift({code: '', codeNm: '전체'});
	
	// jsGrid 셋팅
	$('#jsGrid').jsGrid({
		width: '100%'
		, height: 'auto'
		
		, autoload: true
		
		, filtering: true
		, editing: true
		
		, paging: true
		, pageLoading: true
		, pageSize: 10
		, pageButtoncount: 5
		, pageIndex: 1
		
		, deleteConfirm: "삭제 하시겠습니까?"
		, controller: {
			loadData: function(filter) {
				
				var d = $.Deferred();
				
				$.ajax({
					type: 'POST'
					, url: '/sec/phcf/getEgovPhcfAuthorList.do'
					, data: filter
					, dataType: 'JSON'
				}).done(function(response) {
					d.resolve({data: response.value, itemsCount: response.totCnt });
				});
				
				return d.promise();
			}
			, updateItem: function(item) {
				return $.ajax({
					type: 'POST'
					, url: '/'
					, data: item
					, success: function(result) {
						$("#jsGrid").jsGrid("loadData");
					}
					, error: function(e) {
						console.log('== error :', e);
						alert('업데이트 중 오류 발생.');
					}
				});
			} 
			, deleteItem: function(item) {
				return $.ajax({
					type: 'POST'
					, url : '/'
					, data: item
					, success: function(result) {
						$("#jsGrid").jsGrid("loadData");
					}
					, error: function(e) {
						console.log('== error :', e);
						alert('삭제 중 오류 발생.');
					}
				});
			}
		}
	
		, noDateContent: '데이터가 없습니다.'
		, loadMessage: '조회 중...'
	
		, fields: [
			{name: 'seq', title:'순번', type: 'text', editing: false, filtering: false, width: 50, align: 'center' }
			, {name: 'authName', title: '권한이름', type: 'text', editing: false, width: 80, align: 'center' }
			, {name: 'page', title: '페이지', type: 'select', items: pageArr, valueField: 'code', textField: 'codeNm', editing: false, width: 70, align: 'center' }
			/* , {name: 'orgnztId', title: '조직ID', type: 'text', editing: false, width: 150, align: 'center' } */
			, {name: 'orgnztNm', title: '조직이름', type: 'text', editing: false, width: 120 }
			, {name: 'groupId', title: '그룹ID', type: 'text', editing: false, width: 100 }
			, {name: 'acceptLink', title: '허용LINK', type: 'text', editing: false, filtering: false, width: 200 }
			, {name: 'banLink', title: '차단LINK', type: 'text', editing: false, filtering: false, width: 200 }
			, {name: 'authPriority', title: '우선순위', type: 'text', editing: false, filtering: false, align: 'center' }
			, {name: 'useYn', title: '사용유무', type: 'text', editing: false, align: 'center' }
			/* , {name: 'insId', title: '등록자', type: 'text', editing: false, filtering: false, align: 'center' }
			, {name: 'insDt', title: '생성일', type: 'text', editing: false, filtering: false, width: 180, align: 'center' } */
			, {type: 'control'}
		]
	});
	
});
</script>

</head>

<body>

<form name="frm" id="frm" action="${ctx}/sec/phcf/getEgovPhcfAuthorList.do" method="post" >

<div class="search_box" >
	<ul style="display:none;">
		<li class="div-left"></li>
		<li></li>
		<li></li>
	</ul>
</div>

<input type="hidden" id="page_no" name="page_no" value="${paramMap.page_no }">

	<div class="area">
		<div id="jsGrid"></div>
	</div>

</form>

</body>
</html>
