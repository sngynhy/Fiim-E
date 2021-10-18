<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>    
<!DOCTYPE html>
<html lang="zxx">
<script type="text/javascript">
	function logout(){
		if(confirm("정말로 로그아웃 하시겠습니까?")==true){
			location.href="Logout.do";		
		}
		else{
			return;
		}
	}
</script>
<head>
<meta charset="UTF-8">
<meta name="description" content="Anime Template">
<meta name="keywords" content="Anime, unica, creative, html">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Film-E | Categories</title>
<link rel="Film-E icon" href="img/main.ico">

<!-- Google Font -->
<link
	href="https://fonts.googleapis.com/css2?family=Oswald:wght@300;400;500;600;700&display=swap"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css2?family=Mulish:wght@300;400;500;600;700;800;900&display=swap"
	rel="stylesheet">

<!-- Css Styles -->
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="css/font-awesome.min.css" type="text/css">
<link rel="stylesheet" href="css/elegant-icons.css" type="text/css">
<link rel="stylesheet" href="css/plyr.css" type="text/css">
<link rel="stylesheet" href="css/nice-select.css" type="text/css">
<link rel="stylesheet" href="css/owl.carousel.min.css" type="text/css">
<link rel="stylesheet" href="css/slicknav.min.css" type="text/css">
<link rel="stylesheet" href="css/style.css" type="text/css">
</head>

<body>
	<!-- Page Preloder -->
	<div id="preloder">
		<div class="loader"></div>
	</div>

	<!-- Header Section Begin -->
	<header class="header">
		<div class="container">
			<mytag:categories/>
			<div id="mobile-menu-wrap"></div>
		</div>
	</header>
	<!-- Header End -->

	<!-- Hero Section Begin -->
	<!-- Hero Section End -->

	<!-- Product Section Begin -->
	<section class="product spad">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<div class="trending__product">
						<div class="row">
							<div class="col-lg-12 col-md-8 col-sm-8">
							<c:if test="${mtype == null || mtype == '' }"> <!-- 성공 -->
							<div class="section-title">
									<h4>전체 영화</h4>
								</div>
							</c:if>
							<c:if test="${mtype != null && mtype != '' }">
							<div class="section-title">
									<h4>${mtype}</h4>
								</div>
							</c:if>	
							</div>
						</div>
						<div class="row">
							<c:if test="${empty datas}">
							<h2 style="color:#FFFFFF;">&nbsp&nbsp검색결과없음</h2>
							</c:if>
							<c:forEach var="v" items="${datas}"> <!-- 컨트롤이랑 맞추기 -->
							
							<div class="col-lg-3 col-md-6 col-sm-6">
								<div class="product__item">
									<div class="product__item__pic set-bg" data-setbg="${v.filename}" style="cursor:pointer;" onclick="location.href='RselectAll.do?mpk=${v.mpk}'">
									</div>
									<div class="product__item__text">
										<h5>
											<a href="RselectAll.do?mpk=${v.mpk}">${v.title}</a>
										</h5>
									</div>
								</div>
							</div>
							</c:forEach>
						</div>
					</div>
					<div class="product__pagination">
						<a href="Categories.do?page=1&mtype=${mtype}&search=${search}"><i class="fa fa-angle-double-left"></i></a><!-- 처음으로 -->
						<c:if test="${paging.startPage != 1 }">
						<a href="Categories.do?page=${(page-1)-(page-1)%paging.perPageSet - paging.perPageSet + 1}&mtype=${mtype}&search=${search}"><i class="fa fa-angle-left"></i></a><!-- 이전페이지 -->
						</c:if>
						<c:forEach begin="${paging.startPage}" end="${paging.endPage}" var="p">
						<c:if test="${paging.curPage == p}">
						<a href="Categories.do?page=${p}&mtype=${mtype}&search=${search}" class="current-page">${p}</a>
						</c:if>
						<c:if test="${paging.curPage != p}">
						<a href="Categories.do?page=${p}&mtype=${mtype}&search=${search}" >${p}</a>
						</c:if>
						</c:forEach>
                        <c:if test="${paging.endPage != paging.lastPage}">
                        <a href="Categories.do?page=${(page-1)-(page-1)%paging.perPageSet + paging.perPageSet + 1}&mtype=${mtype}&search=${search}"><i class="fa fa-angle-right"></i></a><!-- 다음페이지 -->
                        </c:if>
                        <a href="Categories.do?page=${paging.lastPage}&mtype=${mtype}&search=${search}"><i class="fa fa-angle-double-right"></i></a><!-- 마지막으로 -->
                    </div>
				</div>
			</div>
		</div>
	</section>
	<!-- Product Section End -->

	<!-- Footer Section Begin -->
	<footer class="footer">
		<div class="page-up">
			<a href="#" id="scrollToTopButton"><span class="arrow_carrot-up"></span></a>
		</div>
		<div class="container">
			<div class="row">
				<div class="col-lg-3">
					<div class="footer__logo">
						<a href="Main.do"><img src="img/logo.png" alt="logo확인"></a>
					</div>
				</div>
				<div class="col-lg-3">
					<p>
						<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
						Copyright &copy;
						<script>document.write(new Date().getFullYear());</script>
						All rights reserved | This template is made with <i
							class="fa fa-heart" aria-hidden="true"></i> by <a
							href="https://colorlib.com" target="_blank">Colorlib</a>
						<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
					</p>

				</div>
			</div>
		</div>
	</footer>
	<!-- Footer Section End -->

	<!-- Search model Begin -->
	<div class="search-model">
		<div class="h-100 d-flex align-items-center justify-content-center">
			<div class="search-close-switch">
				<i class="icon_close"></i>
			</div>
			<form class="search-model-form" action="Categories.do">
				<input type="hidden" name="mtype" value="${mtype}">
				<input type="text" id="search-input" name="search" placeholder="영화 제목 검색.....">
			</form>
		</div>
	</div>
	<!-- Search model end -->

	<!-- Js Plugins -->
	<script src="js/jquery-3.3.1.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/player.js"></script>
	<script src="js/jquery.nice-select.min.js"></script>
	<script src="js/mixitup.min.js"></script>
	<script src="js/jquery.slicknav.js"></script>
	<script src="js/owl.carousel.min.js"></script>
	<script src="js/main.js"></script>


</body>

</html>