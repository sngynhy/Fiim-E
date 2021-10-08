<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>    
<!DOCTYPE html>
<html lang="zxx">
<script type="text/javascript">
	function Cupdate(){
		if(confirm("Are you sure you want to edit your account?") == true ){
			document.form1.submit();
		}
		else{
			return;
		}
	}
	function Cdelete(){
		return confirm("Are you sure you want to delete your account?");
	}
</script>
<head>
    <meta charset="UTF-8">
    <meta name="description" content="Anime Template">
    <meta name="keywords" content="Anime, unica, creative, html">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Film-E | MyPage</title>

    <!-- Google Font -->
    <link href="https://fonts.googleapis.com/css2?family=Oswald:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Mulish:wght@300;400;500;600;700;800;900&display=swap"
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
                        <a href="Main.do">
                            <img src="img/logo.png" alt="logo확인">
                        </a>
                    </div>
                </div>
                <div class="col-lg-8">
                    <div class="header__nav">
                        <nav class="header__menu mobile-menu">
                            <ul>
                                <li><a href="Main.do">Homepage</a></li>
                                <li><a href="Categories.do">Categories <span class="arrow_carrot-down"></span></a>
                                    <ul class="dropdown">
                                        <li><a href="Categories.do?mtype=액션">Action</a></li>
                                        <li><a href="Categories.do?mtype=애니메이션">Animation</a></li>
                                        <li><a href="Categories.do?mtype=멜로/로맨스">Melo/Romance</a></li><!-- 컨트롤과 맞추기 -->
                                        <li><a href="Categories.do?mtype=드라마">Drama</a></li>
                                        <li><a href="Categories.do?mtype=다큐멘터리">Documentary</a></li>
                                        <li><a href="Categories.do?mtype=ETC">ETC</a></li>
                                    </ul>
                                </li>
                               <li><a href="project.jsp">Project</a></li>
                               <li><a href="aboutus.jsp">AboutUs</a></li>
                            </ul>
                        </nav>
                    </div>
                </div>
               <div class="col-lg-1">
					<div class="header__right">
						<a href="#" class="search-switch"><span class="icon_search" ></span></a>
					</div>
				</div>
				<mytag:log/>
            </div>
            <div id="mobile-menu-wrap"></div>
        </div>
    </header>
    <!-- Header End -->

    <!-- Normal Breadcrumb Begin -->
    <section class="normal-breadcrumb set-bg" data-setbg="img/login_img.jpg">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <div class="normal__breadcrumb__text">
                        <h2>MyPage</h2>
                        <p>Welcome to the Film-E.</p>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- Normal Breadcrumb End -->

    <!-- Login Section Begin -->
    <section class="login spad">
        <div class="container">
            <div class="row">
                <div class="col-lg-6">
                    <div class="login__form">
                        <h3>Update Account</h3>
                        <form action="Cupdate.do" method="post" name="form1">
                        	<div class="input__item">
                                <input type="text" name="id" value="${data.id}" readonly>
                                <span class="icon_profile"></span>
                            </div>
                            <div class="input__item">
                                <input type="text" name="pw" value="${data.pw}">
                                <span class="icon_lock"></span>
                            </div>
                             <div class="input__item">
                                <input type="text" name="email" id="email" value="${data.email}">
                                <span class="icon_mail"></span>
                            </div>
                            <button type="button" class="site-btn" onclick="Cupdate()">Update Account</button>
                        </form>
                    </div>
                </div>
                <div class="col-lg-6">
                    <div class="login__register">
                        <h3>Delete Account</h3>
                        <a href="Cdelete.do" class="primary-btn" onclick="return Cdelete()">Delete Your Account</a>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- Login Section End -->

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
                    <p><!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                      Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | This template is made with <i class="fa fa-heart" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank">Colorlib</a>
                      <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. --></p>

                  </div>
              </div>
          </div>
      </footer>
      <!-- Footer Section End -->

      <!-- Search model Begin -->
      <div class="search-model">
        <div class="h-100 d-flex align-items-center justify-content-center">
            <div class="search-close-switch"><i class="icon_close"></i></div>
           <form class="search-model-form" action="Categories.do">
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