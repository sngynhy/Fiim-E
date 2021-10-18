package common.crawling;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.UUID;

import org.apache.jasper.tagplugins.jstl.core.Set;
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

		try {

			doc = Jsoup.connect(url).get();
			Elements imgUrl = doc.select(".thumb"); // �� �̹��� url ���

			// �� ������ 10�� ��ȭ ���� 10���� ũ�Ѹ�
			for (int i=0; i<10; i++) {
				url2 = "https://movie.naver.com";

				doc = Jsoup.connect((url2 + imgUrl.get(i).select("a").attr("href")).replace("basic", "point")).get(); // �� �̹��� Ŭ�� �� �̵��Ǵ� �ش� ��ȭ ���� ������ document

				Element ele = doc.select(".mv_info_area").get(0);
				String genre = ele.select(".info_spec > dd").first().select("span").get(0).text(); // �帣 ����
				if (genre.indexOf(",") > 0) { // �帣 �������� ��� �� ù��° ���� ����
					genre = genre.substring(0, genre.indexOf(","));
				}

				url2 = url2 + doc.select(".ifr_module2 > iframe").attr("src");
				doc = Jsoup.connect(url2).get();

				conn.setAutoCommit(false); // Ʈ�� ��� scope ������ while�� ������

				String mpk = "";

				int sum = 0; // �� ��ȭ�� rating �հ� ����			
				int cnt = 0; // ���� ����

				Iterator<Element> itr = doc.select(".score_result li").iterator();
				while (itr.hasNext()) {
					Element el = itr.next();

					String review = el.select(".score_reple p").select("span").last().text(); // ����
					String rdate = el.select("dt em").last().text(); // ���� �ۼ� ��¥ �� �ð�
					int rating = Integer.parseInt(el.select(".star_score em").text())/2; // ���� �ְ��� 10�� ���� -> 5�� ����

					// ���̰� �� ����
					Elements el2 = el.select(".score_reple ._unfold_ment");

					if (el2.size() == 1) {
						review = el2.select("a").attr("data-src");
					}					

					String id = UUID.randomUUID().toString(); // id ���� ó��
					id = id.substring(0, 8);

					String pw = UUID.randomUUID().toString(); // pw ���� ó��
					pw = pw.substring(0, 5);

					String email = UUID.randomUUID().toString(); // email ���� ó��
					email = email.substring(0, 6) + "@naver.com";

					
					if (map.containsKey(genre)) {
						mpk = map.get(genre) + mpkInt;
					} else {
						mpk = "EX" + mpkInt;
					}


					// ����� ���� rating �հ�
					sum += rating;
					cnt++;

					System.out.println("���� : " + review);
					System.out.println("id : " + id);
					System.out.println("pw : " + pw);
					System.out.println("email : " + email);
					System.out.println("mpk : " + mpk);
					System.out.println("�ۼ���¥ : " + rdate);
					System.out.println("���� : " + rating);
					System.out.println();

					// DB�� ����		
					String reviewInsert = "insert into review (cmt, id, mpk, rdate, rating) values (?, ?, ? ,date_format(?,'%Y-%m-%d %H:%i'), ?)";
					String clientInsert = "insert into client values (?, ?, ?)";

					pstmt = conn.prepareStatement(reviewInsert);
					pstmt.setString(1, review);
					pstmt.setString(2, id);
					pstmt.setString(3, mpk);
					pstmt.setString(4, rdate);
					pstmt.setInt(5, rating);
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
				}

				System.out.println();
				mpkInt++;

				double ratingAvg = (double) sum / cnt ; // movie ���̺� �� ���� ���
				ratingAvg = Math.round(ratingAvg*10)/10.0;


				String movieUpdate = "update movie set ratingavg = ? where mpk = ?";
				pstmt = conn.prepareStatement(movieUpdate);
				pstmt.setDouble(1, ratingAvg);
				pstmt.setString(2, mpk);
				if (pstmt.executeUpdate()==0) {
					conn.rollback();
					return;
				}
				conn.commit();
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
