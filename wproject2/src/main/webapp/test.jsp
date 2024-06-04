<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="java.time.format.DateTimeFormatter"%>
   <%@page import="java.time.LocalTime"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@include file="jsp3top.jsp" %> <!-- 해당 파일 소스 전체가 실행됨. -->

<h1>include 연습</h1>
include 지시어 : 여러 jsp 파일에서 공통으로 사용할 부분을 별도 파일로 작성 후 필요할 때 포함해서 쓴다.
<pre>
여기는 본문
</pre>
--- 여기는 include action tag로 파일 포함 결과 출력 지역 --- <br>
<jsp:include page="jsp3tag1.jsp" />
<br>
뭔가를 작업...
<br>
<div style="color:red; font-size:30px;">
<jsp:include page="jsp3tag2.jsp">
<jsp:param value="good" name="msg"/> <!-- include 태그 내의 종속적임 -->
</jsp:include>
</div>

<%@ include file="jsp3bottom.jsp" %>
</body>
</html>