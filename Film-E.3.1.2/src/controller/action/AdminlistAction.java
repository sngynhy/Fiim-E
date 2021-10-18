package controller.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.page.PageDAO;
import common.page.PageVO;
import model.movie.MovieDAO;
import model.movie.MovieVO;

public class AdminlistAction implements Action{

   @Override
   public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException{
      ActionForward forward = new ActionForward();
      
      MovieDAO mDAO = new MovieDAO();
      PageDAO pDAO = new PageDAO();
      PageVO pVO = new PageVO();
      
      int page = 1;
      String ppage = request.getParameter("page");
      if(ppage != null) {
         page = Integer.parseInt(ppage);
      } // ���� �ߴ� ����¡ ó�� mcnt�� �Ȱ��� ���� 
      
      ArrayList<MovieVO> datas = new ArrayList<MovieVO>(); // m_selectDB_all�� ���� ��̸���Ʈ
      
      String search = request.getParameter("search"); // SelectAll�� ���� �˻��� �ϱ����� search�� type�� �޾ƿ�
      String mtype = request.getParameter("mtype");       
      
      pVO.setCurPage(page);   //   ���� ������   
      pVO.setPerPage(12);      //   ������ �Խù� ��
      pVO.setPerPageSet(5);   //   ������ ��
      
  	  pVO = pDAO.paging(pVO,null,mtype,search,"movie");
      
      datas = mDAO.m_selectDB_all(mtype, search, pVO ); 
      request.setAttribute("datas", datas);  // ����¡ ó���� �ϱ� ���� �� 3����
      request.setAttribute("paging", pVO);
      request.setAttribute("page", page);
      request.setAttribute("search", search);
      
      
      forward.setRedirect(false);
      forward.setPath("adminlist.jsp");
      return forward;
   }

}