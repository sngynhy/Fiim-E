package controller.action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.page.PageDAO;
import common.page.PageVO;
import model.movie.MovieDAO;

public class MainAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ActionForward forward = new ActionForward(); // output
		
		MovieDAO mDAO = new MovieDAO();
	
		PageVO pVO = new PageVO();
		PageDAO pDAO = new PageDAO();
		
		int page = 1;
		String ppage = request.getParameter("page");
		if(ppage != null) {
			page = Integer.parseInt(ppage);
		}
		
		pVO.setCurPage(page);	//	���� ������	
		pVO.setPerPage(12);		//	������ �Խù� ��
		pVO.setPerPageSet(5);	//	������ ��
		

		String mtype = request.getParameter("mtype");
		String search = request.getParameter("search");
		
		pVO = pDAO.paging(pVO, null ,mtype,search,"movie");
		request.setAttribute("datas", mDAO.m_selectDB_all(mtype, search, pVO));
		request.setAttribute("paging", pVO);
		request.setAttribute("page", page);
		
		forward.setRedirect(false);
		forward.setPath("main.jsp");
		
		
		return forward;
		// ����������, SelectAll�� �˻��� ���ÿ� ��
	}
}