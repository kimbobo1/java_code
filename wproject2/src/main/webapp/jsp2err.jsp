<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
현재 jsp 문서는 예기치 않은 문서가 발생한 경우, 대체용<br>
고객님 릴렉스~<br>
<%= "에러 원인 : " + exception.getMessage() %> <!-- exception : 9개의 내장 객체 중 하나 -->
...
</body>
</html>