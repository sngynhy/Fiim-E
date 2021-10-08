package model.movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.page.PageVO;
import model.common.JNDI;


public class MovieDAO {
	String rdelete = "DELETE FROM REVIEW WHERE MPK = ?";										//���� ���� Ʈ�����

	String selectAll = "SELECT * FROM(SELECT A.*, ROWNUM AS RNUM FROM(SELECT * FROM MOVIE ORDER BY TITLE ASC) A WHERE ROWNUM < ?) WHERE RNUM >=?";			//��ȭ ��ü ����Ʈ
	String selectAllT = "SELECT * FROM(SELECT A.*, ROWNUM AS RNUM FROM(SELECT * FROM MOVIE WHERE MTYPE = ? ORDER BY TITLE ASC) A WHERE ROWNUM < ?) WHERE RNUM >=?";
	String selectAllSearch = "SELECT * FROM(SELECT A.*, ROWNUM AS RNUM FROM(SELECT * FROM MOVIE WHERE TITLE LIKE ? ORDER BY TITLE ASC) A WHERE ROWNUM < ?) WHERE RNUM >=?";
	String selectAllSearchT = "SELECT * FROM(SELECT A.*, ROWNUM AS RNUM FROM(SELECT * FROM MOVIE WHERE MTYPE = ? AND TITLE LIKE ? ORDER BY TITLE ASC) A WHERE ROWNUM < ?) WHERE RNUM >=?";
	
	//select ����
	//order by�� rownum�� ������ �ʿ��Ͽ� () ���
	//rownum�� 1���� ���� ���� ������ ���� ���� ����
	//�׷��Ƿ� rownum > ? �̷������δ� 0�� �ƴϸ� ���� ���� ����
	//�׷��� rownum < ? �޾ƿ��� rnum�� ������ rnum> ? �� ������ ���
	//(select * from movie order by title asc) A -> movie ���̺��� �̸������� ������ ����Ʈ�� A�� �ӽ� ����
	//(select A.* , rownum as rnum from () A where rownum < ?) -> rownum�� rnum�� �ӽ� ���� A, rownum < ? �� ����Ʈ ����
	//select * from () where rnum >= ? �� ����Ʈ ����
	
	String mpk = "SELECT MPK FROM MOVIE";									//��ȭ ��ü ����Ʈ
	String selectOne = "SELECT * FROM MOVIE WHERE MPK = ?";					//��ȭ Ŭ��
	String insert = "INSERT INTO MOVIE VALUES (?,?,?,?,TO_CHAR(TO_DATE(?,'YYYY-MM-DD'),'YYYY-MM-DD'),?)";				//��ȭ ���
	String delete = "DELETE FROM MOVIE WHERE MPK = ?";						//��ȭ ����
	String update = "UPDATE MOVIE SET TITLE  = ?, CONTENT = ?, MTYPE = ?, MDATE = TO_CHAR(TO_DATE(?,'YYYY-MM-DD'),'YYYY-MM-DD'), FILENAME = ?  WHERE MPK = ?";				//��ȭ ����
	
	String isHttp = null;		//http�� �ƴҰ�� img/�� ���� String
	Boolean isMpk = false;		//�帣�� mpkSet�� ����Ǿ����� Ȯ��
	String [][] mpkSet= {		//���������� ����  mpkSet ����
			{"�׼�","�ִϸ��̼�","���/�θེ","���","��ť���͸�"},
			{"AC","AN","RO","DR","DC"}		
	};
	

	MovieVO data = null;
	ArrayList<MovieVO> datas = null;
	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet rs = null;

	boolean flag = false;

	public ArrayList<MovieVO> m_selectDB_all(String mtype, String search, PageVO vo){			//��ȭ ����Ʈ
		datas = new ArrayList<MovieVO>();
		conn = JNDI.connect();
		try {
			//����, ī�װ�, �����ڸ���Ʈ ���������� ���
			//�˻��� ī�װ������� �����Ͽ� ����
			if(mtype == null || mtype.equals("")) {				

				if(search == null || search.equals("")) {		//ī�װ� x, �˻� x
					pstmt=conn.prepareStatement(selectAll);
					pstmt.setInt(1, vo.getEnd());
					pstmt.setInt(2, vo.getStart());
					System.out.println("��ü ����Ʈ");
				}
				else {											//ī�װ� x, �˻� o
					pstmt=conn.prepareStatement(selectAllSearch);
					pstmt.setString(1, "%"+search+"%" );
					pstmt.setInt(2, vo.getEnd());
					pstmt.setInt(3, vo.getStart());
					System.out.println("��ü ����Ʈ �˻�");
				}
			}

			else{												
				if(search == null || search.equals("")) {		//ī�װ� o, �˻� x
					pstmt=conn.prepareStatement(selectAllT);
					pstmt.setString(1, mtype);
					pstmt.setInt(2, vo.getEnd());
					pstmt.setInt(3, vo.getStart());
					System.out.println(mtype+" ����Ʈ");
				}
				else {											//ī�װ� o, �˻� o
					pstmt=conn.prepareStatement(selectAllSearchT);
					pstmt.setString(1, mtype);
					pstmt.setString(2, "%"+search+"%" );
					pstmt.setInt(3, vo.getEnd());
					pstmt.setInt(4, vo.getStart());
					System.out.println(mtype+" ����Ʈ �˻�");
				}

			}

			rs=pstmt.executeQuery();
			while(rs.next()) {
				data = new MovieVO();
				data.setMpk(rs.getString("mpk"));
				data.setTitle(rs.getString("title"));
				data.setContent(rs.getString("content"));
				data.setMtype(rs.getString("mtype"));
				data.setMdate(rs.getString("mdate"));
				
				isHttp = rs.getString("filename");
				
				if(!isHttp.substring(0, 4).equals("http")) {		// ó�� 4���ڰ� http�� �ƴϸ� �տ� img/�߰�
					isHttp = "img/"+isHttp;
				}
					
				data.setFilename(isHttp);
				datas.add(data);
			}
			rs.close();
		}
		catch (SQLException e) {
			System.out.println("MovieDAO ��ȭ ��ü ����Ʈ ����");
			e.printStackTrace();
		}
		finally {
			JNDI.disconnect(pstmt, conn);
		}
		//System.out.println("MovieDAO selectAll datas :" + datas);
		return datas;	
	}

	public MovieVO m_selectDB_one(MovieVO vo){			//��ȭ Ŭ��
		//System.out.println("MovieDAO ��ȭ Ŭ�� VO :" + vo);
		data = new MovieVO();
		conn = JNDI.connect();
		try {
			pstmt=conn.prepareStatement(selectOne);
			pstmt.setString(1, vo.getMpk());
			rs=pstmt.executeQuery();
			while(rs.next()) {
				
				data.setMpk(rs.getString("mpk"));
				data.setTitle(rs.getString("title"));
				data.setContent(rs.getString("content"));
				data.setMtype(rs.getString("mtype"));
				data.setMdate(rs.getString("mdate"));
				
				isHttp = rs.getString("filename");
				
				if(!isHttp.substring(0, 4).equals("http")) {		// ó�� 4���ڰ� http�� �ƴϸ� �տ� img/�߰�
					isHttp = "img/"+isHttp;
				}
				
				data.setFilename(isHttp);
			}
			rs.close();
			System.out.println("MovieDAO ��ȭ Ŭ�� :" + data);
		}
		catch (SQLException e) {
			System.out.println("MovieDAO ��ȭ Ŭ�� ����");
			e.printStackTrace();
		}
		finally {
			JNDI.disconnect(pstmt, conn);
		}
		System.out.println("MovieDAO selectOne data :" + data);
		return data;	
	}


	public Boolean m_insertDB(MovieVO vo) {          //��ȭ ���
	      System.out.println("MovieDAO ��ȭ ��� VO :" + vo);
	      conn = JNDI.connect();
	      try {



	         pstmt = conn.prepareStatement(mpk);
	         rs = pstmt.executeQuery();

	         String mpkStr = null;   //   mpk ��
	         String mpkType = null;   //  'AC'1001  mpk �տ� �� ���� 


	         int mpkInt = 0;         //   mpk�� ������Ű������ int�� ��ȯ�ϰ� ������ ����
	         int max = 0;
	         int cnt = 0;         //   ������ų mpk�� �ִ��� Ȯ���ϴ� ����

	         while(rs.next()) {

	            mpkStr = rs.getString("mpk").substring(2);      //mpk type ������ �޺κи� ������
	            mpkInt = Integer.parseInt(mpkStr);            //��Ʈ�� ��ȯ
	            if(mpkInt > max){
	            max = mpkInt;
	            }                              //1 ����
	            cnt++;                        //cnt���� 
	         }

	         if(cnt == 0) {            //rs �� null �ϰ��
	            mpkInt = 1001;         //mpk�� 1001�� ����
	         }

	         
	         for(int i = 0 ; i < mpkSet[0].length; i++) {   
	            if(vo.getMtype().equals(mpkSet[0][i])) {      //�帣�� mpkSet�� ������ִ� �����Ϳ� ���� ���
	               mpkType = mpkSet[1][i];                  
	               isMpk = true;
	            }            
	         }
	         

	         if(!isMpk) {                              //�帣�� mpkSet�� ������� �ʾ��� ���
	            mpkType = "EX";                           
	            vo.setMtype("ETC");                        //vo�� ETC�� ����
	         }
	               
	         max++;         
	         mpkStr = mpkType + max;                     // AC + 1001
	         vo.setMpk(mpkStr);                           // mpkStr�� mpk�� set         

	         pstmt=conn.prepareStatement(insert);

	         pstmt.setString(1, vo.getMpk());
	         pstmt.setString(2, vo.getTitle());
	         pstmt.setString(3, vo.getContent());
	         pstmt.setString(4, vo.getMtype());
	         pstmt.setString(5, vo.getMdate());
	         pstmt.setString(6, vo.getFilename());
	         System.out.println("MovieDAO insert vo :" + vo);
	         if(pstmt.executeUpdate() != 0) {         //��� ����
	            System.out.println("MovieDAO insert ����");
	            flag = true;
	         }
	      }
	      catch (SQLException e) {
	         System.out.println("MovieDAO insert ����");
	         e.printStackTrace();
	      }
	      finally {
	         JNDI.disconnect(pstmt, conn);
	      }
	      return flag;                     //��� ����

	   }

	public Boolean m_updateDB(MovieVO vo) { 			//��ȭ ����
		System.out.println("MovieDAO ��ȭ ���� VO :" + vo);
		conn = JNDI.connect();
		try {
			pstmt=conn.prepareStatement(update);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getMtype());
			pstmt.setString(4, vo.getMdate());
			pstmt.setString(5, vo.getFilename());
			pstmt.setString(6, vo.getMpk());

			if(pstmt.executeUpdate() != 0) {			//���� ����
				System.out.println("MovieDAO ��ȭ ���� ����");
				flag = true;
			}
		}
		catch (SQLException e) {
			System.out.println("MovieDAO ��ȭ ���� ����");
			e.printStackTrace();
		}
		finally {
			JNDI.disconnect(pstmt, conn);
		}
		return flag;								//���� ����

	}

	public Boolean m_deleteDB(MovieVO vo) throws SQLException { 				//��ȭ ����
		System.out.println("MovieDAO ��ȭ ���� VO :" + vo);
		conn = JNDI.connect();
		try {
			conn.setAutoCommit(false); 				//����Ŀ�� ����

			pstmt=conn.prepareStatement(delete);	//��ȭ ����
			pstmt.setString(1, vo.getMpk());
			if(pstmt.executeUpdate() != 0) {
				System.out.println("MovieDAO movie delete ����");
			}


			pstmt=conn.prepareStatement(rdelete);	//���� ����
			pstmt.setString(1, vo.getMpk());

			if(pstmt.executeUpdate() != 0) {
				System.out.println("MovieDAO movie delete,rdelete ����");
			}



			conn.commit();				//Ŀ��
			flag = true;
			System.out.println("MovieDAO movie delete Ŀ�� ����");

		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("MovieDAO delete Ʈ����� ����");
			conn.rollback();		//sql���� �߻��� �ѹ�
		}finally {
			conn.setAutoCommit(true);
			JNDI.disconnect(pstmt, conn);				
		}
		return flag;

	}

}
