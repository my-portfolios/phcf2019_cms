<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
<title><spring:message code="comUatUia.title" /></title><!-- 로그인 -->
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/com.css' />">
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/uat/uia/login.css' />">
<script type="text/javascript" src="<c:url value='/js/egovframework/com/cmm/showModalDialog.js'/>" ></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/cmm/jquery.js'/>" ></script>
<script type="text/javaScript" language="javascript">
function actionLogin() {
	if (document.loginForm.id.value =="") {
        alert("<spring:message code="comUatUia.validate.idCheck" />"); <%-- 아이디를 입력하세요 --%>
    } else if (document.loginForm.password.value =="") {
        alert("<spring:message code="comUatUia.validate.passCheck" />"); <%-- 비밀번호를 입력하세요 --%>
    } else {
        $.ajax({
        	url: '/uat/uia/actionLogin.do',
        	data: {
        		id: $("#id").val(),
        		password: $("#password").val(),
        		userSe: $("#userSe").val()
        	},
        	type: 'post',
        	dataType: 'json',
        	success: function(data){
        		if(data.result=='success'){
        			location.href='/';
        		}
        		else{
        			alert("아이디 또는 비밀번호가 올바르지 않습니다!");
        		}
        	}
        });
    }
}

function setCookie (name, value, expires) {
    document.cookie = name + "=" + escape (value) + "; path=/; expires=" + expires.toGMTString();
}

function getCookie(Name) {
    var search = Name + "=";
    if (document.cookie.length > 0) { // 쿠키가 설정되어 있다면
        offset = document.cookie.indexOf(search);
        if (offset != -1) { // 쿠키가 존재하면
            offset += search.length;
            // set index of beginning of value
            end = document.cookie.indexOf(";", offset);
            // 쿠키 값의 마지막 위치 인덱스 번호 설정
            if (end == -1)
                end = document.cookie.length;
            return unescape(document.cookie.substring(offset, end));
        }
    }
    return "";
}

function saveid(form) {
    var expdate = new Date();
    // 기본적으로 30일동안 기억하게 함. 일수를 조절하려면 * 30에서 숫자를 조절하면 됨
    if (form.checkId.checked)
        expdate.setTime(expdate.getTime() + 1000 * 3600 * 24 * 30); // 30일
    else
        expdate.setTime(expdate.getTime() - 1); // 쿠키 삭제조건
    setCookie("saveid", form.id.value, expdate);
}

function getid(form) {
    form.checkId.checked = ((form.id.value = getCookie("saveid")) != "");
}

function fnInit() {
	if('<%=request.getParameter("result")%>' == "fail"){
		alert("아이디 또는 비밀번호가 올바르지 않습니다.");
	}

    //getid(document.loginForm);
    // 포커스
    //document.loginForm.rdoSlctUsr.focus();
    
    getid(document.loginForm);
}

</script>
</head>
<body onLoad="fnInit();">		
<!-- javascript warning tag  -->
<noscript class="noScriptTitle"><spring:message code="common.noScriptTitle.msg" /></noscript>


<!-- 일반로그인 -->
<div class="login_form">
	<form name="loginForm" id="loginForm" action="<c:url value='/uat/uia/actionLogin.do'/>" method="post">
	<input type="hidden" id="message" name="message" value="<c:out value='${message}'/>">
	
	<fieldset>
		<div style="text-align: center;"><img src="<c:url value='/images/egovframework/com/cmm/main/login_logo.png'/>" alt="login title image"  title="login title image"></div>
	
		<div class="login_input">
			<ul>
				<!-- 아이디 -->
				<c:set var="title"><spring:message code="comUatUia.loginForm.id"/></c:set>
				<li>
					<label for="id">${title}</label>
					<input type="text" name="id" id="id" maxlength="10" title="${title} ${inputTxt}" placeholder="${title} ${inputTxt}">
				</li>
				<!-- 비밀번호 -->
				<c:set var="title"><spring:message code="comUatUia.loginForm.pw"/></c:set>
				<li>
					<label for="password">${title}</label>
					<input type="password" name="password" id="password" maxlength="12" title="${title} ${inputTxt}" placeholder="${title} ${inputTxt}">
				</li>
				<!-- 아이디 저장 -->
				<c:set var="title"><spring:message code="comUatUia.loginForm.idSave"/></c:set>
				<li class="chk">
					<input type="checkbox" name="checkId" class="check2" onclick="javascript:saveid(document.loginForm);" id="checkId">${title}
				</li>
				<li>
					<input type="button" class="btn_login" value="<spring:message code="comUatUia.loginForm.login"/>" onclick="actionLogin()"> <!-- 로그인  -->
				</li>
				<ul class="btn_idpw">
						<li><a href="/uss/umt/EgovStplatCnfirmMber.do">회원가입</a></li> <!-- 회원가입  -->
						<li><a href="/uat/uia/egovIdSearch.do">아이디 찾기</a> / <a href="/uat/uia/egovPasswordSearch.do">비밀번호 찾기</a></li>
					</ul>
				
			</ul>
		</div>
	</fieldset>
	
	<input name="userSe" id="userSe" type="hidden" value="USR"/>
	<input name="j_username" type="hidden"/>
	</form>
</div>
</body>
</html>