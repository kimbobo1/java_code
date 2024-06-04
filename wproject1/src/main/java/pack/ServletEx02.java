package pack;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pack.other.ServletEx02Other;

/**
 * Servlet implementation class ServletEx02
 */
@WebServlet("/ServletEx02")
public class ServletEx02 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletEx02Other other;
	
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {  // 가장 처음 요청한 사람만 init 메소드를 만난다. 그 다음 사람부터는 doGet만 실행.
		// 서버는 init() 메소드를 호출해서 Servlet을 초기화한다.
		other = new ServletEx02Other("고길동");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");  
		// 웹 출력
		PrintWriter out = response.getWriter(); 
		out.println("<html><body>");
		out.println("<h1>서블릿 문서 Ex02</h1>");
		
		// 지역변수 출력
		int a = 10, b = 20;
		out.println("a : " + a + ", b : " + b);
		
		// 연재 클래스의 메소드 호출
		int tot = calc(a, b);
		out.println("<br>두 수의 합은 " + tot);
		
		// 클래스 호출
		//ServletEx02Other other = new ServletEx02Other("홍길동");  // new 사용 하면 안됨 -> 클라이언트가 호출될 때마다 객체를 생성하기 때문에 서버가 다운됨. -> init에서 선언한다.
		String name = other.getName();
		out.println("<br>이름은 " + name);
		
		out.println("</body></html>");
		out.close();
	}
	
	private int calc(int a, int b) {
		int sum = a + b;
		return sum;
	}

}
