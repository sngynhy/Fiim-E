<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>   
<!DOCTYPE html>
<html lang="zxx">

<head>
<meta charset="UTF-8">
<meta name="description" content="Anime Template">
<meta name="keywords" content="Anime, unica, creative, html">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>project 소개 페이지</title>

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
								<li><a href="Main.do">Homepage</a></li>
								<li><a href="Categories.do">Categories <span
										class="arrow_carrot-down"></span></a>
									<ul class="dropdown">
										<!-- 한글로 넘길수도 있음 -->
										<li><a href="Categories.do?mtype=액션">Action</a></li>
										<li><a href="Categories.do?mtype=애니메이션">Animation</a></li>
										<li><a href="Categories.do?mtype=멜로/로멘스">Melo/Romance</a></li>
										<!-- 컨트롤과 맞추기 -->
										<li><a href="Categories.do?mtype=드라마">Drama</a></li>
										<li><a href="Categories.do?mtype=다큐멘터리">Documentary</a></li>
										<li><a href="Categories.do?mtype=ETC">ETC</a></li>
									</ul></li>
								<li class="active"><a href="project.jsp">Project</a></li>
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

   <!-- Blog Details Section Begin -->
   <section class="blog-details spad">
      <div class="container">
         <div class="row d-flex justify-content-center">
            <div class="col-lg-8">
               <div class="blog__details__title">
                  <h6>
                     <span> October 18, 2021</span>
                  </h6>
                  <h2>Film-E</h2>
               </div>
            </div>
            <div class="col-lg-12">
               <div class="blog__details__pic">
                  <img class="mainLogo" src="img/blog/details/slate.gif" alt="슬레이트 움짤">
               </div>
            </div>
            <div class="blog__details__item__text">
               <img class="project" style="opacity: 0; height:0" src="img/blog/details/FLogo.png" alt="로고 의미를 담은 이미지">
               <p class="project" style="opacity: 0;height:0">
                  Feel Me: 나를 느끼다. <br> Fill Me: 나를 채우다. <br> 영화를 통해 나를
                  채우고 알아간다는 의미를 담은 브랜드명.
               </p>
            </div>
            <div class="col-lg-12" >
               <div class="blog__details__pic">
                  <img class="project" style="opacity: 0;height:0" src="img/blog/details/lala.gif" alt="라라랜드 움짤">
               </div>
            </div>
            <div class="col-lg-8">
               <div class="blog__details__content">
                  <div class="blog__details__text">
                     <p class="project" style="opacity: 0;height:0">
                        ▸&nbsp;코로나가 장기화되며, OTT 플랫폼 서비스도 점차 늘어날 것으로 예상. <br>
                        &nbsp;&nbsp;&nbsp; → 다양한 영화 정보를 얻기 위한 사용자들의 니즈가 늘어나게 될 것으로 예측
                     </p>
                     <br>
                     <p class="project" style="opacity: 0;height:0">
                        ▸&nbsp;해외에는 로튼토마토, IMDB 같은 영화 리뷰를 보여주는 전문적인 사이트가 존재. <br>
                        &nbsp;&nbsp;&nbsp;→ 국내 : 전문 영화 리뷰 플랫폼의 부재 <br>
                        &nbsp;&nbsp;&nbsp;→ Film-E (영화리뷰) 프로그램 기획
                     </p>
                  </div>


               </div>
            </div>

         </div>
      </div>
   </section>
   <!-- Blog Details Section End -->

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
   <script src="js/jquery.nice-select.min.js"></script>
   <script src="js/mixitup.min.js"></script>
   <script src="js/jquery.slicknav.js"></script>
   <script src="js/owl.carousel.min.js"></script>
   <script src="js/main.js"></script>
   <script type="text/javascript">
   $(document).ready(function() {
      

            
      $(".mainLogo").delay(3000).animate({            /*없어지면서 프로젝트 페이지  보여주는 애니메이션*/
            opacity : 0,
            top : '0px',
            height : '0px',
            width : '0px'
         }, 1000);
               
   
      $(".project").delay(3000).animate({            /* 프로젝트 페이지  나타나는 애니메이션*/
            opacity : 100,
            height : '60%'
         }, 0);         

   }); 
   
   </script>

</body>

</html>