<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="memberMgr" class="pack.member.MemberMgr"></jsp:useBean>

<%


String id = request.getParameter("id");
String passwd = request.getParameter("passwd");

boolean b = memberMgr.loginCheck(id, passwd);

if(b){
	session.setAttribute("idkey", id);
	response.sendRedirect("login.jsp");
	
}else{
	response.sendRedirect("logfail.html");
}
%>