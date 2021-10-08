package controller.action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.movie.MovieDAO;
import model.movie.MovieVO;

public class AdminAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		ActionForward forward = new ActionForward();
		
		MovieDAO mDAO = new MovieDAO();
		MovieVO mVO = new MovieVO();
		
		mVO.setMpk(request.getParameter("mpk")); // m_selectDB_one�� �ϱ����ؼ� �޾ƿ�
		System.out.println(mVO+"mVOȮ��");
		
		
		request.setAttribute("datas", mDAO.m_selectDB_one(mVO)); // ���� ����Ʈ ���������� �Խù��� ������ �� �Խù�db�� datas�� ��� v�� ������
		
		forward.setRedirect(false);
		forward.setPath("admin.jsp");
		
		return forward;
		// �Խù� ���� �������� �̵�
	}

}
