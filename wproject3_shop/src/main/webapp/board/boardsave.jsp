<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("utf-8");%>
<jsp:useBean id="bean" class="pack.board.BoardFormBean"></jsp:useBean>
<jsp:setProperty property="*" name="bean" />
<jsp:useBean id="boardMgr" class="pack.board.BoardMgr"></jsp:useBean>
<%
bean.setBip(request.getRemoteAddr()); //클라이언트에 아이피 어드레스 등록
bean.setBdate(); //시스템이 가지고 있는 날짜 넣어줌
int newNum = boardMgr.currentMaxNum() + 1;
bean.setNum(newNum);
bean.setGnum(newNum);

boardMgr.saveData(bean); 

response.sendRedirect("boardlist.jsp?page=1");
%>