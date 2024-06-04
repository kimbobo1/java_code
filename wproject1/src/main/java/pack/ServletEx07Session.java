package pack;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ServletEx07Session")
public class ServletEx07Session extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// 클라이언트가 get 요청을 할 때 (클라이언트가 가진)쿠키 안에 서버가 만들어놓은 세션 아이디를 읽어옴
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session : 각 클라이언트의 정보를 웹서버에 메모리 확보 후 저장
		HttpSession session = request.getSession(true);   // 세션이 있으면 읽고 없으면 세션 생성함
		//HttpSession session = request.getSession(false);  // 세션이 있으면 읽고 없으면 세션 생성 안함
		
		//setMaxInactiveInterval() : 유효 시간
		// 클라이언트가 서버에 접근하고 연결이 끊긴 시점부터 유효 시간이 카운트되기 시작됨.(클라이언트가 활동을 멈춘 시점부터 시작)
		session.setMaxInactiveInterval(10);  // 10초간 유효, 기본값 1800(30분)
		
		// setAttribute(설정한_세션아이디, 세션에_넣을_값) : session id 생성 후, 서버 뿐 아니라 클라이언트 컴퓨터의 cookie에도 저장됨.
		if(session != null) {
			session.setAttribute("name", "홍길동");  
			// 복수 작성이 가능
		}
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();  
		out.println("<html><body>");
		out.println("session id : " + session.getId());
		out.println("<br>사용자명 : " + session.getAttribute("name")); // name에 들어있는 값 확인
		out.println("</body></html>");
		out.close();
	}

}
