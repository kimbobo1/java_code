package pack;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class DbTest2CRUD {
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private Properties prop = new Properties(); // 비밀번호, 사용자, 유저 등 개인정보를 포함
	
	public DbTest2CRUD() {  // secure coding의 하나로 연결정보 별도 저장 후 읽기
		try {
			prop.load(new FileInputStream("C:\\work\\jsou\\jpro2\\src\\pack\\dbtest2.properties")); // properties 
			
			Class.forName(prop.getProperty("driver"));
			conn = DriverManager.getConnection(
					prop.getProperty("url"),
					prop.getProperty("user"),
					prop.getProperty("passwd"));
			
			stmt = conn.createStatement();
			
			String sql = "";
			
			// 자료 추가
			// autocommit이 기본 설정
			sql = "insert into sangdata values(5,'새우깡',55,3000)";
			stmt.executeUpdate(sql); // insert, update, delete는 executeUpdate를 사용하여 실행, 여기서 바로 오토커밋이 실행됨
			
			
//			// autocommit을 수동으로 전환해 작업 : Transaction 처리가 필요
//			conn.setAutoCommit(false);  // autocommit을 해제(수동)
//			sql = "insert into sangdata values(5,'새우깡',7,3000)";
//			stmt.executeUpdate(sql);  // 여기서 트랜잭션이 시작됨.
//			sql = "insert into sangdata values(7,'고구마깡',17,2000)";
//			stmt.executeUpdate(sql);
//			//conn.rollback();  // Transaction 종료 - 클라이언트에서 입력한 자료 취소
//			conn.commit(); // Transaction 종료 - 클라이언트에서 입력한 자료가 원격 DB에 저장된다.
//			conn.setAutoCommit(true);  // autocommit(자동)
//			// insert 이므로 다시 실행하면 primary key 가 중복된다. -> 에러 발생
			
			// 자료 수정
//			sql = "update sangdata set sang='데일리 콤부차',su=12,dan=8000 where code=5";  // 덮어써도 에러가 발생하지 않음
//			stmt.executeUpdate(sql);
			
			// 자료 삭제
			sql = "delete from sangdata where code >=5";
			//stmt.executeUpdate(sql);
			// insert, update, delete는 수행 후 처리 수 만큼 행의 개수를 반환
			int result = stmt.executeUpdate(sql);  // update, delete는 여러 개가 리턴될 수 있다. insert는 0-1개가 리턴될 수 있다.  ******
			System.out.println("result : "+ result);  // result는 1을 리턴한다.
			if(result == 0) System.out.println("삭제 실패");
			
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
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	
	public static void main(String[] args) {
		new DbTest2CRUD();
	}

}
