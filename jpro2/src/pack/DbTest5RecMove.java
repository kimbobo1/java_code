package pack;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

// Connection 캑체는 필요할 때만 연결하고 끊는 것이 원칙이나 그렇지 않은 경우도 있다.
public class DbTest5RecMove extends JFrame implements ActionListener {
	JButton btnF = new JButton("|<<");
	JButton btnP = new JButton("<");
	JButton btnN = new JButton(">");
	JButton btnL = new JButton(">>|");
	
	JTextField txtNo = new JTextField("", 5);
	JTextField txtName = new JTextField("", 10);
	
	Connection conn;
	Statement stmt; // where 조건을 줄 때, 문자열 더하기를 하게 되면 해킹 위험이 높아짐. SQL Injection 문제가 발생.   // statement는 where 조건문 사용 시 문자열 더하기만 가능
	// 이를 해결하기 위해서 preparedStatement를 사용
	// preparedStatement의 단점은 대량의 데이터 처리에 비교적 좋지않다.
	// ? 순서에 따라 데이터를 입력받는다.(?)
	// https://velog.io/@k4minseung/DB-SQL-Injection-%EA%B3%B5%EA%B2%A9%EA%B3%BC-%EB%B0%A9%EC%96%B4-%EB%B0%A9%EB%B2%95
	ResultSet rs;
	
	public DbTest5RecMove() {
		super("레코드 이동");
		
		layInit();
		accDb();
		
		setBounds(800, 200, 300, 250);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if(e.getSource() == btnF) rs.first();
			else if(e.getSource() == btnP) rs.previous();  // 한 행 위로 이동한다. ->
			else if(e.getSource() == btnN) rs.next();
			else if(e.getSource() == btnL) rs.last();
			
			
			display();  // 여기서 출력해준다. <-
		} catch (Exception e2) {
			//JOptionPane.showMessageDialog(this, "dd");
		}
	}
	
	private void display() {
		try {
			txtNo.setText(rs.getString("jikwon_no"));
			txtName.setText(rs.getString("jikwon_name"));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void layInit() {
		JPanel panel1 = new JPanel();  // FlowLayout
		panel1.add(new JLabel("직원 자료"));
		txtNo.setEditable(false);  // 입력 불가, false인 경우 편집만 불가
//		txtName.setEnabled(false); // 입력 불가, false인 경우 비활성화
		txtName.setEditable(false);
		panel1.add(txtNo);
		panel1.add(txtName);
		add("North", panel1);
		
		JPanel panel2 = new JPanel();
		panel2.add(btnF);
		panel2.add(btnP);
		panel2.add(btnN);
		panel2.add(btnL);
		add("Center", panel2);
		
		btnF.addActionListener(this);
		btnP.addActionListener(this);
		btnN.addActionListener(this);
		btnL.addActionListener(this);
	}
	
	private void accDb() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			
			//processDb();   
			String url = "jdbc:mariadb://localhost:3306/mydb";
			conn = DriverManager.getConnection(url, "root", "123");  // 연결 객체를 만듦
			
			stmt = conn.createStatement();  
			
			rs = stmt.executeQuery("select jikwon_no,jikwon_name from jikwon"); // 여기서 읽음, statement를 사용하면 sql 문장을 매번 써야한다.
			rs.next();
			display();  // 첫번째 데이터가 보이도록 함
		} catch (Exception e) {
			System.out.println("accDb err : " + e);
		}
	}
	/*
	private void processDb() {  // DB를 닫지 않아야 함.
		try {
			String url = "jdbc:mariadb://localhost:3306/mydb";
			conn = DriverManager.getConnection(url, "root", "123");  // 연결 객체를 만듦
			
			stmt = conn.createStatement();  
			
			rs = stmt.executeQuery("select jikwon_no,jikwon_name from jikwon"); // 여기서 읽음, statement를 사용하면 sql 문장을 매번 써야한다.
			rs.next();
			display();  // 첫번째 데이터가 보이도록 함
		} catch (Exception e) {
			System.out.println("processDb err : " + e);
		}
	}*/
	
	public static void main(String[] args) {
		new DbTest5RecMove();
	}

}
