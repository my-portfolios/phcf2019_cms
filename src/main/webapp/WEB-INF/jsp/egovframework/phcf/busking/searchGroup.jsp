<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link rel="stylesheet" href="/css/egovframework/phcf/datepicker.css" />

<script src="/js/egovframework/phcf/datepicker.js"></script>
<script src="/js/egovframework/phcf/datepicker.ko.js"></script>
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
		<li><!-- 장르-->
			<c:if test='<%= request.getParameter("fixStatus") == null %>'>
	            <form:select class="floatleft" id="genre" name="genre" path="genreCodeNmList" title="${title} ${inputTxt}" cssClass="txt">
					<form:option value="" label="장르" />
					<form:options items="${genreCodeNmList}"/>
				</form:select>
			</c:if>
		</li>
		<li><!-- 지역 -->
              <form:select class="floatleft" id="areas" name="areas" path="areaCodeNmList" title="${title} ${inputTxt}" cssClass="txt">
				<form:option value="" label="지역" />
				<form:options items="${areaCodeNmList}"/>
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
					<option value="TEAM_NAME" label="팀이름">
					<option value="HEAD_NAME" label="대표자">
				</select>
			</c:if>
		</li>
		<!-- 검색키워드 및 조회버튼 -->
		<li>
			<input class="s_input" id="searchKeyword" type="text"/>
			<input type="button" class="s_btn" value="검색" onclick="search();"/>
					
		</li>
		<li><button onclick="updateSearchCount(this)" class="s_btn">30줄</button></li>
		<li><button onclick="updateSearchCount(this)" class="s_btn">50줄</button></li>
	</ul>
</div>