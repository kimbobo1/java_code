<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");
	String num = request.getParameter("num");
	String name = request.getParameter("name");
%>

{"gogek":
[
<%
	// sangdata 테이블을 읽어 JSON 형식으로 출력
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	try {
		Class.forName("org.mariadb.jdbc.Driver"); 
		
		String url = "jdbc:mariadb://localhost:3306/test";
		conn = DriverManager.getConnection(url, "root", "123");
		
		String sql = "";
		
		
		sql = "select gogek_name, gogek_tel, case when gogek_jumin like '_______1%' then '남' when gogek_jumin like '_______2%' then '여' end as gogek_gen from gogek inner join jikwon on gogek_damsano=jikwon_no where jikwon_no=? and jikwon_name=?";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,num);
		pstmt.setString(2,name);
		rs = pstmt.executeQuery();
			
		String result = "";
		
		while(rs.next()) {  
			result += "{";
			
			result += "\"gogek_name\":\"" + rs.getString("gogek_name") + "\",";
			result += "\"gogek_tel\":\"" + rs.getString("gogek_tel") + "\",";
			result += "\"gogek_gen\":\"" + rs.getString("gogek_gen") + "\"";
			
			result += "},";
		}
		if(result.length() > 0) {
			result = result.substring(0, result.length() - 1);
			// 전체길이 - 1 만큼만 반환을 받는다. 
		}
		out.print(result);
	} catch(Exception e) {
		System.out.println("에러 : " + e);	
	} finally {
		try {
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) { }
	}
%>
]
}