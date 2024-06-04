<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String msg = request.getParameter("msg");  // 세 번째 내장객체 request.  // out, exception
out.println("msg는 " + msg); // include의 경우는 받을 수 없다?
%>
