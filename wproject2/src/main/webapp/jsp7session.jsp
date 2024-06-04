<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
String id = request.getParameter("id");

//request.setAttribute("idKey", id);      // 현재 jsp 파일에서만 유효
//application.setAttribute("idKey", id);  // 현재 서비스 중 모두에게 유효

session.setAttribute("idKey", id);      // 세션을 참조하는 파일에서만 유효, 세션이 생성된다.
session.setMaxInactiveInterval(10);     // session의 유효 시간 설정

// 서버가 클라이언트 컴에 sessionid를 쿠키에 저장해 둠. 
// 이후, 클라이언트가 서버에 정보 요청 시 sessionid가 담긴 cookie를 들고 감.
// 갑과 을이라는 세션의 위치가 다른데 갑이라는 요청은 갑 세션으로 이동해야 하는데 이름표를 달고 가야한다. 이 이름표가 sessionid이다. 이 값은 중복이 불가하다.


// a.html에서 
// request의 경우,
// request와 response를 넘겨주면 다른 파일(b.html)에서 getAttribute()에서 값을 받을 수 있음. response를 넘겨주지 않으면 받을 수 없음.

// application의 경우
// 해당 파일이 있는 모든 파일은 인식이 가능하다. 

//session의 경우
// 클라이언트가 누구인지 중요하다. id 값에 '갑'을 입력하고 b.jsp를 불러 setAttribute를 하면 '갑'이 나온다., 클라이언트 별로 값이 달라진다.
// 을 이라는 사람이 다시 id값에 '을' 을 넣으면 '을'이 출력?
		

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>jsp7session</title>
</head>
<body>
	<h2>세션 연습</h2>
	<form action="jsp7session2.jsp">
	보고 싶은 영화 선택 : <br>
	<input type="radio" name="movie" value="원더랜드" checked="checked">원더랜드
	&nbsp;&nbsp;
	<input type="radio" name="movie" value="퓨리오사">퓨리오사
	&nbsp;&nbsp;
	<input type="radio" name="movie" value="설계자">설계자
	<input type="submit" value="결과보기">
	</form>
</body>
</html>