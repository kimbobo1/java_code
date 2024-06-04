package pack7gui;

import java.awt.Button;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Ex44Event extends Frame implements ActionListener {
	private Button btn1 = new Button("button1");
	private Button btn2 = new Button("button2");
	private Button btn3 = new Button("button3");
	private Button btn4 = new Button("button4");
	private Button btn5 = new Button("button5");
	
	public Ex44Event() {
		super("이벤트 연습");
		
		addLayout();
		
		setBounds(800, 200, 400, 400);
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	private void addLayout() {
		add("East",btn1);                                // Frame은 BorderLayout이 기본
		add("West",btn2);                                // East, West, Center, North, South는 키워드
		add("Center",btn3);
		add("South",btn4);
		add("North",btn5);
		
		btn1.addActionListener(this);                    // 방법1 : 현재 클래스에 implements ActionListener를 사용
		btn2.addActionListener(this);
		
		btn3.addActionListener(new EventTest());         // 방법2 : 내부 클래스 사용(implements), 버튼 이벤트
		
		btn4.addMouseListener(new EventTest2());         // 방법3 : 내부 클래스 사용(extends), 마우스 이벤트
		
		btn5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {     // 방법4 : 내부 무명(익명) 클래스 사용, 마우스 이벤트
				// btn5 클릭하면 수행되는 메소드
				setTitle("버튼 5 클릭");
			}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// btn1, btn2를 클릭하면 수행되는 메소드
		
		//System.out.println(e.getActionCommand());      // button1, button2가 출력
		//System.out.println(e.getSource());   
		if(e.getSource() == btn1) {                      // 객체 비교 시, == 사용, String 비교 시 equals 사용
			setTitle("버튼 1 클릭");
		} else if(e.getSource() == btn2) {
			setTitle("버튼 2 click");
		}
	}
	
	class EventTest implements ActionListener {          // 내부 클래스 EventTest 선언
		@Override
		public void actionPerformed(ActionEvent e) {     // 추상 메소드 오버라이딩
			// btn3를 클릭하면 수행되는 메소드
			setTitle("버튼 3을 누르다니");
		}
	}
	
	class EventTest2 extends MouseAdapter {            
		@Override
		public void mouseClicked(MouseEvent e) {         // 일반 메소드 오버라이딩
			// btn4를 클릭하면 수행되는 메소드
			setTitle("버튼 마우스 이벤트 btn4 처리");
		}
	}
	
	public static void main(String[] args) {
		// 이벤트 처리 정리
		new Ex44Event();
	}

}
