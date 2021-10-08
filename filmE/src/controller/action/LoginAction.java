package controller.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.client.ClientDAO;
import model.client.ClientVO;

public class LoginAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ActionForward forward = null;
				
		ClientDAO cDAO = new ClientDAO();
		ClientVO cVO = new ClientVO();
		cVO.setId(request.getParameter("id")); // �α��� id
		cVO.setPw(request.getParameter("pw")); // �α��� pw
		
		if (cDAO.login(cVO) != null) { // �α��� ����
			HttpSession session = request.getSession();
			session.setAttribute("sessionID", cVO.getId());
			
			forward = new ActionForward();
			if (cVO.getId().equals("admin")) { // ������ �α��� ó��
				forward.setPath("Adminlist.do");
				forward.setRedirect(true);
			} else {
				forward.setPath("Main.do");
				forward.setRedirect(true);
			}
			
			

		} else { // �α��� ����
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('�α��ο� �����Ͽ����ϴ�. ���̵� Ȥ�� ��й�ȣ Ȯ�� �� �ٽ� �Է��ϼ���.');history.go(-1)</script>");
		}
		
		return forward;
	}
}
