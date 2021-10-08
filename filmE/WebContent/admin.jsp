<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zxx">
<script type="text/javascript">
	function Minsert(){
		if(confirm("Are you sure you want to insert movie?") == true ){
			document.insert.submit();
		}
		else{
			return;
		}
	}
	function Mupdate(){
		if(confirm("Are you sure you want to edit movie?") == true ){
			document.movie.submit();
		}
		else{
			return;
		}
	}
	function Mdelete(){
		return confirm("Are you sure you want to delete movie?");
	}
</script>

<head>
<meta charset="UTF-8">
<meta name="description" content="Anime Template">
<meta name="keywords" content="Anime, unica, creative, html">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Film-E | Admin</title>

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
			<div class="row">
				<div class="col-lg-2">
					<div class="header__logo">
						<a href="Main.do"> <img src="img/logo.png" alt="logo확인">
						</a>
					</div>
				</div>
				<div class="col-lg-8">
					<div class="header__nav">
						<nav class="header__menu mobile-menu">
							<ul>
							<li ><a href="Main.do">Homepage</a></li>
								<li class="active"><a href="Adminlist.do">AdminList</a></li>
								<li><a href="project.jsp">Project</a></li>
								<li><a href="aboutus.jsp">AboutUs</a></li>
							</ul>
						</nav>
					</div>
				</div>
				<div class="col-lg-1">
					<div class="header__right">
						<a href="#" class="search-switch"><span class="icon_search"></span></a>
					</div>
				</div>
				<mytag:log />
			</div>
			<div id="mobile-menu-wrap"></div>
		</div>
	</header>
	<!-- Header End -->

	<!-- Breadcrumb Begin -->
	<!-- Breadcrumb End -->

	<!-- Anime Section Begin -->
	<section class="anime-details spad">
		<div class="container">
			<div class="anime__details__content">
				<br>
				<c:choose>
					<c:when test="${datas.title != null && datas.title != '' }">
						<div class="col-lg-12 col-md-8 col-sm-8">
							<div class="section-title">
								<h4>영화 수정 및 삭제</h4>
							</div>
						</div>
						<div class="row">
							<form action="Mupdate.do?mpk=${datas.mpk}" method="post" name="movie" enctype="multipart/form-data">
<!-- 								<div class="col-lg-3" style="float: left;"> -->
								<div class="row" style="margin-left:15px">
								<div class="col-lg-5">
									<div class="anime__details__pic set-bg" data-setbg="${datas.filename}"></div>
								</div>
<!-- 								<div class="col-lg-8" style="margin-left: 300px;"> -->
								<div class="col-lg-5">
									<div class="anime__details__text">
										<div class="anime__details__title">
											<input type="file" name="filename" value="${datas.filename}">
											<h3>
												<input type="text" name="title" value="${datas.title}">
											</h3>
											<select name="mtype">
												<option selected value="액션">Action</option>
												<option value="애니메이션">Animation</option>
												<option value="멜로/로맨스">Melo/Romance</option>
												<option value="드라마">Drama</option>
												<option value="다큐멘터리">Documentary</option>
												<option value="ETC">ETC</option>
											</select>
											<h3>
												<input type="date" name="mdate" value="${datas.mdate}">
											</h3>
										</div>
										<textarea name="content" style="width: 400px; height: 100px; resize: none;">${datas.content}</textarea>
									</div>
									<div class="blog__details__tags">
										<button type="button" class="site-btn" onclick="Mupdate()" style="
   												background: rgba(255, 255, 255, 0.1);
											    margin-bottom: 10px;
											    padding: 6px 15px;
											    font-size: 12px;
											    color: #b7b7b7;
											    letter-spacing: 2px;
											    text-transform: uppercase;">
										수정</button>
										<a href="Mdelete.do?mpk=${datas.mpk}" onclick="return Mdelete()">삭제</a>
									</div>
								</div>
								</div>
							</form>
						</div>
					</c:when>
					<c:when test="${title == null || title == '' }">
						<div class="col-lg-12 col-md-8 col-sm-8">
							<div class="section-title">
								<h4>영화 추가</h4>
							</div>
						</div>
						<div class="row">
							<form class="col-lg-12" action="Minsert.do" method="post" name="insert" enctype="multipart/form-data">
								<div class="col-lg-3" style="float: left;">
									<div class="anime__details__pic set-bg" data-setbg="${datas.filename}">
									</div>
								</div>
								<div class="col-lg-8" style="margin-left: 300px;">
									<div class="anime__details__text">
										<div class="anime__details__title">
											<input type="file" name="filename" value="포스터등록">
											<h3>
												<input type="text" name="title" placeholder="Title">
											</h3>
											<select name="mtype">
												<option selected value="액션">Action</option>
												<option value="애니메이션">Animation</option>
												<option value="멜로/로맨스">Melo/Romance</option>
												<option value="드라마">Drama</option>
												<option value="다큐멘터리">Documentary</option>
												<option value="ETC">ETC</option>
											</select>
											<h3>
												<input type="date" name="mdate" placeholder="date">
											</h3>
										</div>
										<textarea style="width: 400px; height: 100px; resize: none;" name="content" placeholder="Content"></textarea>
									</div>
									<button type="button" class="site-btn" onclick="Minsert()" style="
										    background: rgba(255, 255, 255, 0.1);
										    margin-bottom: 10px;
										    padding: 6px 15px;
										    font-size: 12px;
										    color: #b7b7b7;
										    letter-spacing: 2px;
										    text-transform: uppercase;">
									 등록</button>
								</div>
							</form>
						</div>
					</c:when>
				</c:choose>
			</div>
		</div>
	</section>
	<!-- Anime Section End -->

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
						<script>
							document.write(new Date().getFullYear());
						</script>
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
			<form class="search-model-form" action="Adminlist.do">
				<input type="text" id="search-input" name="search"
					placeholder="영화 제목 검색.....">
			</form>
		</div>
	</div>
	<!-- Search model end -->

	<!-- Js Plugins -->
	<script src="js/jquery-3.3.1.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/player.js"></script>
	<script src="js/mixitup.min.js"></script>
	<script src="js/jquery.slicknav.js"></script>
	<script src="js/owl.carousel.min.js"></script>
	<script src="js/main.js"></script>

</body>

</html>