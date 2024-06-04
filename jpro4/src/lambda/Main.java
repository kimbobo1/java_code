package lambda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {
	Connection conn;
	PreparedStatement pstmt, pstmt2, pstmt3, pstmt4;
	ResultSet rs, rs2, rs3, rs4;

	public Main() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://localhost:3306/mydb";
			conn = DriverManager.getConnection(url, "root", "123"); // 연결 객체를 만듦
			
			Scanner sc = new Scanner(System.in);
			System.out.print("부서명 : ");
            String buser_name = sc.next();

            String sql1 = "select buser.buser_name, buser_tel, jikwon_no, jikwon_name, jikwon_jik, jikwon_gen from jikwon "
            		+ "left outer join buser on jikwon.buser_num=buser.buser_no where buser_name=?";

            pstmt = conn.prepareStatement(sql1);
            pstmt.setString(1,buser_name);
            rs = pstmt.executeQuery();
            if(rs.next()) {
            	System.out.println(rs.getString(1) + " 전화번호는 " + rs.getString(2));
            }

            String sql2 = "select jikwon_no, jikwon_name, jikwon_jik, jikwon_gen from jikwon "
            		+ "left outer join buser on jikwon.buser_num=buser.buser_no where buser_name=?";
            
            pstmt2 = conn.prepareStatement(sql2);
            pstmt2.setString(1,buser_name);
            rs2 = pstmt2.executeQuery();
            System.out.println("\n사번"+ "\t"+"이름"+ "\t"+"직급"+ "\t"+"성별"+ "\t");
            while(rs2.next()) {
            	System.out.println(rs2.getString(1) + "\t" + rs2.getString(2)+ "\t"+ rs2.getString(3)+ "\t"+ rs2.getString(4));
            }
            
            String sql4 = "select jikwon_gen, count(jikwon_gen) from jikwon left outer join buser on jikwon.buser_num=buser.buser_no where buser_name=? GROUP BY jikwon_gen";
            pstmt4 = conn.prepareStatement(sql4);
            pstmt4.setString(1,buser_name);
            rs4 = pstmt4.executeQuery();
            System.out.print("\n인원수는 남직원 : ");
            if(rs4.next()) System.out.print(rs4.getString(2));
            System.out.print("명, 여직원 : ");
            if(rs4.next()) System.out.print(rs4.getString(2));
            System.out.println("명");
            
            String sql3 = "select avg(jikwon_pay), jikwon_gen from jikwon left outer join buser on jikwon.buser_num=buser.buser_no where buser_name=? GROUP BY jikwon_gen";
            pstmt3 = conn.prepareStatement(sql3);
            pstmt3.setString(1,buser_name);
            rs3 = pstmt3.executeQuery();
            System.out.print("연봉 평균은 남직원 : ");
            if(rs3.next()) System.out.print(rs3.getInt(1));
            System.out.print(", 여직원 : ");
            if(rs3.next()) System.out.print(rs3.getInt(1));

            int man = Integer.parseInt(rs3.getString(1));
            System.out.println(man);
            
		} catch (Exception e) {
			// TODO: handle exception
		}  finally {
			try {
				if(rs != null) rs.close();
				if(rs2 != null) rs2.close();
				if(rs3 != null) rs3.close();
				if(rs4 != null) rs4.close();
				if(pstmt != null) pstmt.close();
				if(pstmt2 != null) pstmt2.close();
				if(pstmt3 != null) pstmt3.close();
				if(pstmt4 != null) pstmt4.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

	public static void main(String[] args) {
		new Main();
	}

}
