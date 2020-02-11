<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<c:set var="pageTitle">휴먼회원관리</c:set>
<!DOCTYPE html>
<html>
<head>
<title>${pageTitle} <spring:message code="title.create" /></title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="<c:url value='/css/egovframework/com/com.css' />" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<c:url value='/js/egovframework/com/cmm/jquery-1.12.4.min.js' />"></script>
<link type="text/css" rel="stylesheet" href="/js/egovframework/phcf/jsgrid-1.5.3/jsgrid.min.css" />
<link type="text/css" rel="stylesheet" href="/js/egovframework/phcf/jsgrid-1.5.3/jsgrid-theme.min.css" />
    
<script type="text/javascript" src="<c:url value='/js/egovframework/phcf/jsgrid-1.5.3/jsgrid.min.js'/>"></script>

<script type="text/javascript">
$(document).ready(function() {
	
	// 필터링 기능 때문에 전체를 조회 할 수 있는 코드값을 추가 함.
	/* var pageArr = ${pageList};
	pageArr.unshift({code: '', codeNm: '전체'});
	var deptArr = ${deptList};
	deptArr.unshift({deptCode: '', deptNm: '전체'})
	var groupArr = ${groupList};
	groupArr.unshift({groupId: '', groupNm: '전체'});
	var menuArr = ${menuList};
	menuArr.unshift({link: '', menuNm: '전체'});
	var useYnArr = [{code : 'Y', codeNm: 'Y'}, {code : 'N', codeNm: 'N'}];
	useYnArr.unshift({code: '', codeNm: '전체'}); 
	var sPeriodArr = [{prd : '6', prdNm: '6개월'}, {prd : '12', prdNm: '1년'}, {prd : '0', prdNm: '활동회원'}];*/
	var sendMailYnArr = [{code : 'Y', codeNm: 'Y'}, {code : 'N', codeNm: 'N'}];
	
	// jsGrid 셋팅
	$('#jsGrid').jsGrid({
		width: '100%'
		, height: 'auto'
		
		, autoload: true
		
		, filtering: true
		, editing: true
		//, inserting: true
		, paging: true
		, pageLoading: true
		, pageSize: 10
		, pageButtoncount: 5
		, pageIndex: 1
		
		, deleteConfirm: "삭제 하시겠습니까?"
		, controller: {
			loadData: function(filter) {
				
				var getDataUrl = '';
				if($("#sPeriodSort").val()) filter.sPeriod = $("#sPeriodSort").val();
				if(filter.sPeriod == '6' || filter.sPeriod == '0') getDataUrl = '/uss/umt/getDormantMber.do';
				else getDataUrl = '/uss/umt/getMovedDormantMber.do';
				
				var d = $.Deferred();
				
				$.ajax({
					type: 'POST'
					, url: getDataUrl
					, data: filter
					, dataType: 'JSON'
				}).done(function(response) {
					d.resolve({data: response.value, itemsCount: response.totCnt });
				});
				
				return d.promise();
			}
			/* , insertItem: function(item) {
				return $.ajax({
					type: 'POST'
					, url: '/uss/umt/insertDormantMber.do'
					, data: item
					, success: function(result) {
						$("#jsGrid").jsGrid("loadData");
					}
					, error: function(e) {
						console.log('== error :', e);
						alert('삽입 중 오류 발생.');
					}
				});
			}  */
			, updateItem: function(item) {
				return $.ajax({
					type: 'POST'
					, url: '/uss/umt/updateDormantMber.do'
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
					, url : '/uss/umt/deleteDormantMber.do'
					, data: item
					, success: function(result) {
						$("#jsGrid").jsGrid("loadData");
					}
					, error: function(e) {
						console.log('== error :', e.responseText);
						alert('삭제 중 오류 발생.');
					}
				});
			}
		}
	
		, noDateContent: '데이터가 없습니다.'
		, loadMessage: '조회 중...'
		, rowClick: function(args) {
			var $items = $("#"+args.item.mberId+"Ch");
			if(!$items.prop("checked")) $items.prop("checked",true);
			else $items.prop("checked",false);
		}
			
		, fields: [
			{name: 'mberId', title:'<input type="checkbox" id="checkall">', 
				itemTemplate: function(value, item) {
                	return $("<input>").attr("type", "checkbox")
                					.attr("name", "mberId")
                					.attr("value",value)
                					.attr("class","mberIdCh")
                					.attr("id",value+"Ch")
                					.click(function(){ 
                						if(!$(this).prop("checked")) $(this).prop("checked",true);
                						else $(this).prop("checked",false);
                					});
             	}, 
            width: 20, align: 'center'  }
			, {name: 'mberId', title:'아이디', type: 'text', editing: false, width: 50, align: 'center' }
			, {name: 'mberNm', title: '회원명', type: 'text', editing: false, width: 80, align: 'center' }
			, {name: 'groupNm', title: '그룹이름', type: 'text', editing: false, width: 80, align: 'center' }
			, {name: 'moblphonNo', title: '연락처', type: 'text', editing: false, width: 80, align: 'center' }
			, {name: 'mberEmailAdres', title: '이메일', type: 'text', editing: false, width: 80, align: 'center',
				itemTemplate: function(value, item) {
					return $("<div>").attr("class","mailAdrsDiv").text(value);
				}
			}
			, {name: 'sbscrbDe', title: '최초가입일', type: 'text', editing: false, filtering: false, width: 80, align: 'center' }
			, {name: 'creatDt', title: '마지막로그인', type: 'text', editing: false, filtering: false, width: 80, align: 'center' }
			, {name: 'sendMailYn', title: '메일수신여부', type: 'select', items: sendMailYnArr, valueField: 'code', textField: 'codeNm', editing: false, filtering: true,
				itemTemplate: function(value, item) {
					return $("<div>").attr("class","sendMailYnDiv").text(value);
				}, 
				width: 80, align: 'center' }
			, {type: 'control', deleteButton: false}
		]
	});
	
	$("#sPeriodSort").change(function() {
		$('#jsGrid').jsGrid("search", { sPeriod: $("#sPeriodSort").val() })
    });
	
	$("#sendEmail").click(function(){
		$(".mberIdCh").each(function (index, item){
			var $mailAdrs = $("#"+$(item).val()+"Ch").closest('td').nextAll(':has(.mailAdrsDiv):first').text();
			if($(item).prop("checked")) {
				var mailSubject = "휴면회원이 되었습니다.";
				var mailContent = "휴면회원이 되었습니다.";
				
				var formData = new FormData();
				
				formData.append("posblAtchFileNumber","1");
				formData.append("fileStreCours","");
				formData.append("recptnPerson",$mailAdrs);
				formData.append("sj",mailSubject);
				formData.append("emailCn",mailContent);
				
				var xhr = new XMLHttpRequest();

				xhr.open("POST", "/cop/ems/insertSndngMail.do");  
				xhr.send(formData);
				
				console.log($(item).val());
				console.log($mailAdrs);
			}
		});
	});
	
	$("#sPeriodSelect").change(function(){
		$("#frm").submit();
	});
	
});
</script>

</head>

<body>

<form name="frm" id="frm" action="/uss/umt/getDormantMber.do" method="post" >
<div class="board">
	<h1>휴먼회원 관리</h1>
<div class="search_box" >
	<ul>
		<li class="div-right"><div id="sendEmail">안내메일 보내기</div></li>
		<li class="div-right">
			<select id="sPeriodSort">
				<option value="12">휴먼회원(12개월)</option>
				<option value="6">휴먼회원(6개월)</option>
				<option value="0">활동회원</option>
			</select>
		</li>
	</ul>
</div>

<input type="hidden" id="page_no" name="page_no" value="${paramMap.page_no }">

	<div class="area">
		<div id="jsGrid"></div>
	</div>

</div>

</form>

</body>
</html>

