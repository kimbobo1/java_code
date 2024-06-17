<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="cartMgr" class="pack.order.CartMgr" scope="session" />
<!-- session 카트 mgr에 세션이 살아있는동안 살아있다 -->
<jsp:useBean id="order" class="pack.order.OrderBean"></jsp:useBean>
<jsp:setProperty property="*" name="order" />

<%
String orderFlag = request.getParameter("flag"); //구매목록 보기,수정,삭제,판단용
String id = (String)session.getAttribute("idkey");
out.print(order.getProduct_no() + " , 주문 수량 :" + order.getQuantity());
%>