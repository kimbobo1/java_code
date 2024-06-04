<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 현재 jsp 파일은 비즈니즈 로직 처리용으로 출력에는 참여하지 않음.
String data = request.getParameter("datak");
String msg = "Mr. " + data;

// 1. redirect 방식으로 파일 호출
//response.sendRedirect("jsp6invoked.jsp?data=" + msg);
// 값이 여러 개인 경우 & 사용하여 쿼리스트링 작성
//response.sendRedirect("jsp6invoked.jsp?data=" + msg + "&data2=" + msg2);

// 2. forward 방식으로 파일 호출
request.setAttribute("datas", msg);

ArrayList<String> list = new ArrayList<String>();
list.add("mouse");
list.add("pen");
list.add("book");
request.setAttribute("product", list);  // request의 경우, 컨테이너 기능을 가지고 있음. list와 같은 값들은 쿼리스트링에 전달할 수 없다.

// 자바의 경우 getRequestDispatcher() 메소드 사용
//request.getRequestDispatcher("jsp6invoked.jsp").forward(request, response);
%>

<%-- jsp의 경우 액션 태그 <jsp:forward/> 사용 --%>
<jsp:forward page="jsp6invoked.jsp"></jsp:forward>