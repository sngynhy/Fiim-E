package model.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.common.JNDI;


public class ClientDAO {

	// ���������� �ɸ��°��� ���� ���� fk�� ������� �ʰ� �� ��� Ʈ������� ����ϰ� ó��

	String login = "select * from client where id= ? and pw = ?"; // �α���
	String insert = "insert into client values (?,?,?)"; // ȸ������
	String delete = "delete from client where id = ?"; // ȸ��Ż��
	String update = "update client set pw = ?, email= ? where id = ?"; // ȸ������ ����
	String rUpdate = "update review set id = '(�˼�����)'  where id = ?";  // ȸ�� Ż��� ���� ID (�˼�����)
	String selectONE = "select * from client where id = ?"; // ȸ��Ż�� ���� Ȯ�� (�̻��)
	String rSelectONE = "select * from review where id= ?"; // vo��  ID��  ���� �����ִ��� Ȯ��! (�̻��)
	String checkID = "select * from client where id = ?"; // ȸ������ �� ���̵� �ߺ�Ȯ��, ȸ������ ������ id�� ��ȸ


	Connection conn=null;
	PreparedStatement pstmt=null; 

	ClientVO data= null;

	public ClientVO login(ClientVO vo) { //�α��� ����
		conn =JNDI.connect();


		try {
			pstmt=conn.prepareStatement(login);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
				data=new ClientVO();
				data.setId(rs.getString("id"));
				data.setPw(rs.getString("pw"));
				data.setEmail(rs.getString("email"));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JNDI.disconnect(pstmt, conn);
		}
		return data;
	}

	public boolean c_insertDB(ClientVO vo) { // ȸ�����Խ� ������ �߰�
		conn=JNDI.connect();

		boolean result=false;
		try {
			pstmt=conn.prepareStatement(insert);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());
			pstmt.setString(3, vo.getEmail());	
			pstmt.executeUpdate();
			result=true;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JNDI.disconnect(pstmt, conn);
		}
		return result;
	}

	public boolean c_deleteDB(ClientVO vo) throws SQLException{ // ȸ��Ż��
		conn = JNDI.connect();
		
		boolean result = false;

		try {
			conn.setAutoCommit(false); // �ڵ����� DB�� commit�� ���� �ɶ����� sql�� ����Ǵ� ���� ������! 
			// ����Ŀ�� ����


			pstmt=conn.prepareStatement(delete); // ȸ��Ż��
			pstmt.setString(1, vo.getId());
			pstmt.executeUpdate();
	
			pstmt=conn.prepareStatement(rUpdate); // ȸ��Ż��� ���� id = '�˼�����'���� ����
			pstmt.setString(1, vo.getId());
			pstmt.executeUpdate();

			conn.commit(); // Ŀ��
			conn.setAutoCommit(true);
		
			result= true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ClientDAO delete Ʈ����� ����");
			conn.rollback();		//�ѹ�
			conn.setAutoCommit(true);
		} finally {
			JNDI.disconnect(pstmt, conn);
		}
		return result;

	}

	/*public boolean c_deleteDB(ClientVO vo) { // ȸ��Ż��
		conn = JNDI.connect();

		boolean result = false;


		try {
			conn.setAutoCommit(false); // �ڵ����� DB�� commit�� ���� �ɶ����� sql�� ����Ǵ� ���� ������!
			ResultSet rs=null;


			pstmt=conn.prepareStatement(delete);
			pstmt.setString(1, vo.getId());
			pstmt.executeUpdate();


			pstmt=conn.prepareStatement(rUpdate);
			pstmt.setString(1, vo.getId());
			pstmt.executeUpdate();


			pstmt=conn.prepareStatement(selectONE);
			pstmt.setString(1, vo.getId());
			rs=pstmt.executeQuery();
			if(rs.next()) { // ID�� �����ִٸ� ...
				conn.rollback();
			}


			pstmt=conn.prepareStatement(rSelectONE);
			pstmt.setString(1, vo.getId());
			rs=pstmt.executeQuery();
			if(rs.next()) { // ���信 ID�� vo�� ID���� Ȯ��!
				conn.rollback();
			}

			rs.close();
			conn.commit();
			conn.setAutoCommit(true);

			result= true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JNDI.disconnect(pstmt, conn);
		}
		return result;

	}*/

	public boolean c_updateDB(ClientVO vo) { // ȸ�� ���� ����
		conn= JNDI.connect();

		boolean result=false;


		try {
			pstmt=conn.prepareStatement(update);
			pstmt.setString(1, vo.getPw());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getId());
			pstmt.executeUpdate();


			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JNDI.disconnect(pstmt, conn);
		}
		return result;
	}
	
	public ClientVO c_selectDB_one(ClientVO vo) { // ȸ������ ������ id�� ��ȸ
		conn =JNDI.connect();


		try {
			pstmt=conn.prepareStatement(checkID);
			pstmt.setString(1, vo.getId());
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
				data=new ClientVO();
				data.setId(rs.getString("id"));
				data.setPw(rs.getString("pw"));
				data.setEmail(rs.getString("email"));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JNDI.disconnect(pstmt, conn);
		}
		return data;
	}
	

	public boolean checkID(String id) { // ȸ�����Խ� ���̵� �ߺ�Ȯ��

		conn= JNDI.connect();
		

		boolean exist = false;
		try {
			pstmt = conn.prepareStatement(checkID);
			pstmt.setString(1, id);

			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				exist = true;
			}

			rs.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			JNDI.disconnect(pstmt, conn);
		}
		return exist;
	}

}
