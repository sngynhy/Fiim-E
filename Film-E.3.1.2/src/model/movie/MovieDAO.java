package model.movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import common.page.PageVO;
import model.common.JNDI;


public class MovieDAO {
	String rdelete = "delete from review where mpk = ?";										//���� ���� Ʈ�����

	String count = "select count(*) from movie";
	String selectRand = "select * from movie";

	String selectAllm = "select * from movie order by ratingavg desc limit ?, ?";			//��ȭ ��ü ����Ʈ
	String selectAll = "select * from movie order by title asc limit ?, ?";			//��ȭ ��ü ����Ʈ
	//SELECT * FROM MOVIE WHERE MTYPE = ? ORDER BY TITLE ASC LIMIT ?, ?
	String selectAllT = "select * from movie where mtype = ? order by title asc limit ?, ?";
	String selectAllSearch = "select * from movie where title like ? order by title asc limit ?, ?";
	String selectAllSearchT = "select * from movie where mtype = ? and title like ? order by title asc limit ?, ?";

	//select ����
	//order by�� rownum�� ������ �ʿ��Ͽ� () ���
	//rownum�� 1���� ���� ���� ������ ���� ���� ����
	//�׷��Ƿ� rownum > ? �̷������δ� 0�� �ƴϸ� ���� ���� ����
	//�׷��� rownum < ? �޾ƿ��� rnum�� ������ rnum> ? �� ������ ���
	//(select * from movie order by title asc) A -> movie ���̺��� �̸������� ������ ����Ʈ�� A�� �ӽ� ����
	//(select A.* , rownum as rnum from () A where rownum < ?) -> rownum�� rnum�� �ӽ� ���� A, rownum < ? �� ����Ʈ ����
	//select * from () where rnum >= ? �� ����Ʈ ����

	String mpk = "select mpk from movie";									//��ȭ ��ü ����Ʈ
	String selectOne = "select * from movie where mpk = ?";					//��ȭ Ŭ��
	String insert = "insert into movie(mpk, title, content, mtype,mdate,filename) values (?,?,?,?,date_format(?,'%Y-%m-%d'),?)";				//��ȭ ���
	String delete = "delete from movie where mpk = ?";						//��ȭ ����
	String update = "update movie set title  = ?, content = ?, mtype = ?, mdate = date_format(?,'%Y-%m-%d'), filename = ?  where mpk = ?";				//��ȭ ����

	String isHttp = null;		//http�� �ƴҰ�� img/�� ���� String
	Boolean isMpk = false;		//�帣�� mpkSet�� ����Ǿ����� Ȯ��
	String [][] mpkSet= {		//���������� ����  mpkSet ����
			{"�׼�","�ִϸ��̼�","���/�θེ","���","��ť���͸�"},
			{"AC","AN","RO","DR","DC"}		
	};
	
	HashMap<String, String> mpkMap = new HashMap<String, String>(){{
		   put("�׼�", "AC");
		   put("�ִϸ��̼�", "AN");
		   put("���/�θǽ�", "RO");
		   put("���", "DR");
		   put("��ť���͸�", "DC");
	}};
	
	MovieVO data = null;
	ArrayList<MovieVO> datas = null;
	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet rs = null;

	boolean flag = false;

	public ArrayList<MovieVO> m_selectDB_all_m(PageVO vo){
		datas = new ArrayList<MovieVO>();
		conn = JNDI.connect();
		try {
			//����, ī�װ�, �����ڸ���Ʈ ���������� ���
			//�˻��� ī�װ������� �����Ͽ� ����	

			pstmt=conn.prepareStatement(selectAllm);
			pstmt.setInt(1, vo.getStart());
			pstmt.setInt(2, vo.getPerPage());
			System.out.println("���� ��ü ����Ʈ");


			rs=pstmt.executeQuery();
			while(rs.next()) {
				data = new MovieVO();
				data.setMpk(rs.getString("mpk"));
				data.setTitle(rs.getString("title"));
				data.setContent(rs.getString("content"));
				data.setMtype(rs.getString("mtype"));
				data.setMdate(rs.getString("mdate"));
				data.setRatingavg(rs.getInt("ratingavg"));

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

	public ArrayList<MovieVO> m_selectDB_all(String mtype, String search, PageVO vo){			//��ȭ ����Ʈ
		datas = new ArrayList<MovieVO>();
		conn = JNDI.connect();
		try {
			//����, ī�װ�, �����ڸ���Ʈ ���������� ���
			//�˻��� ī�װ������� �����Ͽ� ����
			if(mtype == null || mtype.equals("")) {				

				if(search == null || search.equals("")) {		//ī�װ� x, �˻� x
					pstmt=conn.prepareStatement(selectAll);
					pstmt.setInt(1, vo.getStart());
					pstmt.setInt(2, vo.getPerPage());
					System.out.println("��ü ����Ʈ");
				}
				else {											//ī�װ� x, �˻� o
					pstmt=conn.prepareStatement(selectAllSearch);
					pstmt.setString(1, "%"+search+"%" );
					pstmt.setInt(2, vo.getStart());
					pstmt.setInt(3, vo.getPerPage());
					System.out.println("��ü ����Ʈ �˻�");
				}
			}

			else{												
				if(search == null || search.equals("")) {		//ī�װ� o, �˻� x
					pstmt=conn.prepareStatement(selectAllT);
					pstmt.setString(1, mtype);
					pstmt.setInt(2, vo.getStart());
					pstmt.setInt(3, vo.getPerPage());
					System.out.println(mtype+" ����Ʈ");
				}
				else {											//ī�װ� o, �˻� o
					pstmt=conn.prepareStatement(selectAllSearchT);
					pstmt.setString(1, mtype);
					pstmt.setString(2, "%"+search+"%" );
					pstmt.setInt(3, vo.getStart());
					pstmt.setInt(4, vo.getPerPage());
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
				data.setRatingavg(rs.getInt("ratingavg"));

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

	public MovieVO m_selectDB_rand(){

		Random rand = new Random();
		int temp = 0;
		int cnt = 0;
		conn = JNDI.connect();
		data = new MovieVO();
		try {

			pstmt = conn.prepareStatement(count);
			ResultSet rs1 = pstmt.executeQuery();
			if(rs1.next()) {
				temp = rand.nextInt(rs1.getInt(1));			
			}
			pstmt = conn.prepareStatement(selectRand);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				if(temp == cnt) {
					data = new MovieVO();
					data.setMpk(rs.getString("mpk"));
					data.setTitle(rs.getString("title"));
					data.setContent(rs.getString("content"));
					data.setMtype(rs.getString("mtype"));
					data.setMdate(rs.getString("mdate"));	
					data.setRatingavg(rs.getDouble("ratingavg"));

					isHttp = rs.getString("filename");

					if(!isHttp.substring(0, 4).equals("http")) {		// ó�� 4���ڰ� http�� �ƴϸ� �տ� img/�߰�
						isHttp = "img/"+isHttp;
					}

					data.setFilename(isHttp);

				}
				cnt++;
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JNDI.disconnect(pstmt, conn);
		}


		return data;

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
				data.setRatingavg(rs.getDouble("ratingavg"));

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
			boolean isNew = true;         //   ������ų mpk�� �ִ��� Ȯ���ϴ� ����

			while(rs.next()) {

				mpkStr = rs.getString("mpk").substring(2);      //mpk type ������ �޺κи� ������
				mpkInt = Integer.parseInt(mpkStr);            //��Ʈ�� ��ȯ
				if(mpkInt > max){
					max = mpkInt;
				}                              //1 ����
				isNew = false; 
			}

			if(isNew) {            //rs �� null �ϰ��
				mpkInt = 1001;         //mpk�� 1001�� ����
			}

			
			if(mpkMap.containsKey(vo.getMtype())) {
				mpkType = mpkMap.get(vo.getMtype());
			}else {
				mpkType = "EX";
				vo.setMtype("ETC");
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
