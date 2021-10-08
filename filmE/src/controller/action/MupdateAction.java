package controller.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import model.movie.MovieDAO;
import model.movie.MovieVO;

public class MupdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		ActionForward forward = new ActionForward(); 

		MovieDAO mDAO = new MovieDAO();
		MovieVO mVO = new MovieVO();


		PrintWriter out = response.getWriter();
		String saveDir = request.getSession().getServletContext().getRealPath("img"); // �̹��� ���

		int maxSize = 16*1024*1024;
		String encoding = "UTF-8";

		MultipartRequest multi;
		multi = new MultipartRequest(request, saveDir, maxSize, encoding, new DefaultFileRenamePolicy());
		//saveDir : ���� ��� ���� ���ϸ� ���ε� �� ������ ����� ������ �����մϴ�. ��� ���� Ʋ���� ����ó��
		//maxSize : 16mb
		//encoding : UTF-8
		//new DefaultFileRenamePolicy() : ���࿡ ���ε� �� ������ ����Ǵ� ������ ���� �̸��� ������ �����Ѵٸ� ���ϸ� �ڿ� ���ڰ� 1���� �ٽ��ϴ�.


		mVO.setMpk(request.getParameter("mpk")); // selectone�� �ʿ��� ��
		String prefile = mDAO.m_selectDB_one(mVO).getFilename(); // // selectone���� ���� �ִ� DB�� Ȯ��
		mVO.setFilename(multi.getFilesystemName("filename")); // Update�� ���� ������ �޾ƿ��� ����  
		//getFileNames("filename") : ���ε� �� ���ϵ鿡 ���� �̸��� Enumeration ��ü�� String ���·� ��� ��ȯ��
		
		mVO.setMdate(multi.getParameter("mdate"));
		mVO.setContent(multi.getParameter("content"));
		mVO.setMtype(multi.getParameter("mtype"));
		mVO.setTitle(multi.getParameter("title"));

		if(!prefile.substring(0, 4).equals("http")) {		// ó�� 4���ڰ� http�� �ƴϸ� �տ� img/�߰�
			prefile = prefile.substring(4); // img/�� 2�� �پ ������ ��
		}

		File file = null;      // �ι� ���� �����Ƽ� ����


		if(mVO.getFilename() == null) { // v���� �޾ƿ� ���ϳ����� null�϶�
			mVO.setFilename(prefile);	// db�� �ִ� �������� ���
			if(mDAO.m_updateDB(mVO)) {	
				forward.setRedirect(true);
				forward.setPath("Adminlist.do");
			}else {
				response.setContentType("text/html; charset=UTF-8");      
				out.println("<script>alert('���� ����!');history.go(-1)</script>"); // ���� ���� ���� �� alertâ
			}
		}
		else {
			if(mDAO.m_updateDB(mVO)) { // v���� ���� ���� ������ null�� �ƴҶ�
				// ���� ���� �� 
				saveDir += "/" + prefile; // ���� �ִ� DB ��
				file = new File(saveDir); 
				file.delete(); // ���� ���� 
				forward.setRedirect(true);
				forward.setPath("Adminlist.do");
			}
			else {
				// ���� ���н�
				saveDir += "/" + mVO.getFilename(); // DB�� ������� �ϴ� ��
				file = new File(saveDir);  
				if(file.exists()) { // ���� �ϸ� 
					file.delete();  // ����
				}
				response.setContentType("text/html; charset=UTF-8");      
				out.println("<script>alert('���� ����!');history.go(-1)</script>"); // ���� ���� ���� �� alertâ

			}

		}
		return forward;





	}

}