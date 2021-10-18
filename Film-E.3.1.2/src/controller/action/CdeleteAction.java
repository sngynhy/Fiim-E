package controller.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.client.ClientDAO;
import model.client.ClientVO;

public class CdeleteAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		ActionForward forward = null; // output
		
		ClientDAO cDAO = new ClientDAO();
		ClientVO cVO = new ClientVO();
		
		HttpSession session = request.getSession();
		cVO.setId((String)session.getAttribute("sessionID"));
		
		try {
			if (cDAO.c_deleteDB(cVO)) {
				session.invalidate();
				forward = new ActionForward();
				forward.setPath("Main.do");
				forward.setRedirect(true);
			} else {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>alert('ȸ�� Ż�� ����!');history.go(-1)</script>");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return forward;
	}
}