package controller.common;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.action.ActionForward;
import controller.action.AdminAction;
import controller.action.AdminlistAction;
import controller.action.CdeleteAction;
import controller.action.CinsertAction;
import controller.action.CseletOneAciton;
import controller.action.CupdateAction;
import controller.action.CategoriesAction;
import controller.action.CheckIDAction;
import controller.action.LoginAction;
import controller.action.LogoutAction;
import controller.action.MdeleteAction;
import controller.action.MinsertAction;
import controller.action.MupdateAction;
import controller.action.MainAction;
import controller.action.RdeleteAction;
import controller.action.RinsertAction;
import controller.action.RselectAllAction;

//"���" Ŭ���̾�Ʈ�� ��û�� ���� �������� ������ �ǰ�, �̰����� ��û�� ���� control.jsp�� �̵��ϵ��� ��

/*@WebServlet("*.do")*/ // �����ϰ� �� ��ĵ� ����
@WebServlet("/FrontController")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public FrontController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
	
	private void doAction(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		/* 1. Ŭ���̾�Ʈ�� "��û"�� �м��ϴ� �Լ� */
		String uri = request.getRequestURI(); // uri : /FrontController/main.do
		String cp = request.getContextPath(); // cp :/FrontController
		String action = uri.substring(cp.length()); // action : /main/do - ���� �׼ǰ��� ���� -> ���� ������ ����
		
		/* 2. control�� �����ϴ� ���� */
		ActionForward forward = null;
		
		System.out.println(action);
		
		// ȸ��, ���� ���� �׼� ó��
		
		if (action.equals("/Main.do")) {
			forward = new MainAction().execute(request, response);
		} else if (action.equals("/Login.do")) { // �α���
			System.out.println(action);
			forward = new LoginAction().execute(request, response); // ������������ �̵�
		} else if (action.equals("/Logout.do")) { // �α׾ƿ�
			forward = new LogoutAction().execute(request, response); //  ������������ �̵�
		} else if (action.equals("/Cinsert.do")) { // ȸ�� ���� 
			forward = new CinsertAction().execute(request, response); // �α��� �������� �̵�
//			String pattern = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
//			String email = request.getParameter("email");
//			System.out.println("email"+email);
//			if(Pattern.matches(pattern, email)) {
//			    forward = new CinsertAction().execute(request, response); // �α��� �������� �̵�
//			} else {
//				PrintWriter out = response.getWriter();
//			    out.println("<script>alert('�ùٸ� �̸��� ������ �ƴմϴ�.');history.go(-1)</script>");
//			}
		} else if (action.equals("/CheckID.do")) { // �ߺ� ���̵� üũ
			forward = new CheckIDAction().execute(request, response);
		} else if (action.equals("/CselectOne.do")) { // ȸ�� ���� ��ȸ
			forward = new CseletOneAciton().execute(request, response); // ������������ �̵�
		} else if (action.equals("/Cdelete.do")) { // ȸ�� Ż��
				forward = new CdeleteAction().execute(request, response);
		} else if (action.equals("/Cupdate.do")) { // ȸ�� ���� ����
			forward = new CupdateAction().execute(request, response); // ������������ �̵�
		} else if (action.equals("/RselectAll.do")) { // ��� ���� ��ȸ
			forward = new RselectAllAction().execute(request, response); // ������������ �̵�
		} else if (action.equals("/Rinsert.do")) { // ���� ���
			forward = new RinsertAction().execute(request, response); // ������������ �̵�
		} else if (action.equals("/Rdelete.do")) { // ���� ����
			forward = new RdeleteAction().execute(request, response); // ������������ �̵�
		} else if (action.equals("/Admin.do")) {	// ������ �̵�
			forward = new AdminAction().execute(request, response); // �Խù� ���� ������ (����) �̵�
		} else if (action.equals("/Adminlist.do")) { // �˻� �� selectAll ���
			forward = new AdminlistAction().execute(request, response); // ���� ������ �̵�
		} else if (action.equals("/Categories.do")) { // �˻� �� SelectAll ��� 
			forward = new CategoriesAction().execute(request, response);	// ī�װ� ������ �̵�
		} else if (action.equals("/Mdelete.do")) {	 // �Խù� ����
			forward = new MdeleteAction().execute(request, response); // ����: ���� ������ ,����: �Խù� ���� ������(����) �̵� 
		} else if (action.equals("/Minsert.do")) {  // �Խù� ��� 
			forward = new MinsertAction().execute(request, response); // ����: ���� ������ ,����: �Խù� ���� ������(����) �̵�
		} else if (action.equals("/Mupdate.do")) {  // �Խù� ����
			forward = new MupdateAction().execute(request, response); // ����: ���� ������ ,����: �Խù� ���� ������(����) �̵�
		}
		else { // ���� �߸��� action���� ��� ��, null�� ��� ���� �������� ����
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("/error/errer404.jsp");
		}
		
		/* 3. Ŭ���̾�Ʈ���� ��� ȭ�� ��� */
		if (forward != null) { // ���� forward == null�̸� ���� �׼�ó���� ����� ���� ���� ��!
			if (forward.isRedirect()) { // redirect ���
				response.sendRedirect(forward.getPath());
			} else { // forward ��� - ��û ����� �����Ͽ� ������ �̵�
				System.out.println("!!!!");
				// Dispatcher
				// : Ŭ���̾�Ʈ�κ��� ���ʿ� ���� ��û�� JSP/Servlet ������ ���ϴ� �ڿ����� ��û�� �ѱ�� ������ �����ϰų�,
				//   Ư�� �ڿ��� ó���� ��û�ϰ� ó�� ����� ������ ����� �����ϴ� Ŭ����
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath()); // ���� ���
				dispatcher.forward(request, response);
			}
		}
	}
}
