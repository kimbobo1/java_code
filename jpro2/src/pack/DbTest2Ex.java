package pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

// 키보드로 부서번호를 입력받아 해당 부서에 근무하는 직원자료 출력
// 부서번호: 10 <==

public class DbTest2Ex {
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	
	public DbTest2Ex() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");  // 외부에서 가져온 파일, 예외 처리 필요			
		} catch (Exception e) {
			System.out.println("로딩 실패 : " + e);
			return;  // 로딩 실패 시, 생성자를 탈출하여 go를 출력하지 못한다.
		}
		
		try {
			String url = "jdbc:mariadb://localhost:3306/test";    // commmand에서 로그인과 use test; 까지 실행한 상태.
			conn = DriverManager.getConnection(url, "root", "123");    //.getConnection(url, 사용자, 비밀번호) // 싱글턴?
			
		} catch (Exception e) {
			System.out.println("연결 실패 : " + e.getMessage());   // 포트번호나 비밀번호가 틀리다면 DB 접근 불가
			return;
		}
		
		try {
			stmt = conn.createStatement();
			Scanner sc = new Scanner(System.in);
			System.out.print("부서 번호 입력: ");
			String buser_num=sc.next();
			System.out.println();
			rs = stmt.executeQuery("select jikwon_no as 사번,jikwon_name as 이름, buser_name as 부서명, jikwon_jik as 직급, jikwon_pay as 연봉 from jikwon inner join buser on jikwon.buser_num=buser.buser_no where buser_num=" + buser_num);
			
			int cnt = 0;
			System.out.println("사번\t이름\t부서명\t직급\t연봉");
			System.out.println("------------------------------------------");
			while(rs.next()) {
				int jikwon_no = rs.getInt("사번");  
				String jikwon_name = rs.getString("이름");
				String buser_name = rs.getString("부서명");
				String jikwon_jik = rs.getString("직급");
				int jikwon_pay = rs.getInt("연봉"); 
				System.out.println(jikwon_no + "\t" + jikwon_name + "\t" + buser_name + "\t" + jikwon_jik + "\t" + jikwon_pay);
				cnt++;
			}

			System.out.println("건수 : " + cnt);
			sc.close();
		} catch (Exception e) {
			System.out.println("처리 실패 :" + e);
		} finally {
			try {
				if(rs != null) rs.close(); // rs가 이미 닫혀있을 수 있으므로
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	
	public static void main(String[] args) {
		new DbTest2Ex();

	}

}
