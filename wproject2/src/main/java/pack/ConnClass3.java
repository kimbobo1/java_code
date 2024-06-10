package pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// DBConnection copy
public class ConnClass3 {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private int recTotal = 0; //레코드 전체 갯수
	private int pageSize = 5; //페이지당 출력 레코드 수
	private int totalPage = 0; //전체 페이지 수

 	public ConnClass3() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (Exception e) {
			System.out.println("DBConnection() ERROR : " + e.getMessage());
		}
	}
/*
	public ArrayList<SangpumDto> getDataAll(String pa) {
		//System.out.println("pa" + pa);
		ArrayList<SangpumDto> list = new ArrayList<SangpumDto>();
		try {
			String url = "jdbc:mariadb://localhost:3306/test2";
			conn = DriverManager.getConnection(url, "root", "bohyun");
			pstmt = conn.prepareStatement("SELECT * FROM sangdata ORDER BY code DESC");
			rs = pstmt.executeQuery();
			
			int startNum = (Integer.parseInt(pa) - 1) * pageSize + 1;
			for(int p = 1; p < startNum; p++) {
				rs.next(); 
				//레코드 포인터 위치 이동 pa:1 (recPointer:0),pa:2 (recPointer:5),pa:3(recPointer:10)
			}
			
			int i = 1;
			while (rs.next() && i <= pageSize) {
				SangpumDto dto = new SangpumDto();
				dto.setCode(rs.getString("code")); // rs.getString(1)과 같은 의미
				dto.setSang(rs.getString("sang"));
				dto.setSu(rs.getString("su"));
				dto.setDan(rs.getString("dan"));
				list.add(dto);
				i++;
			}

		} catch (Exception e) {
			System.out.println("getDataAll() ERROR : " + e.getMessage());
		} finally {
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
				System.out.println("getDataAll() - finally ERROR : " + e2.getMessage());
			}
		}
		return list;
	}
	*/
 	//위 코드를 '스트림 + 람다 표현식'으로 수정
 	public ArrayList<SangpumDto> getDataAll(String pa) {
 		ArrayList<SangpumDto> list = new ArrayList<SangpumDto>();
 		String url = "jdbc:mariadb://localhost:3306/test2";
 		try(Connection conn = DriverManager.getConnection(url, "root", "bohyun");
 			
 			PreparedStatement pstmt=conn.prepareStatement("SELECT * FROM sangdata ORDER BY code DESC");
 			ResultSet rs = pstmt.executeQuery();
 			){
 			int startNum = (Integer.parseInt(pa) - 1) * pageSize + 1;
 			rs.absolute(startNum -1); //레코드 포인터를 시작 위치로 이동
 			
 			list = Stream.iterate(1, i -> i +1)  //1부터 시작하는 스트림 생성
 					.limit(pageSize)  // 스트림의 크기는 pageSize로 제한
 					//map을 통해 각 스트림 요소에 대해 SangpumDto 객체를 생성
 					// rs.next()를 호출, 다음 레코드로 이동하고 각 칼럼 값을SangpumDto에 설정
 					.map(i ->{
 						try {
							if(rs.next()) {
								SangpumDto dto = new SangpumDto();
								dto.setCode(rs.getString("code")); // rs.getString(1)과 같은 의미
								dto.setSang(rs.getString("sang"));
								dto.setSu(rs.getString("su"));
								dto.setDan(rs.getString("dan"));
								return dto;
							}
						} catch (Exception e) {
							System.out.println("map() ERROR : " + e.getMessage());
						}
 						return null;
 					})
 					.filter(Objects::nonNull) //null이 아닌 객체만 필터링
 					.collect(Collectors.toCollection(ArrayList::new));
 					//collect()스트림 결과를 Arraylist로 수집
 		} catch (Exception e) {
			System.out.println("getDataAll() ERROR : " + e.getMessage());
		}
 		
 		return list;
 	}
 	
	public int prepareTotalPage() {
		try {
			String url = "jdbc:mariadb://localhost:3306/test2";
			conn = DriverManager.getConnection(url, "root", "bohyun");
			pstmt = conn.prepareStatement("select count(*) from sangdata");
			rs = pstmt.executeQuery();
			
			if(rs.next()) recTotal = rs.getInt(1);
			//전체 페이지 수 구하기
			totalPage = recTotal / pageSize;
			if(recTotal % pageSize !=0) totalPage += 1; //자투리 계산
			System.out.println("전체 페이지 수 :" + totalPage);
		} catch (Exception e) {
			// TODO: handle exception
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
		
		return totalPage;
	}

	public void insertData(SangpumBean bean) {
		// System.out.println(bean.getSang() + " " + bean.getSu() + " " +
		// bean.getDan());
		try {
			String url = "jdbc:mariadb://localhost:3306/test2";
			conn = DriverManager.getConnection(url, "root", "bohyun");
			
			//새상품 코드를 만들기
			String sql = "select max(code) from sangdata";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			rs.next();
			int maxCode = rs.getInt(1);
			System.out.println("현재 가장 큰 상품 번호: " + maxCode);
			
			//신상 insert
			pstmt = conn.prepareStatement("insert into sangdata values(?,?,?,?)");
			pstmt.setInt(1, maxCode + 1);
			pstmt.setString(2, bean.getSang());
			pstmt.setString(3, bean.getSu());
			pstmt.setString(4, bean.getDan());
			pstmt.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
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
				System.out.println("getDataAll() - finally ERROR : " + e2.getMessage());
			}
		}
	}
}
