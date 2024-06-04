<!-- Service 메소드가 아래 코드를 감싸고 있다. 다시 말해 아래 내용은 service() 메소드 내 기술한다. -->

<!-- 
protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
-->
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP의 이해</title>
</head>
<body>
	<!-- 출력용 자바(JSP), 내부 처리용 자바(서블릿) -->
	<h1>JSP의 이해</h1>
	<%  // scriptlet : Java 코드를 입력할 수 있는 영역
		String name = "한국인";      // 지역변수 -> service 메소드 내 선언되어 있으므로
		System.out.println(name);  // 서버 컴의 콘솔로 출력(개발자가 뭔가 출력하고자 할 때 사용)
		// out : PrintWriter 객체 타입의 내장 객체 중 하나(총 9개), out 객체는 숨겨져 있다.
		out.println(name + "님의 홈페이지임을 선언하노라!");  // 클라이언트 브라우저로 출력
	%>
	<br>
	<!-- expression(표현식), 출력문 하나만 작성 가능, 출력문 뒤에 세미콜론(;)은 적지 않음 -->
	<%= name + "님이 홈페이지임을 선언하노라!" %> 
	<br>
	<h1>자바 만세</h1>
	<h2>자바 만세</h2>
	<h3>자바 만세</h3>
	<h4>자바 만세</h4>
	<h5>자바 만세</h5>
	<h6>자바 만세</h6>
	
	<%
		for(int i = 1; i <= 6; i++) {
			out.print("<h" + i + ">");
			out.print("자바 파이팅!");
			out.println("</h" + i + ">");
		}
	%>
	현재 날짜 및 시간 <%= new Date() %>
	<br>
	<%
		int a = 0, sum = 0;
		do {
			a++;
			sum += a;
		} while(a < 10);
		
		out.println("10까지의 합은 " + sum);
	%>
	<br>
	<%= "10까지의 합은 " + sum %>
	<hr>
	<%= name + "님의 전화번호는 " + phone %>
	
	<%!  // <%! : 선언 -> 이곳에 선언한 변수는 전역 변수(멤버 필드, 멤버 메소드 해당)
		String phone = "111-1234";  // 전역(멤버)변수
		
		// 클래스 멤버 메소드
		public int dataAdd(int su1, int su2) {
			return su1 + su2;
		}
	%>
	
	<!--  현재 위치는 service() 메소드 안. -->
	<% 
		/* 에러 : 메소드 내에 메소드는 사용할 수 없다.
		public int dataAdd(int su1, int su2) {
			return su1 + su2;
		}
	*/
	%>
	<br>
	<%= dataAdd(10, 20) %>
</body>
</html>
<!-- 
} 
-->