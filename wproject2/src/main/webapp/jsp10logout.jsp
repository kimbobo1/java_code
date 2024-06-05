<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
//쿠키에서 JWT를 제거
Cookie jwtCookie =new Cookie("jwt", "");
jwtCookie.setMaxAge(0); //쿠키제거
response.addCookie(jwtCookie);

response.sendRedirect("jsp10jwtlogin.html");
%>