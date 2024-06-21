<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="orderMgr" class="pack.order.OrderMgr"></jsp:useBean> 
<%
String flag = request.getParameter("flag");//flag가 넘어온다
String no = request.getParameter("no");
String state = request.getParameter("state");

boolean b = false;  

if(flag.equals("update")){
	b = orderMgr.updateOrder(no, state);
}else if(flag.equals("delete")){ 
	b = orderMgr.deleteOrder(no);
}else{
	response.sendRedirect("ordermanager.jsp");
	
}

if(b){
%>
	<script>
	alert("정상적으로 처리");
	location.href = "ordermanager.jsp";
	</script>
<%
}else{
%>
	<script>
	alert("정상적으로 처리");
	location.href = "ordermanager.jsp";
	</script>
<% 
}
%>   
    
