package pack;

import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DbTest7Ex1 extends JFrame implements ActionListener {
	JButton btn = new JButton("추가");
	JTextArea txtResult = new JTextArea();
	JTextField txtCode, txtSu, txtDan, txtSang;

	// DB 연결
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;

	public DbTest7Ex1() {
		setTitle("상품 처리");

		accDb();
		layInit();

		setBounds(800, 200, 550, 250);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	private void layInit() {
		JPanel panel = new JPanel();

		txtCode = new JTextField("", 4);
		txtSang = new JTextField("", 4);
		txtSu = new JTextField("", 4);
		txtDan = new JTextField("", 4);

		panel.add(new Label("코드 : "));
		panel.add(txtCode);
		panel.add(new Label("품명 : "));
		panel.add(txtSang);
		panel.add(new Label("수량 : "));
		panel.add(txtSu);
		panel.add(new Label("단가 : "));
		panel.add(txtDan);
		panel.add(btn);
		add("North", panel);
		JScrollPane pane = new JScrollPane(txtResult);
		add(pane);

		btn.addActionListener(this);
	}

	private void display() {
		try {
			String result = "";
			
			DecimalFormat deci = new DecimalFormat("#,##0");
			
			txtResult.append("코드" + "\t" + "상품명" + "\t" + "수량" + "\t" + "단가" + "\t" + "금액" + "\n");
			while(rs.next()) {
				result = rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t" +
			deci.format(Integer.parseInt(rs.getString(3)) * Integer.parseInt(rs.getString(4))) + "\n";
				txtResult.append(result);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
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
		try {
			txtResult.setText(null);

			// TextField 값이 없는 경우 실행
			if(txtCode.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "코드를 입력하시오.", "알림", JOptionPane.INFORMATION_MESSAGE);
				txtCode.requestFocus();
				return;
			}
			if(txtSang.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "상품명을 입력하시오.", "알림", JOptionPane.INFORMATION_MESSAGE);
				txtSang.requestFocus();
				return;
			}
			if(txtSu.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "수량을 입력하시오.", "알림", JOptionPane.INFORMATION_MESSAGE);
				txtSu.requestFocus();
				return;
			}
			if(txtDan.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "단가를 입력하시오.", "알림", JOptionPane.INFORMATION_MESSAGE);
				txtDan.requestFocus();
				return;
			}
			
			String url = "jdbc:mariadb://localhost:3306/mydb";
			conn = DriverManager.getConnection(url, "root", "123");

			String udtSql = "";
			String sql = "";

			int code = Integer.parseInt(txtCode.getText());
			String sang = txtSang.getText();
			int su = Integer.parseInt(txtSu.getText());
			String dan = txtDan.getText();

			
			udtSql = "INSERT INTO sangdata VALUES(?,?,?,?)";
			pstmt = conn.prepareStatement(udtSql);
			pstmt.setInt(1, code);
			pstmt.setString(2, sang);
			pstmt.setInt(3, su);
			pstmt.setString(4, dan);
			pstmt.executeUpdate();
			

			sql = "SELECT code, sang, su, dan, su*dan FROM sangdata";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			display();

		} catch (Exception e2) {
			JOptionPane.showMessageDialog(this, "코드가 중복되었습니다.");
		} finally {
			try {
				if(rs != null) rs.close(); 
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) { }
		}

	}

	public static void main(String[] args) {
		new DbTest7Ex1();
	}
}
