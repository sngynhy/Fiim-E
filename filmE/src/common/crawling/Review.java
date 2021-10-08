package common.crawling;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import model.common.JDBC;

public class Review {
	
	public static void main(String[] args) throws SQLException {
		
		Connection conn = JDBC.getConnection();
		PreparedStatement pstmt = null;

		String url = "https://movie.naver.com/movie/running/current.naver"; // ��ȭ ����Ʈ
		String url2 = "";
		
		Document doc = null;
		
		int mpkInt = 1001;
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("�׼�", "AC");
		map.put("�ִϸ��̼�", "AN");
		map.put("���/�θǽ�", "RO");
		map.put("���", "DR");
		map.put("��ť���͸�", "DC");
		
//		try {
//			pstmt = conn.prepareStatement("select rpk, cmt, id, mpk, to_char(rdate,'YYYY/MM/DD HH:MI') rdate from review");
//			ResultSet rs = pstmt.executeQuery();
//			while (rs.next()) {
//				System.out.println(rs.getString("rdate"));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			JDBC.close(conn, pstmt);
//		}
		
		try {
			doc = Jsoup.connect(url).get();
			Elements imgUrl = doc.select(".thumb"); // �� �̹��� url ���
			
			// �� ������ 10�� ��ȭ ���� 10���� ũ�Ѹ�
			for (int i=0; i<10; i++) {
				url2 = "https://movie.naver.com";

//				System.out.println((url2 + imgUrl.get(i).select("a").attr("href")).replace("basic", "point"));
				doc = Jsoup.connect((url2 + imgUrl.get(i).select("a").attr("href")).replace("basic", "point")).get(); // �� �̹��� Ŭ�� �� �̵��Ǵ� �ش� ��ȭ ���� ������ document
				
				Element ele = doc.select(".mv_info_area").get(0);
				String genre = ele.select(".info_spec > dd").first().select("span").get(0).text(); // �帣 ����
//				System.out.println(genre);
				
				url2 = url2 + doc.select(".ifr_module2 > iframe").attr("src");
//				System.out.println(url2);
				
				doc = Jsoup.connect(url2).get();
//				System.out.println(doc);
				
				Iterator<Element> itr = doc.select(".score_reple").iterator();
				while (itr.hasNext()) {
					Element el = itr.next();
					
					String review = el.select("p").select("span").last().text(); // ����
					String rdate = el.select("dt em").last().text(); // ���� �ۼ� ��¥ �� �ð�
//					System.out.println(rdate);
					
					if (review.contains("�����Ϸ��� ���Ե� �������Դϴ�. ������ ���� ")) { // ��ó��
						review = review.replace("�����Ϸ��� ���Ե� �������Դϴ�. ������ ���� ", "");
					}
//					System.out.println(review);
					
					String id = UUID.randomUUID().toString(); // id ���� ó��
					id = id.substring(0, 8);
					
					String pw = UUID.randomUUID().toString(); // pw ���� ó��
					pw = pw.substring(0, 5);
					
					String email = UUID.randomUUID().toString(); // email ���� ó��
					email = email.substring(0, 6) + "@naver.com";
					
//					System.out.println(id);
//					System.out.println(pw);
//					System.out.println(email);

					
					String mpk = "";
					if (map.containsKey(genre)) {
						mpk = map.get(genre) + mpkInt;
					} else {
						mpk = "EX" + mpkInt;
					}
					System.out.println(mpk);
//					System.out.println();
					
					// DB�� ����
					String reviewInsert = "insert into review values (review_seq.NEXTVAL, ?, ?, ? ,to_date(?, 'YYYY/MM/DD HH24:MI'))";
					String clientInsert = "insert into client values (?, ?, ?)";
					
					conn.setAutoCommit(false);
					pstmt = conn.prepareStatement(reviewInsert);
					pstmt.setString(1, review);
					pstmt.setString(2, id);
					pstmt.setString(3, mpk);
					pstmt.setString(4, rdate);
					if (pstmt.executeUpdate()==0) {
						conn.rollback();
						return;
					}
					
					pstmt = conn.prepareStatement(clientInsert);
					pstmt.setString(1, id);
					pstmt.setString(2, pw);
					pstmt.setString(3, email);
					if (pstmt.executeUpdate()==0) {
						conn.rollback();
						return;
					}
					
					conn.commit();
				}
			
			System.out.println();
			mpkInt++;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
		} finally {
			JDBC.close(conn, pstmt);
		}
		
	}
	
}
