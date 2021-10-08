※실행시 주의사항※
크롤링시 JDBC USER/PW 꼭 확인
=============================================
1007 수정사항
View
1. Categories.jsp 페이징 mtype,search추가
2. adminlist.jsp 상단메뉴 categories > adminlist변경 및 연결 / 페이징 search추가
3. admin.jsp 상단메뉴 categories > adminlist변경 및 연결 
+4. mypage.jsp 이미지사이에 login > mypage로 변경 / update,del 버튼 confirm 기능 추가 
+5. review.jsp form에 name달음/ insert,del버튼 confirm 기능 추가
+6. admin.jsp 등록/수정/삭제 confirm기능추가 + 버튼 색상 변경
7. style.css hover 색 추가

controller
1. categoryAction request.setAttribute("search") 추가


=============================================
1006 수정사항
++추가로 수정해야할 사항
이미지 미리보기
이메일 유효성 검사
파일입력 수정 삭제 required 하기

Model
1. MovieDAO 수정(인서트가 2번째부턴 안올라가지는 상태였음)
2. MovieDAO insertSQL과updateSQL수정
3. sql movie테이블 mdate 타입 date > varchar2로 변경
+4. reviewDAO rselectAll 쿼리문 수정, reviewVO rdate타입 수정 date -> String
+5. pageDAO, movieDAO 쿼리문 수정
Controller
1. MupdateAction 수정
+2. 진한님 action 주석추가
View
1. 전체 페이지에 상단부분 페이지명 표시
2. admin.jsp/adminlist.jsp 상단 카테고리 a태그 막아둠(임시)
+3. 각 페이지마다 project.do -> project.jsp로 변경
+4. review.jsp 리뷰 삭제버튼 위치 변경 및 p태그 자동줄바꿈 추가
+5. review.jsp 글자 재조정
+6. project.jsp 추가 및 상단메뉴 이용가능 
+7. login.jsp id/pw required추가
+8. categories.jsp 검색결과없음 추가
==========================================
1005 수정사항
현재 파일 수정 안됨(권수님 체크사항)
해야할일 
admin.jsp css수정할것
------------------------------------------
Control
1. Contorller에 unused 코드 삭제
2. C_deleteAction 회원탈퇴 성공시 session.invalidate추가
3. FrontController /mypage.do > c_selectOne.do로 변경\
4. c_selectOneAction에 request.setAttribute("data", cDAO.c_selectDB_one(cVO));
   코드추가
5. C_updateAction에 cVO.setId(request.getParameter("id"));추가
6. C_updateAction에 forward.setPath("c_selectOne.do");와forward.setRedirect(true);수정
7. 클래스명, frontcontroller 액션명 수정
-------------------------------------------
View
1. review.jsp 미로그인시 리뷰 작성 불가
2. mypage.jsp 제작(c_update/c_delete)
3. categories에 a태그 R_selectAllAction 수정
4. 이미지 사이즈 css수정
5. admin.jsp 등록/수정/삭제 변경
-------------------------------------------
Model
1. ClientDAO > c_updateDB에 pstmt=conn.prepareStatement(update);로 수정
2. PageDAO, PageVO, MovieDAO, MovieVO 로깅, 주석 추가
=====================================================================
현재 review.jsp 인서트가 되거나/혹은 된다음 새로고침시 r_insert가 안풀리는 버그 존재
=========================================
1004 수정사항
1. 페이징 처리 해당 페이지 동그라미 표시기능
2. 크롤링 추가
3. 메뉴마다 빨간색 변경
4. review.jsp 인서트 추가 / 페이징 수정
5. 컨트롤러 필요없는것 주석처리


-----------------------------------------
model_김권수_1004
MovieDAO selectOne
유지보수를 위해 mpk사용할때 장르 분류를 String배열 을 통해
for문으로 구현
isMpk를 통해 기타 장르 분류 및 기타장르 etc로변환
data = new MovieVO();-스코프 문제로 위치 변경
data.setFilename(isHttp);

PageDAO
public PageVO paging(PageVO vo, MovieVO mvo, String mtype, String search, String table) { - mVO input 추가

model_이자경_1004
sql insert 변경
-"insert into review (rpk, cmt, id, mpk, rdate) values(nvl((select max(rpk) from review),0)+1, ?, ?, ?, sysdate)"; // 리뷰 등록


-----------------------------------------
controller
R_selectAllAction
setEnd, setStart 삭제
pVO = pDAO.paging(pVO,mVO ,mtype, search,"review"); - input 추가
request.setAttribute("mdata", mDAO.m_selectDB_one(mVO));	- mdata parameter set

r_insert하고 redirect로 풀기



