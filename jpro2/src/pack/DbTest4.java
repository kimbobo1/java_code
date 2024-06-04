package pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbTest4 {
	private Connection conn;
	private Statement stmt;  // SQL 문 실행을 위해 사용
	private ResultSet rs;
	
	public DbTest4() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			
			String url = "jdbc:mariadb://localhost:3306/mydb";
			conn = DriverManager.getConnection(url, "root", "123");
			stmt = conn.createStatement();
			
			boolean b = false;
		
			// execute : executeQuery(), executeUpdate()를 하나로 처리
			
			// update
			b = stmt.execute("update sangdata set sang='마우스' where code=4");  // false -> select인지 아닌지를 판단(?)
			System.out.println("update b : " + b);
			
			int result = stmt.getUpdateCount();
			System.out.println("result : " + result);
			if(result >= 1) {
				System.out.println("작업 성공");
			} else {
				System.out.println("작업 실패");
			}
			
			// select
			b = stmt.execute("select * from sangdata");  
			System.out.println("select b : " + b);  // 자료를 읽어오므로 true
			// 반환 값이 ‘true’이면, getResultSet 메서드를 사용함으로써 결과 집합을 얻을 수 있습니다.
			rs = stmt.getResultSet();  // b가 true인 경우 실행(?)
			while(rs.next()) {
				System.out.println(rs.getString(1) + "\t" + 
									rs.getString(2) + "\t" + 
									rs.getString(3) + "\t" +
									rs.getString(4));
			}
			
		} catch (Exception e) {
			System.out.println("DbTest4 실패 :" + e);
		}
	}
	
	public static void main(String[] args) {
		new DbTest4();
	}
}
