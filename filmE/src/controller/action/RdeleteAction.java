package controller.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.page.PageDAO;
import common.page.PageVO;
import model.movie.MovieDAO;
import model.movie.MovieVO;
import model.review.ReviewDAO;
import model.review.ReviewVO;

public class RdeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ActionForward forward = null; // output
		
		ReviewDAO rDAO = new ReviewDAO();
		ReviewVO rVO = new ReviewVO();
		
		MovieVO mVO = new MovieVO();
		MovieDAO mDAO = new MovieDAO();
		
		PageDAO pDAO = new PageDAO(); //
		PageVO pVO = new PageVO(); //
		
		int page = 1; //
		String ppage = request.getParameter("page");
		if(ppage != null) {
			page = Integer.parseInt(ppage);
		} //
		
		rVO.setRpk(Integer.parseInt(request.getParameter("rpk")));
		
		pVO.setCurPage(page);	//	���� ������	
		pVO.setPerPage(8);		//	������ �Խù� ��
		pVO.setPerPageSet(3);	//	������ ��
		
		String mtype = request.getParameter("mtype");
		String search = request.getParameter("search");
		
		mVO = mDAO.m_selectDB_one(mVO);
		
		pVO = pDAO.paging(pVO,mVO, search, mtype, "review");
		
		/*request.setAttribute("paging", pVO); 
		request.setAttribute("page", page); 
		*/
		
		if (rDAO.r_deleteDB(rVO)) {
			forward = new ActionForward();
			forward.setPath("RselectAll.do?mpk=" + request.getParameter("mpk")); 
			forward.setRedirect(true);
		} else {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('���� ���� ����!');history.go(-1)</script>");
		}
		
		return forward;
	}
}
