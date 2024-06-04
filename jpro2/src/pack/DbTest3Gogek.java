package pack;

import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DbTest3Gogek extends JFrame implements ActionListener{
	JButton btnA = new JButton("전체");
	JButton btnM = new JButton("남자");
	JButton btnF = new JButton("여자");
	JTextArea txtResult = new JTextArea();
	
	// DB 연결
	Connection conn;
	Statement stmt;
	ResultSet rs;
	
	public DbTest3Gogek() {
		setTitle("고객 자료");
		
		layInit();
		accDb();
		
		setBounds(800, 200, 320, 250);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	private void layInit() {
		JPanel jPanel = new JPanel();
		jPanel.add(btnA);
		jPanel.add(btnM);
		jPanel.add(btnF);
		
		txtResult.setEditable(false);  // 텍스트 영역 Read only, 수정 불가
		JScrollPane pane = new JScrollPane(txtResult);
		
		add("Center", pane);
		add("North", jPanel);
		
		btnA.addActionListener(this);
		btnM.addActionListener(this);
		btnF.addActionListener(this);
	}
	
	private void accDb() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (Exception e) {
			System.out.println("accDb err : " + e);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// DB 연결은 필요 시 접속하고 작업이 끝나면 반드시 연결을 해제한다. **
		try {
			String url = "jdbc:mariadb://localhost:3306/test";
			conn = DriverManager.getConnection(url, "root", "123");
			
			stmt = conn.createStatement(); 
			
			String sql="select gogek_no, gogek_name,gogek_jumin from gogek";
			
			if(e.getSource() == btnA) {
				
			} else if(e.getSource() == btnM) {
				sql += " where gogek_jumin like '%-1%'";
				//sql += " where substr(gogek_jumin,8,1)=1";  위 행과 동일
			} else if(e.getSource() == btnF) {
				sql += " where gogek_jumin like '%-2%'";
			}
			
			// 버튼 클릭 시, 텍스트 영역 지우기
			txtResult.setText(null);
			
			// query 실행
			rs = stmt.executeQuery(sql);
			int count = 0;
			txtResult.setText("고객번호\t고객명\t주민번호\n");
			
			while(rs.next()) {
				String imsi = rs.getString("gogek_no") + "\t" +
						rs.getString("gogek_name") + "\t" +
						rs.getString("gogek_jumin") + "\n";
				
				txtResult.append(imsi);
				count++;
			}
			txtResult.append("인원 수: " + count);
			
		} catch (Exception e2) {
			System.out.println("actionPerformed err : " + e2);
		} finally {
			try {
				if(rs != null) rs.close(); 
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) { }
		}
	}
	
	public static void main(String[] args) {
		new DbTest3Gogek();
	}

}
