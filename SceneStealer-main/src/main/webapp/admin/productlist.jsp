<%@page import="pack.product.ProductMgr"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="pack.admin.AdminMgr"%>
<%@page import="pack.product.ProductDto"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="productMgr" class="pack.product.ProductMgr" />
<jsp:useBean id="dto" class="pack.product.ProductDto"></jsp:useBean>

<%
int spage = 1, pageSu = 0; //spage: 현재 페이지 번호를 저장. 기본값은 1
int start, end;   //pageSu: 전체 페이지 수를 저장

try {   //URL 파라미터에서 page 값을 가져와서 현재 페이지 번호를 설정. 잘못된 값이 입력되면 기본값 1을 사용
    spage = Integer.parseInt(request.getParameter("page"));
} catch (Exception e) {
    spage = 1;
}
if (spage <= 0) spage = 1;

productMgr.totalList(); // 전체 레코드 수 계산
pageSu = productMgr.getPageSu(); // 전체 페이지 수 받기

ArrayList<ProductDto> plist = productMgr.getProductAll(spage); 
//현재 페이지에 해당하는 상품 목록을 가져옵니다.

%>
<!DOCTYPE html>
<html>
<head>
<title>상품관리</title>
<script type="text/javascript" src="../js/productedit.js"></script>
<script type="text/javascript">
function productDetail(ss){
    alert(ss);
    document.detailForm.str.value = ss;
    document.detailForm.submit();
}
</script>
</head>
<body>
**관리자 전체 상품 목록 **<p/>
<%@ include file="admin_top.jsp" %>
<table style="width: 90%">
    <tr style="background-color: silver;">
        <th>상품명</th><th>가격</th><th>등록일</th><th>재고량</th><th>횟수</th><th>카테고리</th><th>상세보기</th>
    </tr>
    <%
    if (plist.size() == 0) {
    %>
    <tr>
        <td colspan="7">등록된 상품이 없습니다</td>
    </tr>
    <%
    } else {
        for (ProductDto p : plist) {
    %>
    <tr style="text-align: center;">
        <td><%= p.getName() %></td>
        <td><%= p.getPrice() %></td>
        <td><%= p.getDate() %></td>
        <td><%= p.getStock() %></td>
        <td><%= p.getCount() %></td>
        <td><%= p.getCategory() %></td>
        <td>
            <a href="javascript:productDetail('<%= p.getName() %>')"><%= p.getName() %></a>
        </td>
    </tr>
    <%
        }
    }
    %>
</table>

<table style="width: 100%">
    <tr>
        <td style="text-align: center;">
            <%
            for (int i = 1; i <= pageSu; i++) {
                if (i == spage) {
                    out.print("<b style='font-size:15pt; color:red'>[" + i + "]</b>");
                } else {
                    out.print("<a href='productlist.jsp?page=" + i + "'>[" + i + "]</a>");
                }
            }
            %>
        </td>
    </tr>
</table>

<tr>
    <td colspan="6">
        [<a href="productinsert.jsp">상품 등록</a>]
    </td>
</tr>

<form action="productdetail.jsp" id="detailForm" name="detailForm" method="get">
    <input type="hidden" name="str">
</form>
</body>
</html>
