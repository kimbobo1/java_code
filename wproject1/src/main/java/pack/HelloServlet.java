package pack;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/HelloServlet")
@WebServlet(name="ServletGo", urlPatterns={"/HelloServlet","/good.kor","/better"},loadOnStartup = 1)  // 요청이 없어도, 웹서비스가 시작되면 서블릿 수행   
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;  // 무시!

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// console 출력
		System.out.println("get 요청 성공");
		
		// 자바 작업을 하다가..
		// 서블릿(웹용 자바)으로 클라이언트 브라우저에 데이터 전송
		response.setContentType("text/html;charset=utf-8");  // Mime type과 문자 코드
		// 웹용 출력
		PrintWriter out = response.getWriter();  // response가 PrintWriter 객체를 만들어줌. -> 클라이언트로 전달 
		out.println("<html><body>");
		out.println("<h1>서블릿 문서</h1>");
		out.println("안녕, 반가워!");
		out.println("</body></html>");
		out.close();
	}

}
