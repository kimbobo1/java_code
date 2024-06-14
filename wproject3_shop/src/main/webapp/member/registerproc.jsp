<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    request.setCharacterEncoding("utf-8");
    %>
<jsp:useBean id="mbean" class="pack.member.MemberBean" />
<jsp:setProperty property="*" name="mbean"/>
<jsp:useBean id="memberMgr" class="pack.member.MemberMgr" />

<%
boolean b = memberMgr.memberInsert(mbean);

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
if(b){
	out.println("<b>회원가입 축하축하</b><br>");
	out.println("<a href='login.jsp'>회원로그인</a><br>");
}else{
	out.println("<b>회원가입 실패해또또르르르</b><br>");
	out.println("<a href='register.jsp'>가입 재시도</a><br>");
}
%>
</body>
</html>