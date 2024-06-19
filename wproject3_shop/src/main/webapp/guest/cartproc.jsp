<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="cartMgr" class="pack.order.CartMgr" scope="session" />
<!-- session 카트 mgr에 세션이 살아있는동안 살아있다 -->
<jsp:useBean id="order" class="pack.order.OrderBean"></jsp:useBean>
<jsp:setProperty property="*" name="order" />

<%
String orderFlag = request.getParameter("flag"); //구매목록 보기,수정,삭제,판단용
String id = (String)session.getAttribute("idkey");
//out.print(order.getProduct_no() + " , 주문 수량 :" + order.getQuantity());

if(id == null){
	response.sendRedirect("../member/login.jsp"); //로그인을 안한 경우
}else{
	if(orderFlag == null){
		//cart에 주문 상품 담기 
		order.setId(id); //order :id, quantity , product_no
		cartMgr.addCart(order); 
%>
	<script>
	alert("장바구니에 담았습니다");
	location.href="cartlist.jsp"; //cart 등록된 주문상품 목록 보기
	</script>
<%		
		
	}else if(orderFlag.equals("update")){
		//업데이트
		order.setId(id); 
		cartMgr.updateCart(order);
%>
	<script>
	alert("장바구니에 내용을 수정했습니다");
	location.href="cartlist.jsp"; //cart 등록된 주문상품 목록 보기
	</script>
<%		
	}else if(orderFlag.equals("del")){
		//삭제
		cartMgr.deleteCart(order);
		
	
%>
	<script>
	alert("해당 상품의 주문을 삭제했오");
	location.href="cartlist.jsp"; //cart 등록된 주문상품 목록 보기
	</script>
<%
	}
}
%>