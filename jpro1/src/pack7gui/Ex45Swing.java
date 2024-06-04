package pack7gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
Swing :
AWT 기술을 기반으로 순수 자바 언어로 만들어진 라이브러리
모든 AWT 기능 + 추가된 풍부하고 화려한 고급 컴포넌트
AWT 컴포넌트에 J자가 덧붙여진 이름의 클래스
그 외 J 자로 시작하는 클래스
javax.swing 패키지
Swing 컴포넌트는 경량 컴포넌트(Light weight components)
*/

public class Ex45Swing extends JFrame implements ActionListener {
	JLabel lblShow;
	int count = 0;
	
	public Ex45Swing() {
		setTitle("스윙 연습");
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));  // default는 열 기준
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));   // createEmptyBorder를 사용하여 top, left, bottom, right 여백, Factory가 붙어있는 경우 알아서 new 함.
		
		JButton button = new JButton("클릭(C)");
		button.setMnemonic(KeyEvent.VK_C);  // Mnemonic key  -> alt + c도 사용이 가능해짐.
		button.addActionListener(this);                             
		panel.add(button);
		
		lblShow = new JLabel("버튼 클릭 수 : 0");
		panel.add(lblShow);
		
		add(panel, BorderLayout.CENTER);
		//add("CENTER", panel);     // 위 행과 동일한 결과 출력
		
		setBounds(800,200,300,300);
		setVisible(true);
		
		//addWindowListener(...);  윈도우 종료를 아래와 같이 적을 수 있음
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 윈도우 종료
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		count++;
		lblShow.setText("버튼 클릭 수 : " + count);
	}

	public static void main(String[] args) {
		new Ex45Swing();
	}
}
