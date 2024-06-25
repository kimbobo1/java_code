<%@page import="pack.user.UserDto"%>
<%@page import="pack.user.UserMgr"%>
<%@page import="pack.product.ProductDto"%>
<%@page import="pack.product.ProductMgr_u"%>
<%@ page session="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<jsp:useBean id="userMgr" class="pack.user.UserMgr" />
<jsp:useBean id="productMgr" class="pack.product.ProductMgr_u"></jsp:useBean>
<%

String id = (String)session.getAttribute("idKey");
if (id == null) {
	id = "user1";
//	response.sendRedirect("login.jsp");
}
    // 세션에서 사용자 아이디 가져오기
    String userId = (String) session.getAttribute("userId");
    String productId = request.getParameter("productId"); // 예시로 productId를 URL 파라미터로 받음
    UserDto user = null;
    ProductDto product = null;

    if (userId != null) {
        user = userMgr.getUserById(userId); 
        product = productMgr.getProductByName("productName"); 
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상세 보기</title>
<script type="text/javascript">
function addToCart() {
    alert("장바구니에 담겼습니다 쇼핑하시겠습니까!");
    window.open('cartpage.jsp', '_self');
}
</script>
</head>
<body>
<h2>**주문자 상세 페이지*</h2>
<% if (user != null && product != null) { %>
<form action="cartproc.jsp">
<table style="width: 100%">
    <tr><td>이름 : <%= user.getName() %></td></tr>
    <tr><td>전화 : <%= user.getTel() %></td></tr>
    <tr><td>이메일 : <%= user.getEmail() %></td></tr>
    <tr><td>우편번호: <%= user.getZipcode() %></td></tr>
    <tr><td>주소 : <%= user.getAddress() %></td></tr>
    <tr><td>상품 이름 : <%= product.getName() %></td></tr>
    <tr><td>상품 가격 : <%= product.getPrice() %></td></tr>
    <tr><td>수량 : <%= product.getStock() %></td></tr>
    <tr><td><button type="button" onclick="addToCart()">장바구니에 추가</button></td></tr>
</table>
</form>
<% } else { %>
<p>로그인이 필요하거나 잘못된 상품 정보입니다. <a href="loginForm.jsp">로그인 페이지로 이동</a></p>
<% } %>
<hr>
</body>
</html>
