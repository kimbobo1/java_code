<?xml version="1.0" encoding="UTF-8"?>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/xml; charset=UTF-8"
    pageEncoding="UTF-8"%>
<sangpums>
<%
	// sangdata 테이블을 읽어 XML 형식으로 출력
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	try {
		Class.forName("org.mariadb.jdbc.Driver"); 
		
		String url = "jdbc:mariadb://localhost:3306/test2";
		conn = DriverManager.getConnection(url, "root", "bohyun");
		
		pstmt = conn.prepareStatement("select * from sangdata");
		rs = pstmt.executeQuery();
		
		//rs.next();
		//out.print(rs.getString("sang"));
		
		while(rs.next()) {  // 이 다음 행에서 자바를 빠져나온다.
%>
			<sangpum>
				<code><% out.print(rs.getString("code")); // 다음 행과 동일 out.print를 =로 대체가능. 단, 세미콜론은 제거해야 함 %></code>
				<sangirum><%= rs.getString("sang") %></sangirum>
				<su><%= rs.getString("su") %></su>
				<danga><%= rs.getString("dan") %></danga>
			</sangpum>
<%	// 다시 자바로 들어간다.
		}
	} catch(Exception e) {
		System.out.println("에러 : " + e);	
	} finally {
		
	}
%>
</sangpums>