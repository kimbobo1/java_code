package pack;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ServletBang")
public class ServletBang extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;  // insert 문이기 때문에 필요 없음!

	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/test2", "root", "bohyun");
			pstmt = conn.prepareStatement("insert into guest(name,subject,content) values(?,?,?)");
			
		} catch (Exception e) {
			System.out.println("init err : " + e.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");  // 한글 깨짐 방지
		String name = request.getParameter("name");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		
		try {
			// 수신된 자료로 insert 처리. 자료 검증은 생략함.
			pstmt.setString(1, name);
			pstmt.setString(2, subject);
			pstmt.setString(3, content);
			pstmt.executeUpdate(); // 서버에서 insert 작업이 끝남.
			
			// 값을 등록하면 servletBang이 실행되고 다시 minibang.html로 이동하나. 빨라서 보이지 않음
			// 만약 아래 행을 주석 처리하고 실행하면 ServletBang으로 이동한다.
//			response.sendRedirect("minibang.html"); // 입력 화면(minibang.html)로 이동
			
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<html><body>");
			out.println("<b>" + name + "님 등록완료" + "</b>");
			out.println("<br><a href='minibang.html'>새 글 입력</a>");
			out.println("<br><a href='ServletBangList'>글 내용 보기</a>");
			out.println("</body></html>");
			out.close();
		} catch (Exception e) {
			System.out.println("doPost err : " + e);
		}
	}

	public void destroy() {
		try {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		} catch (Exception e) { }
	}
}
