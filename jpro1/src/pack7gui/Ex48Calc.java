package pack7gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Ex48Calc extends JFrame implements ActionListener {
	JTextField txtNum1, txtNum2;
	ButtonGroup buttonGroup = new ButtonGroup(); // 라디오 버튼 그룹화를 위해 사용
	JRadioButton plus, minus, cross, division;
	JLabel lblResult;
	JButton btn1,btn2,btn3;

	public Ex48Calc() {
		super("미니 계산기");

		comp();
		addLayout(); 
		setBounds(800, 200, 400, 300);
		setVisible(true);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(Ex48Calc.this, 
						"종료?", "종료 확인", JOptionPane.YES_NO_OPTION);
				if(result == JOptionPane.YES_OPTION) 
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				else
					setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
		});
	}

	private void addLayout() {
		btn1 = new JButton("계산");
		btn2 = new JButton("초기화");
		btn3 = new JButton("종료");
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		JPanel panel = new JPanel();
		panel.add(btn1);
		panel.add(btn2);
		panel.add(btn3);
		
		
		add("South", panel);    // Frame은 BorderLayout이므로
	}
	
	private void comp() {
		setLayout(new GridLayout(5, 1));

		JLabel lbl1 = new JLabel("숫자1 : ");
		txtNum1 = new JTextField("", 20);
		JPanel panel1 = new JPanel();
		panel1.add(lbl1);
		panel1.add(txtNum1);

		add(panel1);

		JLabel lbl2 = new JLabel("숫자2 : ");
		txtNum2 = new JTextField("", 20);
		JPanel panel2 = new JPanel();
		panel2.add(lbl2);
		panel2.add(txtNum2);

		add(panel2);

		JLabel lbl3 = new JLabel("연산선택 : ");
		plus = new JRadioButton("+", true);
		minus = new JRadioButton("-", false);
		cross = new JRadioButton("*", false);
		division = new JRadioButton("/", false);
		buttonGroup.add(plus);
		buttonGroup.add(minus);
		buttonGroup.add(cross);
		buttonGroup.add(division);

		JPanel panel3 = new JPanel();
		panel3.add(lbl3);
		panel3.add(plus);
		panel3.add(minus);
		panel3.add(cross);
		panel3.add(division);

		add(panel3);


		lblResult = new JLabel("결과 : ", JLabel.CENTER);
		add(lblResult);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btn1) {
			if (txtNum1.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "숫자1 입력!", "알림", JOptionPane.INFORMATION_MESSAGE);
				txtNum1.requestFocus();
				return;
			}

			if (txtNum2.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "숫자2 입력!", "알림", JOptionPane.INFORMATION_MESSAGE);
				txtNum2.requestFocus();
				return;
			}
			
			
			int num1 = 0, num2 = 0;
			try {
				num1 = Integer.parseInt(txtNum1.getText());
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(this, "정수 입력!", ":/", JOptionPane.INFORMATION_MESSAGE);
				txtNum1.requestFocus();
				return;
			}
			try {
				num2 = Integer.parseInt(txtNum2.getText());
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(this, "정수 입력!", ":/", JOptionPane.INFORMATION_MESSAGE);
				txtNum2.requestFocus();
				return;
			}
			
			
			int result = 0;
			if(plus.isSelected()) {
				lblResult.setText("결과 : " + (num1 + num2));
			} else if(minus.isSelected()) {
				result = num1 - num2;
				System.out.println(result);
			} else if(cross.isSelected()) {
				result = num1 * num2;
				System.out.println(result);
			} else {
				if(num2 != 0) {
					lblResult.setText("결과 : " + num1 / (double)num2);
				} else {
					JOptionPane.showMessageDialog(this, "0으로 나누기 X", "알림", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
		} else if(e.getSource() == btn2) {
			if(!txtNum1.getText().equals("")) {
				txtNum1.setText("");
			}
			if(!txtNum2.getText().equals("")) {
				txtNum2.setText("");
			}
			if(!plus.isSelected()) {
				plus.setSelected(true);
			}
				
		} else if(e.getSource() == btn3){
			int result = JOptionPane.showConfirmDialog(this, "종료?", "",JOptionPane.YES_NO_OPTION);
			System.out.println(result);
	
			switch(result) {
			case JOptionPane.YES_OPTION:     
				System.exit(0);
			case JOptionPane.NO_OPTION:
				break;
			}
		}
	}

	public static void main(String[] args) {
		new Ex48Calc();

	}

}
