package Personal;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class test extends JFrame implements ActionListener {
	JTextField txtName, txtKor, txtEng, txtMat;
	JButton btn1, btn2;
	JLabel total, average, grade;
	
	int x, y;
	Image image;
	int selImage;

	public test() {
		super("성적 출력");

		gradeLayout();
		addLayout();

		setBounds(800, 200, 400, 300);
		setVisible(true);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(test.this, "종료?", "종료 확인", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION)
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			}
		});
		
		x = super.getWidth() / 2; // frame 너비의 반   
		y = super.getHeight() / 2;  // frame 높이의 반 
	}
	
	private void addLayout() {
		btn1 = new JButton("확인");
		btn2 = new JButton("초기화");
		btn1.addActionListener(this);
		btn2.addActionListener(this);

		JPanel panel = new JPanel();
		panel.add(btn1);
		panel.add(btn2);

		
		add("South", panel); // Frame은 BorderLayout이므로
		
	}
	
	
	
	private void gradeLayout() {
		setLayout(new GridLayout(5, 1));

		JLabel lblName = new JLabel("이름 : ");
		txtName = new JTextField("", 20);
		JPanel panel1 = new JPanel();
		panel1.add(lblName);
		panel1.add(txtName);

		add(panel1);

		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		JLabel lblKor = new JLabel("국어 : ");
		txtKor = new JTextField("", 5);
		JLabel lblEng = new JLabel("영어 : ");
		txtEng = new JTextField("", 5);
		JLabel lblMat = new JLabel("수학 : ");
		txtMat = new JTextField("", 5);

		panel2.add(lblKor);
		panel2.add(txtKor);
		panel2.add(lblEng);
		panel2.add(txtEng);
		panel2.add(lblMat);
		panel2.add(txtMat);
		add(panel2);

		JPanel panel3 = new JPanel();
		panel3.setLayout(new FlowLayout());
		total = new JLabel("총점 : ");
		average = new JLabel("평균 : ");
		grade = new JLabel("평가 : ");

		panel3.add(total);
		panel3.add(average);
		panel3.add(grade);
		add(panel3);

//		JPanel panel4 = new JPanel();
		// panel4.add();
		// add(panel4);
		
	}

	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn1) {
			if (txtName.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "이름을 입력하시오.", "알림", JOptionPane.INFORMATION_MESSAGE);
				txtName.requestFocus();
				return;
			}

			if (txtKor.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "국어 점수를 입력하시오.", "알림", JOptionPane.INFORMATION_MESSAGE);
				txtKor.requestFocus();
				return;
			}

			if (txtEng.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "영어 점수를 입력하시오.", "알림", JOptionPane.INFORMATION_MESSAGE);
				txtEng.requestFocus();
				return;
			}

			if (txtMat.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "수학 점수를 입력하시오.", "알림", JOptionPane.INFORMATION_MESSAGE);
				txtMat.requestFocus();
				return;
			}

			int kor = -1, eng = -1, mat = -1;
			try {
				kor = Integer.parseInt(txtKor.getText());
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(this, "정수 입력!", ":/", JOptionPane.INFORMATION_MESSAGE);
				txtKor.requestFocus();
				return;
			}
			try {
				eng = Integer.parseInt(txtEng.getText());
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(this, "정수 입력!", ":/", JOptionPane.INFORMATION_MESSAGE);
				txtEng.requestFocus();
				return;
			}
			try {
				mat = Integer.parseInt(txtMat.getText());
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(this, "정수 입력!", ":/", JOptionPane.INFORMATION_MESSAGE);
				txtMat.requestFocus();
				return;
			}

			int tot = kor + eng + mat;
			double avg = tot / 3;
			String grd = "";

			total.setText("총점 : " + (kor + eng + mat));
			average.setText("평균 : " + avg);
			if (avg >= 90) {
				grade.setText("평가 : A");
				selImage = 1;

			} else if (avg >= 80) {
				grade.setText("평가 : B");
				selImage = 2;
			} else if (avg >= 70) {
				grade.setText("평가 : C");
				selImage = 3;
			} else if (avg >= 60) {
				grade.setText("평가 : D");
				selImage = 4;
			} else {
				grade.setText("평가 : F");
				selImage = 5;
			}

			if(kor >= 0 && mat >= 0 && eng >= 0) {
				repaint();
			}
			
			
		} else if (e.getSource() == btn2) {
			if (!txtKor.getText().equals("")) {
				txtKor.setText("");
			}
			if (!txtEng.getText().equals("")) {
				txtEng.setText("");
			}
			if (!txtMat.getText().equals("")) {
				txtMat.setText("");
			}
		}
		
	}

	
	public static void main(String[] args) {
		new test();
	}

}