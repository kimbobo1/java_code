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

@WebServlet("/Result")
public class Result extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int num = Integer.parseInt(request.getParameter("num"));
		String name = request.getParameter("name");
		int kor = Integer.parseInt(request.getParameter("kor"));
		int eng = Integer.parseInt(request.getParameter("eng"));
		
		HttpSession session = request.getSession(true); 
		
		ArrayList<Students> slist = (ArrayList<Students>)session.getAttribute("list"); 
		
		if(slist == null) slist = new ArrayList<Students>(); 
		
		// 번호 중복 체크
		for(Students student : slist) {
			if (student.getNum() == num) {
                response.sendRedirect("exam.html");             
                //return; 
           }
		}
		
		
		slist.add(new Students(num, name, kor, eng)); 
		session.setAttribute("list", slist);
		
				
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();  
		out.println("<html><body>");
		out.println("학생들 성적표<br><br><table>");
		out.println("<tr><th>번호</th><th>이름</th><th>국어</th><th>영어</th><th>총점</th></tr>");
		

		int count = 0;
		for(int i = 0; i < slist.size() ; i++) {
			Students students = (Students)slist.get(i);  // 컬렉션의 내용을 꺼냄

			out.println("<tr><td>"+ students.getNum() +"</td>");
			out.println("<td>"+ students.getName() +"</td>");
			out.println("<td>"+ students.getKor() +"</td>");
			out.println("<td>"+ students.getEng() +"</td>");
			out.println("<td>"+ students.getTotal() +"</td></tr>");
			count++;
		}
		
		out.print("<tr><td colspan='5'>인원수 : " + count + "</td></tr>");
		out.println("</table></body></html>");
		out.println("<br><a href='exam.html'>새로입력</a>&nbsp;");
		out.println("<a href='Delete'>세션삭제</a>");

	}

}
