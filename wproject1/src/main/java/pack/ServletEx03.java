package pack;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ServletEx03")
public class ServletEx03 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int num = 0;
	
	public void init(ServletConfig config) throws ServletException {  // init() 은 ServletConfig 객체를 만든 후 수행됨.
		// 웹 서버 서비스가 시작되면 자동 호출. 
		// 현재 서블릿 클래스의 초기화를 담당(최초의 요청에 의해 1회만 수행)
		num = 1;
		System.out.println("init() 메소드 수행 : num = " + num);
	}

	// service를 주석처리하지 않으면 둘 다 doGet이거나 doPost가 실행된다. -> get과 post를 구분하고 싶지 않은 경우 사용.
	/* service를 주석처리하면 doGet과 doPost가 분리된다. 
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get/Post 요청 시 매번 수행(doGet이 doPost 보다 우선순위가 높다)
		// doGet, doPost() 메소드를 호출 가능
		// 참고. jsp 파일은 service() 메소드만을 가진 파일이다.
		num++;
		System.out.println("service() 메소드 수행 : num = " + num);
		
		//doGet(request, response); 
	}*/

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get 요청 시 매번 수행
		num++;
		System.out.println("doGet() 메소드 수행 : num = " + num);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Post 요청 시 매번 수행
		num++;
		System.out.println("doPost() 메소드 수행 : num = " + num);
	}

	public void destroy() {
		// 웹 서버 서비스가 시작되면 자동 호출.
		// 마무리 작업을 담당
		System.out.println("destroy() 메소드 수행");  
	}
}
