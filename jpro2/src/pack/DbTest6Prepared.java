package pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// PreparedStatement : 선처리 방식이 가능, SQL문에 입력 자료 적용 시 ? 연산자 가능
public class DbTest6Prepared {
	Connection conn;
	PreparedStatement pstmt;  // 선처리 방식
	ResultSet rs;
	
	public DbTest6Prepared() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			
			String url = "jdbc:mariadb://localhost:3306/mydb";
			conn = DriverManager.getConnection(url, "root", "123");  // 연결 객체를 만듦
			
			String sql = "";
			
			// 자료 추가
			/*
			sql = "insert into sangdata values(?,?,?,?)";  
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "10");
			pstmt.setString(2, "신상품");
			pstmt.setInt(3, 12);
			pstmt.setString(4, "5000");
			
			int re = pstmt.executeUpdate();
			System.out.println("insert 반환 값 : " + re);*/
			
			// 자료 수정
			sql = "update sangdata set sang=?, su=? where code=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "아메리카노");
			pstmt.setInt(2, 33);
			pstmt.setInt(3, 1);
			int re = pstmt.executeUpdate();
			//System.out.println("update 반환 값 : " + re);
			
			// 자료 삭제
			/*sql = "delete from sangdata where code=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 10);
			if(pstmt.executeUpdate() >= 1) {
				System.out.println("삭제 성공");  // 한 번 실행하면 삭제가 완료되었으므로, 다시 실행하면 삭제 실패
			} else {
				System.out.println("삭제 실패"); 
			}*/
			
			// 전체 자료 읽기
			sql = "select * from sangdata";
			//stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); -> statement를 사용한 경우
			pstmt = conn.prepareStatement(sql);   // 객체를 만들면서 쿼리를 주고 다음문장에서는 쿼리를 줄 필요가 없다.
			//rs = stmt.executeQuery("select jikwon_no,jikwon_name from jikwon"); -> statement를 사용한 경우
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				System.out.println(rs.getString(1) + "\t" + 
						rs.getString(2) + "\t" + 
						rs.getString(3) + "\t" +
						rs.getString(4));
			}
			
			System.out.println();
			
			// 부분 자료 읽기
//			String no = "2";   // 외부에서 받았다고 가정
//			//sql = "select * from sangdata where code=" + no; // SQL Injection 공격 대상이 되므로 이렇게 사용하면 안됨!
//			// secure coding guideline에 맟줘서 코딩한다면 다음과 같다.
//			sql = "select * from sangdata where code=?";
//			
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, no);  // 첫 번쨰 물음표에 no가 매핑   // 만약 변수 no의 타입에 따라 타입이 변경됨. 여기서는 no가 String 타입이므로 setString을 사용하였다. 컬럼의 타입과 맞추면 안된다.
//			rs = pstmt.executeQuery();
//			if(rs.next()) {
//				System.out.println(rs.getString("code") + "\t" + 
//						rs.getString("sang") + "\t" + 
//						rs.getString("su") + "\t" +
//						rs.getString("dan"));
//			}
		} catch (Exception e) {
			System.out.println("err : " + e);
		}
	}
	
	public static void main(String[] args) {
		new DbTest6Prepared();
	}

}
