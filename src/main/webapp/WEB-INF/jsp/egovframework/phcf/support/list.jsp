<!DOCTYPE html>
<%
 /**
  * @Class Name  : support.jsp
  * @Description : 후원관리 화면
  * @Modification Information
  * @
  * @  수정일             수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2019.08.29   김경식              최초 생성
  *
  *  @author 대외사업부
  *  @since 2019.08.29
  *  @version 1.0
  *  @see
  *
  */
%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ko">
<head>
<title>후원명단보기</title>
<link href="<c:url value='/css/egovframework/com/com.css' />" rel="stylesheet" type="text/css">

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
	var userTpArr = ${userTp};
	userTpArr.unshift({code: '', codeNm: '전체'});
	var gradeListArr = ${gradeList};
	gradeListArr.unshift({code: '', code_nm: '전체'})
	var spMhTpArr = ${spMhTp};
	spMhTpArr.unshift({code: '', codeNm: '전체'});
	var scPriceTpArr = ${scPriceTp};
	scPriceTpArr.unshift({code: '', codeNm: '전체'});
	
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
					, url: '/cms/support/getCmsSupportList.do'
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
					, url: '/cms/support/updateCmsSupportItem.do'
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
					, url : '/cms/support/deleteCmsSupportItem.do'
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
			{name: 'sp_id', title:'순번', type: 'text', editing: false, filtering: false, width: 50, align: 'center' }
			, {name: 'user_tp', title: '회원유형', type: 'select', items: userTpArr, valueField: 'code', textField: 'codeNm', editing: false, width: 85, align: 'center' }
			, {name: 'user_grade', title: '등급', type: 'select', items: gradeListArr, valueField: 'code', textField: 'code_nm', width: 105, align: 'center' }
			, {name: 'user_id', title: '아이디', type: 'text', editing: false, align: 'center' }
			, {name: 'user_nm', title: '후원자이름', type: 'text', editing: false, width: 150 }
			, {name: 'user_phone', title: '연락처', type: 'text', editing: false, width: 150 }
			, {name: 'user_email', title: 'e-mail', type: 'text', editing: false, width: 200 }
			, {name: 'sp_mh_tp', title: '후원방식', type: 'select', items: spMhTpArr, valueField: 'code', textField: 'codeNm', editing: false, align: 'center' }
			, {name: 'sc_price_tp', title: '결제방식', type: 'select', items: scPriceTpArr, valueField: 'code', textField: 'codeNm', editing: false, align: 'center' }
			, {name: 'sc_price', title: '후원금액(원)', type: 'number', editing: false, filtering: false
				, itemTemplate: function(value) {
					if(typeof value == 'undefined') { return; }
					return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
				}
			  }
			, {name: 'browser', title: '결제모드', type: 'text', editing: false, filtering: false, align: 'center' }
			, {name: 'ins_id', title: '등록자', type: 'text', editing: false, filtering: false, align: 'center' }
			, {name: 'create_dt', title: '생성일', type: 'text', editing: false, filtering: false, width: 180, align: 'center' }
			, {name: 'admin_msg', title: '관리자메시지', type: 'text', filtering: false, width: 200 }
			, {type: 'control'}
		]
	});
	
});
</script>

<script type="text/javascript">
$(document).ready(function() {

	// 아이디를 선택 하여 후원 상세 화면을 보여 준다.
	$('a[name="supportDetail"]').click(function() {

		$('input[name="sp_id"]').val($(this).attr('sp_id'));
		$('form[name="supportDetailFrm"]').submit();
	});
	
	// 관리자 메시지를 수정 할 수 잇는 레이어 팝업 창을 띠워 준다.
	$('[name="msg_btn"]').click(function() {

		var msg = $(this).attr('msg');
		var sp_id = $(this).attr('sp_id');

		$('.viewPop').dialog({
			title: '관리자 메시지'
			, modal: true
			, resizable: false
		    , draggable: false
			, open: function() {
				$(".ui-button").blur();
				$('html').css('overflow','hidden');
				$(this).html('<div>현재메시지 : ' + msg
								 + '<br><br>'
								 + '변경메시지 : <br><br>'
								 + '<input type="text" name="admin_msg" style="width:100%; height:25px" value="">'
								 + '</div>');
			}
			, close : function() {
				$('html').css('overflow-y','scroll');
				$('html').css('overflow-x','hidden');
			}
			, buttons: {
				"적용" : function() {
					if(!confirm("적용하시겠습니까?")) { return; }

					var thisDialog = $(this);
					var msg = $(this).find('[name="admin_msg"]').val();

					$.post('/cms/support/updateAdminMsg.do', {sp_id: sp_id, admin_msg: msg}, function(result) {

						if(!result.state) { alert('관리자 메시지 입력 중 이상 발생.'); thisDialog.dialog('close'); }

						alert('관리자 메시지를 수정 하였습니다.');
						location.href ="/cms/support/listView.do";
						
					}).fail(function() {
						alert('관리자 메시지 입력 중 오류 발생.');
						thisDialog.dialog('close');
					});
				}
				, "닫기" : function() {
					$(this).dialog('close');
				}
			}
		});
	});

	// 등급 선택시 관리자가 수정 할 수 있는 레이어 팝업 창을 띠워 준다.
	$('[name="gradePopup"]').click(function() {

		var userTp = $(this).attr('user_tp');
		var grade = $(this).attr('grade');
		var userId = $(this).attr('user_id');

		var divGradeHtml;
		if(userTp == '개인') {
			divGradeHtml = $('[name="divUserGradeList"]').html();
		} else {
			divGradeHtml = $('[name="divCompGradeList"]').html();
		}
		
		$('.viewPop').dialog({ 
			title: '회원 등급 변경',
			modal: true,
			resizable: false,
	        draggable: false,
			open : function(){
				$(".ui-button").blur();
				$('html').css('overflow','hidden');
				$(this).html('<div>현재등급 : ' + grade
								 + '<br><br>'
								 + divGradeHtml
								 + '</div>');
			},
			close : function(){
				$('html').css('overflow-y','scroll');
				$('html').css('overflow-x','hidden');
			},
			buttons: {
				"적용" : function() {

					var thisDialog = $(this);

					$.post('/cms/support/updateGrade.do', { user_id: userId, grade: $(this).find('[name="gradeList"]').val() }, function(result) {

						if(!result.state) { alert('회원 등급 변경 중 이상 발생'); thisDialog.dialog('close'); }

						alert('등급 변경이 완료 되었습니다.');
						location.href ="/cms/support/listView.do";

					}).fail(function() {
						alert('회원 등급 변경 중 오류 발생');
						thisDialog.dialog('close');
					});
				}
				, "닫기" : function() {
					$(this).dialog('close');
				}
			}
		});
	});
	
	// 결제 테스트
	$('#test_btn').click(function() {
		window.open("${ctx}/nicepay/payRequest.do", "nicepay", "width=730,height=800");
	});

	// 검색 조건 셋팅
	var searchUserTp = '${paramMap.search_user_tp}';
 	$('select[name="search_user_tp"]').val(searchUserTp).prop('selected', true);

 	// 검색 버튼 선택
	$('#search').click(function() {
		$('#page_no').val(1);
		$('#frm').submit();
	});

	// 등록 버튼 선택
	$('#insert_btn').click(function() {
		$('#frm').attr('action', '${ctx}/cms/support/add.do');
		$('#frm').submit();
	});

	// CMS 전송리스트 버튼 선택
	$('#cms_status').click(function() {
		$('#page_no').val(1);
		$('#frm').attr('action', '/cms/support/status.do');
		$('#frm').submit();
	});

});

// 페이징
function fn_page(page_no) {
    $("#page_no").val(page_no);
    $("#frm").submit();
}
</script>

</head>
<body>

<div class="board">
<h1>후원신청 관리</h1>
<%-- <h1><spring:message code="ussCmt.cmtManageList.cmtManage"/></h1> --%>

				
<form name="frm" id="frm" action="${ctx}/cms/support/listView.do" method="post" >

<div class="search_box" >
	<ul>
		<li class="div-left"># 아이디 가 없는 회원은 등급을 수정 할 수 없습니다.</li>
		<li><input type="button" id="insert_btn"  value="카드및CMS등록" class="s_btn"></li>
		<li><input type="button" id="cms_status" value="CMS전송현황" class="s_btn"></li>
	</ul>
</div>

<input type="hidden" id="page_no" name="page_no" value="${paramMap.page_no }">

	<div class="area">
		<div id="jsGrid"></div>
	</div>

</form>

<!-- 상세화면으로 보내기 위한 Form -->
<form name="supportDetailFrm" action="/cms/support/supportDetailView.do" method="post">
	<input type="hidden" name="sp_id" value="">
</form>

<!-- 등급관련 팝업 처리 -->
<div class="viewPop" style="display:none"></div>
<!-- 개인회원 등급을 가져온다. -->
<c:if test="${not empty userGradeList }">
<div name="divUserGradeList" style="display: none">
	변경등급 : 
	<select name="gradeList">
		<c:forEach var="list" items="${userGradeList }" varStatus="status">
			<option value="${list.cd_v }"><c:out value="${list.cd_nm }"/></option>
		</c:forEach>
	</select>
</div>
</c:if>
<!-- 기업및단체회원 등급을 가져온다. -->
<c:if test="${not empty compGradeList }">
<div name="divCompGradeList" style="display: none">
	변경등급 : 
	<select name="gradeList">
		<c:forEach var="list" items="${compGradeList }" varStatus="status">
			<option value="${list.cd_v }"><c:out value="${list.cd_nm }"/></option>
		</c:forEach>
	</select>
</div>
</c:if>

</div>
</body>
</html>