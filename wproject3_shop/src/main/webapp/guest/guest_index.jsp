<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쇼핑몰</title>
<link href="../css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/script.js"></script>
</head>
<body>
<table>
 <tr>
 	<td style="font-size: 24px;">특별 상품 전문 쇼핑몰</td>
 </tr>
</table>
<%@ include file="guest_top.jsp" %>
<table>
<%
if(memid != null){
%>	
	<tr style="text-align:  center;">
		<td>
			<%=memid %>님~오신걸 환영합니다
			<img src="../images/pic2.gif" />
		</td>
	</tr>

<% }else{%>
	<tr style="text-align:  center;">
		<td style="font-size: 24px; background-image: url(../images/pic.jpg);background-size: 100%">
			<br><br><br><br><br><br><br><br><br>
			고객님 이랏샷이마세~~
			<br><br><br>
			로그인 후 이용 바람
		</td>
	</tr>
<% 	
}
%>
</table>

<%@ include file="guest_bottom.jsp" %>
</body>
</html>