<?xml version="1.0" encoding="UTF-8"?>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/xml; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jikwons>
<%
	String gubun = request.getParameter("gubun");
	String name = request.getParameter("name");
	// sangdata 테이블을 읽어 XML 형식으로 출력
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	try {
		Class.forName("org.mariadb.jdbc.Driver"); 
		
		String url = "jdbc:mariadb://localhost:3306/test2";
		conn = DriverManager.getConnection(url, "root", "bohyun");
		
		String sql = "select jikwon_no,jikwon_name,jikwon_jik,jikwon_pay from jikwon";
		if(gubun.equals("all")){
			pstmt = conn.prepareStatement(sql);
		} else {
			pstmt = conn.prepareStatement(sql += " where jikwon_name like ?");
			pstmt.setString(1, name + "%");
		}
		rs = pstmt.executeQuery();
	
		
		while(rs.next()) {  // 이 다음 행에서 자바를 빠져나온다.
%>
			<jikwon>
				<sabun><% out.print(rs.getString("jikwon_no")); // 다음 행과 동일 out.print를 =로 대체가능. 단, 세미콜론은 제거해야 함 %></sabun>
				<irum><%= rs.getString("jikwon_name") %></irum>
				<jik><%= rs.getString("jikwon_jik") %></jik>
				<pay><%= rs.getString("jikwon_pay") %></pay>
			</jikwon>
<%	// 다시 자바로 들어간다.
		}
	} catch(Exception e) {
		System.out.println("에러 : " + e);	
	} finally {
		
	}
%>
</jikwons>