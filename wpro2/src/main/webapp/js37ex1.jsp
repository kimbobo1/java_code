<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
String gender = request.getParameter("gen");
%>

{"jikwon": 
[
<%
Connection conn = null;
PreparedStatement pstmt = null;
ResultSet rs = null;

try {
	Class.forName("org.mariadb.jdbc.Driver"); 
	
	String url = "jdbc:mariadb://localhost:3306/test";
	conn = DriverManager.getConnection(url, "root", "123");
	
	String sql = "";
	if(!gender.equals("전체")) {
		sql = "select jikwon_no, jikwon_name, jikwon_jik, substr(jikwon_ibsail,1,4) ibsail from jikwon where jikwon_gen=?";
	} else {
		sql = "select jikwon_no, jikwon_name, jikwon_jik, substr(jikwon_ibsail,1,4) ibsail from jikwon";
	}
	pstmt = conn.prepareStatement(sql);
	pstmt.setString(1,gender);
	rs = pstmt.executeQuery();
	
	String result = "";
	
	while(rs.next()) {  
		result += "{";
		
		result += "\"no\":\"" + rs.getString("jikwon_no") + "\",";
		result += "\"name\":\"" + rs.getString("jikwon_name") + "\",";
		result += "\"jik\":\"" + rs.getString("jikwon_jik") + "\",";
		result += "\"ibsail\":\"" + rs.getString("ibsail") + "\"";
		
		result += "},";
	}
	if(result.length() > 0) {
		result = result.substring(0, result.length() - 1);
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