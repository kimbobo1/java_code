<%@page import="pack.board.BoardMgr"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("utf-8"); %>

<jsp:useBean id="bean" class="pack.board.BoardFormBean"></jsp:useBean>
<jsp:setProperty property="*" name="bean" />
<jsp:useBean id="boardMgr" class="pack.board.BoardMgr"></jsp:useBean>
<%
String spage = request.getParameter("page");

int num = bean.getNum();
int gnum = bean.getGnum();
int onum = bean.getOnum() +1; //댓글이므로 +1증가
int nested = bean.getNested() +1;

//같은 그룹내에서 새로운 댓글의 onum보다 크거나 같은 값을 댓글 입력 전에 먼저 수정하기
//작으면 수정하지 않는다
boardMgr.updateOnum(gnum, onum);  //onum 갱신용

//댓글 저장
bean.setOnum(onum);
bean.setNested(nested);
bean.setBip(request.getRemoteAddr());
bean.setBdate();
bean.setNum(boardMgr.currentMaxNum() +1); //댓글(새글 ) 번호(num)

boardMgr.replySave(bean);
response.sendRedirect("boardlist.jsp?page=" + spage);

%>
