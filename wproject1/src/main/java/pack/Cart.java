package pack;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Cart")
public class Cart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String name = request.getParameter("name");
		int price = Integer.parseInt(request.getParameter("price"));
		
		HttpSession session = request.getSession(true); // 세션
		
		// 여러 개의 상품은 하나씩 세션에 올리는 것이 아니라 DTO 형태로 상품명과 가격을 담아 ArrayList에 담고 해당 컬렉션을 세션에 올린다.
		// 상품을 추가하는 경우, 세션으로부터 arraylist를 가져와서 추가하고 다시 세션에 올린다.
		ArrayList<Goods> glist = (ArrayList<Goods>)session.getAttribute("list"); // list라는 이름의 세션값을 읽음. 세션 바깥으로 꺼낸 상태
		
		if(glist == null) glist = new ArrayList<Goods>(); // Goods 객체를 담을 glist 생성
		glist.add(new Goods(name, price)); // 장바구니에 상품 추가
		session.setAttribute("list", glist);
		
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();  
		out.println("<html><body>🤣" + name + " 구입했습니다");
		out.println("<br>[<a href='myshop.html'>계속 쇼핑</a>] ");
		out.println("[<a href='Buy'>결제하기</a>]<br>");
		
		out.println("<p><table width='80%'>");
		out.println("<tr><th>상품명</th><th>가격</th></tr>");
		
		for(int i = 0; i < glist.size(); i++) {
			Goods goods = (Goods)glist.get(i);  // 컬렉션의 내용을 꺼냄
			out.println("<tr><td>"+ goods.getName() +"</td>");
			out.println("<td>"+ goods.getPrice() +"</td></tr>");
		}
		
		out.println("</table>");
		out.println("</body></html>");
		out.close();
	}

}
