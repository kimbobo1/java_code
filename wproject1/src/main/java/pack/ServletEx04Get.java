package pack;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletEx04Get
 */
@WebServlet("/ServletEx04Get")
public class ServletEx04Get extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String addr = request.getParameter("addr");
		String age = request.getParameter("age");
		// 받은 자료로 Business Logic, DB에 등록, ... 등을 할 수 있다.
		
		// 클라이언트로 출력
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();  // response가 PrintWriter 객체를 만들어줌. -> 클라이언트로 전달 
		out.println("<html><body>");
		out.println("<h2>Get 요청에 대한 결과</h2>");
		out.println("이름은 " + name +"의 주소 : " + addr + ", 나이 : " + age);
		out.println("<br>" + calcAge(age));
		out.println("<br><br><a href='getdata.html'>자료 다시 입력</a>");
		out.println("</body></html>");
		out.close();
	}
	
	private String calcAge(String age) {
		int imsi = Integer.parseInt(age) / 10 * 10;
		String result = "기타";
		
		switch(imsi) {
		case 20: result = "20대"; break;
		case 30: result = "30대"; break;
		case 40: result = "아저씨"; break;
		}
		
		return result;
	}

}
