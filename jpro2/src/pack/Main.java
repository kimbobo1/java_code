package pack;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class Main {
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private Properties prop = new Properties(); // 비밀번호, 사용자, 유저 등 개인정보를 포함

	public Main() {
		try {
			prop.load(new FileInputStream("C:\\work\\jsou\\jpro2\\src\\pack\\dbtest2.properties")); // properties

			Class.forName(prop.getProperty("driver"));
			conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("user"),
					prop.getProperty("passwd"));

			stmt = conn.createStatement();
			
			String sql = "";
			
			conn.setAutoCommit(false);  // autocommit을 해제(수동)
			
			// insert
			sql = "insert into sangdata values(5,'새우깡',55,3000)";
			stmt.executeUpdate(sql); 
			
			// update
			sql = "update sangdata set sang='데일리 콤부차',su=12,dan=8000 where code=5";  
			stmt.executeUpdate(sql);
			
			// delete
			sql = "delete from sangdata where code >=5";
			
			int result = stmt.executeUpdate(sql);  // update, delete는 여러 개가 리턴될 수 있다. insert는 0-1개가 리턴될 수 있다.  ******
			System.out.println("result : "+ result);  // result는 1을 리턴한다.
			if(result == 0) System.out.println("삭제 실패");
			
//			conn.rollback();  // Transaction 종료 - 클라이언트에서 입력한 자료 취소
			conn.commit(); // Transaction 종료 - 클라이언트에서 입력한 자료가 원격 DB에 저장된다.
			conn.setAutoCommit(true);  // autocommit(자동)
			
			// 모든 자료 읽기
			sql = "select * from sangdata order by code desc";
			rs = stmt.executeQuery(sql);   // select 만 executeQuery를 사용하여 실행
			int cou = 0;
			while(rs.next()) {
				System.out.println(rs.getString("code") + " " + rs.getString("sang") + " " + rs.getString("su") + " " + rs.getString("dan"));
				cou++;
			}
			System.out.println("전체 자료 수 : " + cou);
			
			// 부분 자료 읽기
			sql = "select * from sangdata where code=1";
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				System.out.println(rs.getString("code") + " " + rs.getString("sang") + " " + rs.getString("su") + " " + rs.getString("dan"));
			} else {
				System.out.println("해당 자료 없음");
			}
			
		} catch (Exception e) {
			System.out.println("err : " + e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e2) { }
		}
	}

	public static void main(String[] args) {
		new Main();

	}

}
