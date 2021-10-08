package common.crawling;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import model.common.JDBC;

public class Movie {
   
   public static void main(String[] args) {

      Connection conn = JDBC.getConnection();
      PreparedStatement pstmt = null;

      String url = "https://movie.naver.com/movie/running/current.naver"; // ��ȭ ����Ʈ
      String url2 = "https://movie.naver.com";
      
      Document doc = null;

      HashMap<String, String> map = new HashMap<String, String>();
      map.put("�׼�", "AC");
      map.put("�ִϸ��̼�", "AN");
      map.put("���/�θǽ�", "RO");
      map.put("���", "DR");
      map.put("��ť���͸�", "DC");
      
      int mpkInt = 1001;
      
      try {
         doc = Jsoup.connect(url).get();
         Elements imgUrl = doc.select(".thumb"); // �� �̹��� url ���
//         System.out.println("imgUrl" + imgUrl);
         //System.out.println(imgUrl.size());
         for (int i=0; i<imgUrl.size(); i++) {
            doc = Jsoup.connect(url2 + imgUrl.get(i).select("a").attr("href")).get(); // �� �̹��� Ŭ�� �� �̵��Ǵ� �ش� ��ȭ ���� ������ document            
            
            // ��ȭ ���� ���� ũ�Ѹ�
            Element el = doc.select(".mv_info_area").get(0);
            String title = el.select(".h_movie > a").first().text(); // Ÿ��Ʋ
            System.out.println("Ÿ��Ʋ: " + title);

            Elements info = el.select(".info_spec > dd").first().select("span"); // ��ȭ ���� - �帣, ���۱�, ����Ÿ��, ������¥
            //System.out.println("info : " + info);

            String genre = null; // �帣
            String country = null; // ���۱�
            String runtime = null; // ����Ÿ��
            String date = null; // ���� ��¥

            if (info.size() != 4) {
               continue;
            }
            for (int j=0; j<info.size(); j++) { // �帣, ���۱�, ����Ÿ��, ������¥
               //String a = info.get(j).text();
               //System.out.println(j + " : " + a);
               genre = info.get(0).text();
               country = info.get(1).text();
               runtime = info.get(2).text();
               date = info.get(3).text();
            }

            if (genre.indexOf(",") > 0) { // �帣 �������� ��� �� ù��° ���� ����
               genre = genre.substring(0, genre.indexOf(","));
            }

            //System.out.println(date.indexOf("��"));
            date = date.substring(0,11);
            date = date.replace(".", "/");
            date = date.replace(" ", "");

            System.out.println("�帣: " + genre);
            System.out.println("���۱�: " + country);
            System.out.println("����Ÿ��: " + runtime);
            System.out.println("������¥: " + date);

            String summary = "";
            if (doc.select(".con_tx").size() > 0) {
               summary = doc.select(".con_tx").first().text();
            } else {
               continue;
            }
            
            System.out.println("�ٰŸ�: " + summary);
            
            String poster = el.select(".poster img").attr("src"); // ��ȭ ������ URL
            poster = poster.substring(0,poster.lastIndexOf("?"));
            System.out.println("������: " + poster);                     
            
            String mpk = "";
            if (map.containsKey(genre)) {
               mpk = map.get(genre) + mpkInt;
            } else {
               mpk = "EX" + mpkInt;
               genre = "ETC";
            }
            mpkInt++;
            System.out.println(mpk);
            
            System.out.println();
            
            // insert into movie2 values (mpk, 'title', 'summary', 'genre', to_date('2021/09/29', 'YYYY/MM/DD'), '������URL');
            String sql = "INSERT INTO MOVIE VALUES (?,?,?,?,TO_CHAR(TO_DATE(?,'YYYY-MM-DD'),'YYYY-MM-DD'),?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, mpk);
            pstmt.setString(2, title);
            pstmt.setString(3, summary);
            pstmt.setString(4, genre);
            pstmt.setString(5, date);
            pstmt.setString(6, poster);
            pstmt.executeUpdate();
         }

      } catch (IOException e) {
         e.printStackTrace();
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         JDBC.close(conn, pstmt);
      }

   }
}