<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalTime"%><!-- jsp3include.jsp에 작성할 경우, 여기서는 1,2행을 제거해도 문제 없다. -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<hr>
<%
LocalTime sigan = LocalTime.now();
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH시 mm분 ss초");
out.println(sigan.format(formatter));
%>