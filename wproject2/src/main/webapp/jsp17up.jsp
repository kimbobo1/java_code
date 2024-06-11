<%@page import="pack.SangpumDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String code = request.getParameter("code"); %>

<jsp:useBean id="connP" class="pack.ConnPooling" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
SangpumDto dto = connP.updateData(code); //코드를 들고간다
if(dto == null){ //
%>
	<script type="text/javascript">
		alert("등록된 상품 코드가 아닙니다.\n수정 불가!!");
		//history.back(); //이전으로 돌아가기
		location.href="jsp17dbcp.jsp"; //목록으로 돌아가기
	</script>

<% 	
	return;
}
%>

**상품 수정**<br>
<form action="jsp17upok.jsp" method="post">
코드 :<%=dto.getCode() %><br> <!-- code는 보여만주면댐 수정대상 x -->
<input type="hidden" name="code" value="<%=dto.getCode()%>"> <!-- 코드는 hidden으로 넘긴다 -->
품명 : <input type="text" name="sang" value="<%= dto.getSang()%>"><br>
수량 : <input type="text" name="su" value="<%= dto.getSu()%>"><br>
단가 : <input type="text" name="dan" value="<%= dto.getDan()%>"><br>
<br>
<input type="submit" value="자료 수정">
<input type="button" value="수정 취소" onclick="javascript:location.href='jsp17dbcp.jsp'">
</form>
</body>
</html>