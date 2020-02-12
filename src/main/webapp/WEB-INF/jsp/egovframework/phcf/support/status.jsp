<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script> 
<script type="text/javascript" src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
<link href="<c:url value='/css/egovframework/com/com.css' />" rel="stylesheet" type="text/css">

<div class="wTableFrm">
		<h2>전송현황</h2>
		<%-- <h1><spring:message code="ussCmt.cmtManageList.cmtManage"/></h1> --%>

<form name="frm" id="frm" action="/cms/support/status.do" method="POST">
	<input type="hidden" id="page_no" name="page_no" value="${paramMap.page_no }">
	<input type="hidden" name="select_cms_id" value="">

	<div class="wTable">
			<!--검색-->
			<div class="search_box">
				<ul>
					<li>전체 건수 : <c:out value="${paramMap.total_article}" /></li>
					<li><select class="floatleft" name="search_tp_s">
							<option value="">++선택++</option>
							<option value="user_id" selected="selected">회원ID</option>
					</select></li>
					<li><input type="text" class="resetinput" name="search_s"
						value="${paramMap.search_s }" /></li>
					<li><input type="submit" value="검색" class="s_btn" /></li>
			</div>
			<div class="clear"></div>
			<!--검색-->

			<table class="wTable" id="table_word_wrap">
				<caption class="dpnone">CMS전송현황</caption>
				<thead>
					<tr>
						<th class="textcenter">seq</th>
						<th class="textcenter">회원ID</th>
						<th class="textcenter">결제구분</th>
						<th class="textcenter">동의서<br>전송여부</th>
						<th class="textcenter">회원등록<br>완료여부</th>
						<th class="textcenter">결제등록<br>완료여부</th>
						<th class="textcenter">삭제대상<br>여부</th>
						<th class="textcenter">삭제완료<br>여부</th>
						<th class="textcenter">회원이름</th>
						<th class="textcenter">출금액</th>
						<th class="textcenter">출금요청일</th>
						<th class="textcenter">출금회차</th>
						<th class="textcenter">사용여부</th>
						<th class="textcenter">등록자</th>
						<th class="textcenter">생성일</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${cmsStatus != null && cmsStatus != ''}">
							<c:forEach var="list" items="${cmsStatus}" varStatus="i">
								<tr>
									<td class="textcenter"><c:out value="${list.cms_id}" /></td>
									<td class="textcenter"><a name="cms" href="#" cms_id="${list.cms_id }"><c:out value="${list.user_id}" /></a></td>
									<td class="textcenter"><c:out value="${list.acc_tp }" /></td>
									<td class="textcenter"><c:out value="${list.agree_send_yn}" /></td>
									<td class="textcenter"><c:out value="${list.user_send_yn}" /></td>
									<td class="textcenter"><c:out value="${list.pay_send_yn}" /></td>
									<td class="textcenter">
										<c:choose>
											<c:when test="${list.user_send_yn == 'Y' && list.del_send_yn != 'Y' }">
												<select name="select_del_user" cms_id="${list.cms_id }">
													<option value="Y" <c:if test="${list.del_target_yn == 'Y' }">selected="selected"</c:if>>Y</option>
													<option value="N"<c:if test="${list.del_target_yn == 'N' }">selected="selected"</c:if>>N</option>
												</select>
											</c:when>
											<c:otherwise>
												<c:out value="${list.del_target_yn}" />
											</c:otherwise>
										</c:choose>
									</td>
									<td class="textcenter"><c:out value="${list.del_send_yn}" /></td>
									<td class="textcenter"><c:out value="${list.user_name}" /></td>
									<td class="textcenter"><c:out value="${list.sc_price}" /></td>
									<td class="textcenter"><c:out value="${list.sc_req_dd}" /></td>
									<td class="textcenter"><c:out value="${list.sc_cnt}" /></td>
									<td class="textcenter"><c:out value="${list.use_yn}" /></td>
									<td class="textcenter"><c:out value="${list.ins_id}" /></td>
									<td class="textcenter"><c:out value="${list.create_dt}" /></td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="15" style="text-align: center">조회된 정보가 없습니다.</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
	
	<!--page-->
	<div class="pagination">${paging}</div>
	<!--page-->
	
	<div class="btn">
		<input type="button" id="list_btn" class="" value="목록">
	</div>
	
	</div>
	
</form>
</div>


<script type="text/javascript">

	//페이징
	function fn_page(page_no) {
		$("#page_no").val(page_no);
		$("#frm").submit();
	}

	$(document).ready(function() {

		// 회원등록 삭제 처리
		$('[name="select_del_user"]').change(function() {

			if(this.value == 'Y' && !confirm("회원취소 후에는 신규등록 하셔야 됩니다.\n등록취소을 실행하시겠습니까?")) {
				// 선택 상태를 N으로 만든다..
				this.value = 'N'; 
				return; 
			}

			var cmsIdVal = $(this).attr('cms_id');
			var delVal = this.value;

			// ajax로 처리하는게 좋을듯 하다..
			$.post('/cms/support/status_del.do', {cms_id: cmsIdVal, del_target_yn:delVal}, function(result) {

				if(!result.state)	 {
					alert('회원등록 취소 처리 중 이상 발생');
					return;
				}

				if(delVal == 'Y') { alert('회원 취소가 등록 되었습니다.'); }
				else { alert('회원 취소 등록이 취소 처리 되었습니다.'); }
				
			}).fail(function() {
				alert('회원등록 취소 처리 중 오류 발생');
			});
			location.reload();
		});
		
		// 검색 버튼 클릭
		$('[name="searchBtn"]').click(function() {
			$('#frm').submit();
		});

		$('[name="cms"]').click(function() {

			$('input[name="select_cms_id"]').val( $(this).attr('cms_id') );

			$('#frm').attr('action', '/cms/support/status_edit.do');
			$('#frm').submit();
		});
		
		$('#list_btn').click(function() {
			// 목록 버튼 보기
			location.href="/cms/support/listView.do";
		});
		
	});
</script>