<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    session="true"
    
    %>
<%
//로그인 성공하면 loginok,실패하면 loginfail로 이동하는 로직 처리 

String id = request.getParameter("id");
String pwd = request.getParameter("pwd");

if((id.equals("admin") && pwd.equals("111"))|| 
(id.equals("user") && pwd.equals("222"))){
	session.setAttribute("id", id); //클라이언트마다 다른값을 줄수있다
	response.sendRedirect("jsp8loginok.jsp");//세션은 만들수록 부담이 커짐 
	
	//세션이 아니라 request를 사용한다면
	//request.setAttribute("id", id); //클라이언트마다 다른값 못줌
	//request.getRequestDispatcher("jsp8loginok.jsp").forward(request,response);
}else{
	response.sendRedirect("jsp8loginfail.html");
}
%>