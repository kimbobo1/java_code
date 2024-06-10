package pack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ConnPooling {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private DataSource ds;
	
	public ConnPooling() {
//		//JNDI Java Naming and Directory Interface.
//		이름지정 및 디렉토리 서비스에서 제공하는 데이터 및 객체를 참조(lookup)하기 위한 자바 API이다. 
//		일반적으로 자바 애플리케이션을 외부 디렉터리 서비스(DB server,LDAP server..)에 연결할 때 쓰이는데
//		그중에서도 데이터베이스 연결에 가장 많이 쓰인다. 
		try {
			//context.xml에 설정된 DB 연결 정보 읽기 pool에서 connection 개체를 읽음
			Context context = new InitialContext();
			ds = (DataSource)context.lookup("java:comp/env/jdbc_maria");
		} catch (Exception e) {
			System.out.println("db 연결 실패" + e);
		}
		
	}
	
	public ArrayList<SangpumDto> getdataAll(){
		ArrayList<SangpumDto> list = new ArrayList<SangpumDto>();
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement("select * from sangdata");
			rs= pstmt.executeQuery();
			
			while(rs.next()) {
				SangpumDto dto =new SangpumDto();
				dto.setCode(rs.getString(1));
				dto.setSang(rs.getString(2));
				dto.setSu(rs.getString(3));
				dto.setDan(rs.getString("dan"));
				list.add(dto);
			}
		} catch (Exception e) {
			System.out.println("에러"+e);
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return list;
	}
	public boolean insertData(SangpumBean bean) {
		boolean b = false;
		try {
			conn= ds.getConnection();
			
			
			//신상 번호 구하기 
			String sql = "select max(code) as max from sangdata";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			int maxCode = 0;
			if(rs.next()) {
				maxCode = rs.getInt("max");
			}
			maxCode++; //신상 코드번호
			
			
			//추가 작업
			sql = "insert into sangdata(code,sang,su,dan) values(?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, maxCode);
			pstmt.setString(2, bean.getSang());
			pstmt.setString(3, bean.getSu());
			pstmt.setString(4, bean.getDan());
			int result = pstmt.executeUpdate();
			if(result == 1) b = true;
			
			//...
			
		} catch (Exception e) {
			System.out.println("insertData err" + e);
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return b;
	}
}
