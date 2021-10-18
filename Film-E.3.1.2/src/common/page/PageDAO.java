package common.page;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.common.JNDI;
import model.movie.MovieVO;

public class PageDAO {


	//��ü ����Ʈ�� �� ������ �������� sql
	String selectAllR = "select count(*) from review where mpk = ?";			//���� ��ü ����Ʈ
	String selectAll = "select count(*) from movie";							//��ȭ ��ü ����Ʈ
	String selectAllT = "select count(*) from movie where mtype = ?";			//��ȭ �帣 ���� ����Ʈ
	String selectAllSearch = "select count(*) from movie where title like ?";	//��ȭ �˻��� ��ü ����Ʈ
	String selectAllSearchT = "select count(*) from movie where mtype = ? and title like ?";	//��ȭ �帣 ����&�˻� ����Ʈ 

	PageVO data = null;


	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet rs = null;

	public PageVO paging(PageVO vo, MovieVO mvo, String mtype, String search, String table) {		
		System.out.println("pageDAO vo ������:" + vo);	
		conn = JNDI.connect();
		try {	//�� �Խù� �� count

			if(table.equals("movie")) {
				if(mtype == null || mtype.equals("")) {				

					if(search == null || search.equals("")) {		//type �� ���� �˻��� ������ ��ü ����Ʈ�� ����
						pstmt=conn.prepareStatement(selectAll);
						System.out.println("pageDAO ��ü ����Ʈ ī��Ʈ");
					}
					else {											//type �� ���� �˻��� ������ �˻��� ����Ʈ�� ����
						pstmt=conn.prepareStatement(selectAllSearch);
						pstmt.setString(1, "%"+search+"%" );
						System.out.println("pageDAO ��ü ����Ʈ �˻� ī��Ʈ");
					}
				}

				else{												
					if(search == null || search.equals("")) {		//type�� ������ type �帣 ����Ʈ�� ����
						pstmt=conn.prepareStatement(selectAllT);
						pstmt.setString(1, mtype);
						System.out.println("pageDAO "+mtype+" ����Ʈ ī��Ʈ");
					}
					else {											//type�� �ְ�, �˻��ҽ� type �帣�� �˻��� ����Ʈ�� ����
						pstmt=conn.prepareStatement(selectAllSearchT);
						pstmt.setString(1, mtype);
						pstmt.setString(2, "%"+search+"%" );
						System.out.println("pageDAO "+mtype+" ����Ʈ �˻� ī��Ʈ");
					}
				}
			}
			
			else if(table.equals("review")){						//�����϶� movie�� ���� ����Ʈ ����
				pstmt=conn.prepareStatement(selectAllR);
				pstmt.setString(1, mvo.getMpk());
				System.out.println("pageDAO ���� ��ü ����Ʈ ī��Ʈ");
				
			}



			rs = pstmt.executeQuery();

			if(rs.next()) {
				vo.setTotal(rs.getInt(1));		//sql�� �޾ƿ� �� ����
			}		
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JNDI.disconnect(pstmt, conn);
		}

		
		/*
	 	startPage

	 	(curPage - 1) / perPageSet * perPageSet + 1
	 	->curPage-1�� ������ ��谪���� ������ ���� ex) perPageSet�� 5�϶� 5�������� Ŭ���� 6~10�������� ������
	 	->�����⸦ �ϰ� �ٽ� ���� ������ ���������� ������ ����
	 	

	  	(1-1)/5*5 + 1 = 1
	 	(2-1)/5*5 + 1 = 1
	 	(3-1)/5*5 + 1 = 1
	 	(4-1)/5*5 + 1 = 1
	 	(5-1)/5*5 + 1 = 1

	 	(6-1)/5*5 + 1 = 2
	 	(7-1)/5*5 + 1 = 2
	 	(8-1)/5*5 + 1 = 2
	 	(9-1)/5*5 + 1 = 2
	 	(10-1)/5*5 + 1 = 2


		endPage = startPage + 5-1
		->c:forEach�� begin end�� ����ҽ� start 6 end 10 = 6 7 8 9 10


		===============================
		�Խù�
		perPage�� 5�϶�,	1 ������ �̸�	1~5�� ������ߵ�
		(curPage-1)*perPage+1
		start
		(1-1)*5 + 1 = 1
		(2-1)*5 + 1 = 6
		(3-1)*5 + 1 = 11
		
		end = start + 5
		==============================

		��Ʈ ������
		ī��Ʈ�� ������ ���� perPage���� ����

		(count-1) / perPage + 1

		(50-1) / 5 +1 = 10

		(51-1) / 5 +1= 10
		(52-1) / 5 +1= 10
		(53-1) / 5 +1= 10
		(54-1) / 5 +1= 10
		(55-1) / 5 +1= 10

		(56-1) / 5 +1= 11
		===============================
		 */




		vo.setLastPage((vo.getTotal()-1)/vo.getPerPage()+1);	//������ ������ ����	
		vo.setStart((vo.getCurPage()-1)*vo.getPerPage());		//�������� ������ �Խù� ����
		vo.setEnd(vo.getStart()+vo.getPerPage());				//�������� ������ �Խù� ��		



		vo.setStartPage((vo.getCurPage()-1)/vo.getPerPageSet()*vo.getPerPageSet()+1);	//���� ����
		if(vo.getStartPage() < 1) {		//������������ 1���� ������� 1�� ����
			vo.setStartPage(1);
		}

		vo.setEndPage(vo.getStartPage()+vo.getPerPageSet()-1);							//���� ��
		if(vo.getEndPage() > vo.getLastPage()) {	//���������� ���������������� Ŭ��� �������������� ����
			vo.setEndPage(vo.getLastPage());
		}

		System.out.println("pageDAO ������ vo :" + vo);

		return vo;

	}






}
