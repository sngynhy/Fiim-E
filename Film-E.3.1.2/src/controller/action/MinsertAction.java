package controller.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import model.movie.MovieDAO;
import model.movie.MovieVO;

public class MinsertAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		ActionForward forward = new ActionForward();
		MovieDAO mDAO = new MovieDAO();
		MovieVO mVO = new MovieVO();
		PrintWriter out = response.getWriter();

		
		String saveDir = request.getSession().getServletContext().getRealPath("img");// �ش� ������ �̹����� �����Ŵ
		System.out.println(saveDir);
		//System.out.println("saveDir"); ��� Ȯ�� �α�
		int maxSize = 16*1024*1024; // �ִ� 16mb
		String encoding = "UTF-8";
		MultipartRequest multi;
		multi = new MultipartRequest(request, saveDir, maxSize, encoding, new DefaultFileRenamePolicy());
		 
		mVO.setFilename(multi.getFilesystemName("filename")); // v���� �־��ִ� ���� 
		mVO.setContent(multi.getParameter("content"));	
		mVO.setMdate(multi.getParameter("mdate"));
		mVO.setMpk(multi.getParameter("mpk"));
		mVO.setMtype(multi.getParameter("mtype"));
		mVO.setTitle(multi.getParameter("title"));
		// System.out.println(mVO);
		
		if(mDAO.m_insertDB(mVO)) { 
			//���� ���� ��
			forward.setRedirect(true);
			forward.setPath("Adminlist.do");
		}
		else {
			// ���� ��
			saveDir += "/" + mVO.getFilename(); // ��Ͽ� �����ϸ� v���� ����� ���� ���ϰ� db�� �ִ� ���������� ��ĥ �� �ֱ⶧���� v���� ����� ���� ������ ����� ���ؼ� saveDir�� ����
			File file = new File(saveDir);
			if(file.exists()) { // ������ �����ϸ� ����
				file.delete();
			}
			response.setContentType("text/html; charset=UTF-8");
			out.println("<script>alert('�Խù� ��� ����');history.go(-1)</script>");// �Խù� ��� ���� �� alert
		}
		return forward;
	}

}
