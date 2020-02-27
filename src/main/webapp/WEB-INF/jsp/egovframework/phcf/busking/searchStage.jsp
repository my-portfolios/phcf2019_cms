<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script>
	var datePicker;
	var datePicker_use = false;
	$(function(){
		$("#searchType").on("change",function(){
			if($(this).val() == "SEARCH_DATE"){
				datePicker = $("#keyword").datepicker({
					language: 'ko',
					position: 'bottom right',
					showOtherMonths: false,
					moveToOtherMonthsOnSelect: false,
					selectOtherMonths: false,
					navTitles: {
						days: 'yyyy 년 MM'
					}
				}).data('datepicker');
				datePicker_use = true;
			}
			else if(datePicker_use) {
				datePicker.destroy();
				datePicker_use = false;
			}
		});
		
	});
</script>
<div class="search_box">
	
	<ul>
		<li><!-- 지역 -->
              <input type="text" id="date" class="datepicker2"  name="date" value="" readonly="readonly" />
		</li>
		<li><!-- 지역 -->
              <form:select class="floatleft" id="place" name="place" path="placeCodeNmList" title="${title} ${inputTxt}" cssClass="txt">
				<form:option value="" label="장소" />
				<form:options items="${placeCodeNmList}"/>
			</form:select>
		</li>
		<li><!-- 상태-->
			<c:if test='<%= request.getParameter("fixStatus") == null %>'>
	            <form:select class="floatleft" id="approve" name="approve" path="approveCodeNmList" title="${title} ${inputTxt}" cssClass="txt">
					<form:option value="" label="상태" />
					<form:options items="${approveCodeNmList}"/>
				</form:select>
			</c:if>
		</li>
		<li><!-- 검색키워드-->
			<c:if test='<%= request.getParameter("fixStatus") == null %>'>
	            <select class="floatleft" id="searchCondition" name="searchCondition" title="${title} ${inputTxt}" cssClass="txt">
					<option value="" label="선택" />
					<option value="TEAM_NM" label="팀이름">
					<option value="HEAD_NM" label="대표자">
				</select>
			</c:if>
		</li>
		<!-- 검색키워드 및 조회버튼 -->
		<li>
			<input class="s_input" id="searchKeyword" type="text"/>
			<input type="button" class="s_btn" value="검색" onclick="search();"/>
					
		</li>
		<li><button onclick="updateSearchCount(this)">30</button></li>
		<li><button onclick="updateSearchCount(this)">50</button></li>
	</ul>
</div>