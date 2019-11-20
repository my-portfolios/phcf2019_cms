<%
 /**
  * @Class Name : EgovArticleList.jsp
  * @Description : EgovArticleList 화면
  * @Modification Information
  * @
  * @  수정일             수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2009.02.01   박정규              최초 생성
  *   2016.06.13   김연호              표준프레임워크 v3.6 개선
  *   2018.06.15   신용호              페이징 처리 오류 개선
  *  @author 공통서비스팀
  *  @since 2009.02.01
  *  @version 1.0
  *  @see
  *
  */

%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>문화재단</title>

<link rel="stylesheet" type="text/css" href="<c:url value='/css/egovframework/phcf/full-page/full-page-scroll.css' />">
<link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'/>
<link href='http://fonts.googleapis.com/css?family=Titillium+Web:400,600,700' rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="<c:url value='/css/egovframework/phcf/main/style.css' />">
<link rel="stylesheet" href="<c:url value='/css/egovframework/phcf/main/menu.css' />">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/egovframework/phcf/slick/slick.css' />"/>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/egovframework/phcf/slick/slick-theme.css' />"/>
<link rel="stylesheet" href="<c:url value='/css/egovframework/phcf/main/reset.css' />"> <!-- CSS reset -->
<style type="text/css">

</style>

<script src="https://code.jquery.com/jquery-2.2.0.min.js" type="text/javascript"></script>
<script src="<c:url value='/js/egovframework/phcf/slick/slick.js' />" type="text/javascript" charset="utf-8"></script>
<script src="<c:url value='/js/egovframework/phcf/main/modernizr.js' />"></script> <!-- Modernizr -->
</head>
<body>
<c:if test="${loginVO != null}">
	${loginVO.name }<spring:message code="comCmm.unitContent.2"/> <a href="${pageContext.request.contextPath }/uat/uia/actionLogout.do"><spring:message code="comCmm.unitContent.3"/></a>
</c:if>
<c:if test="${loginVO == null }">
	<jsp:forward page="/uat/uia/egovLoginUsr.do"/>
</c:if>
<!--헤더시작-->
	<header style="position:fixed; width: 100%; top: 0;">
		<c:import url="${pageContext.request.contextPath}/EgovTop.do" />
	</header>
	<!-- 모바일 메뉴 시작-->
	<nav id="cd-lateral-nav">
		<ul class="cd-navigation">
			<li class="item-has-children">
				<a href="#0">Services</a>
				<ul class="sub-menu">
					<li><a href="#0">Brand</a></li>
					<li><a href="#0">Web Apps</a></li>
					<li><a href="#0">Mobile Apps</a></li>
				</ul>
			</li> <!-- item-has-children -->

			<li class="item-has-children">
				<a href="#0">Products</a>
				<ul class="sub-menu">
					<li><a href="#0">Product 1</a></li>
					<li><a href="#0">Product 2</a></li>
					<li><a href="#0">Product 3</a></li>
					<li><a href="#0">Product 4</a></li>
					<li><a href="#0">Product 5</a></li>
				</ul>
			</li> <!-- item-has-children -->

			<li class="item-has-children">
				<a href="#0">Stockists</a>
				<ul class="sub-menu">
					<li><a href="#0">London</a></li>
					<li><a href="#0">New York</a></li>
					<li><a href="#0">Milan</a></li>
					<li><a href="#0">Paris</a></li>
				</ul>
			</li> <!-- item-has-children -->
		</ul> <!-- cd-navigation -->

		<ul class="cd-navigation cd-single-item-wrapper">
			<li><a href="#0">Tour</a></li>
			<li><a href="#0">Login</a></li>
			<li><a href="#0">Register</a></li>
			<li><a href="#0">Pricing</a></li>
			<li><a href="#0">Support</a></li>
		</ul> <!-- cd-single-item-wrapper -->

		<ul class="cd-navigation cd-single-item-wrapper">
			<li><a class="current" href="#0">Journal</a></li>
			<li><a href="#0">FAQ</a></li>
			<li><a href="#0">Terms &amp; Conditions</a></li>
			<li><a href="#0">Careers</a></li>
			<li><a href="#0">Students</a></li>
		</ul> <!-- cd-single-item-wrapper -->

		<div class="cd-navigation socials">
			<a class="cd-twitter cd-img-replace" href="#0">Twitter</a>
			<a class="cd-github cd-img-replace" href="#0">Git Hub</a>
			<a class="cd-facebook cd-img-replace" href="#0">Facebook</a>
			<a class="cd-google cd-img-replace" href="#0">Google Plus</a>
		</div> <!-- socials -->
	</nav>
	<!-- 모바일 메뉴 끝-->
	<script src="<c:url value='/js/egovframework/phcf/main/main.js'/>"></script> <!-- Resource jQuery -->
	<!--헤더끝-->
	
	<div id="main" class="scroll-container cd-main-content">
		
		<!--list 메인-->
		<jsp:include page="../../../../template/${boardMasterVO.tmplatId}/list.jsp" />
		
		<div class="bottom">
			<c:import url="${pageContext.request.contextPath}/EgovBottom.do" />
		</div>
	</div>
	
	
  <script type="text/javascript">
   $(document).on('ready', function() {

	App.accessibleMenu();
	App.cancelAccessibleMenu();

		$('.slider').slick({
			dots: true,
			infinite: true,
			speed: 500,
			fade: true,
			cssEase: 'linear',
			autoplay: true,
			arrows: false,
			autoplaySpeed: 3000
		});

		$('.slider2').slick({
			lazyLoad: 'ondemand',
			slidesToShow: 4,
			slidesToScroll: 1,
			autoplay: true,
			autoplaySpeed: 3000,
			responsive: [ 
				{ /* 반응형웹*/
					breakpoint: 1300, /* 기준화면사이즈 */ 
					settings: {slidesToShow:3 } /* 사이즈에 적용될 설정 */ 
				},
				{ /* 반응형웹*/ 
					
						breakpoint: 1000, /* 기준화면사이즈 */ 
				 settings: {slidesToShow:2 } /* 사이즈에 적용될 설정 */ 
				} ,
				{ /* 반응형	웹*/ breakpoint: 670, /* 기준화면사이즈 */ 
				 settings: {slidesToShow:1 } /* 사이즈에 적용될 설정 */ 
				} 
			]
		});
	   $('.box5').slick({
			dots: true,
			infinite: true,
			speed: 500,
			fade: true,
			cssEase: 'linear',
			autoplay: true,
			arrows: false,
			autoplaySpeed: 3000
		});		
    });
		
		
		var App = {

	accessibleMenu: function(){
		$menu = $('.access-menu');
		$menuItem = $menu.find('> li > a');

		$subMenu = $('.access-submenu');
		$subMenuItem = $subMenu.find('> li > a');
		$submenuLastItem = $subMenu.find('> li:last-child > a');

		$menuItem.bind({
			focus: function(){
				$subMenu.removeClass('is-show');
				if($(this).next($subMenu)){
					$(this).next($subMenu).addClass('is-show');
				}
			},

			blur: function(){
				$subMenu.removeClass('is-show');
			}
		});

		$subMenuItem.bind({
			focus: function(){
				$(this).parent().parent().addClass('is-show');
			}
		});

		$submenuLastItem.bind({
			blur: function(){
				$subMenu.removeClass('is-show');
			}
		});
	},

	cancelAccessibleMenu: function(){
		$(document).click(function(){
			if($subMenu.hasClass('is-show')){
				$subMenu.removeClass('is-show');
			}
		});
	}

};
		
	</script>

</body>
</html>