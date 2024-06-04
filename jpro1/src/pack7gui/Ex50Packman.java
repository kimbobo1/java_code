package pack7gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class Ex50Packman extends JFrame implements KeyListener {
	Image image;
	int x = 100, y = 100;
	int selImage = 1;  // 첫 번째 이미지가 나오도록 설정
	
	public Ex50Packman() {
		super("Packman - 상하좌우 화살표를 사용");
	
		// 아이콘 이미지 변경
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\work\\jsou\\jpro1\\src\\pack7gui\\pack1.jpg"));  // Toolkit : 이미지를 넣을 때 사용, 이미지는 절대경로로만 설정 가능
		
		setLayout(null);  // 배치 관리자 없음
		setResizable(false);  // 창 크기 조절을 하지 못하도록 함, maximize 버튼 비활성화
	
		setBounds(800, 200, 500, 500);
		setBackground(Color.WHITE);
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addKeyListener(this);  // 키보드에 키리스너를 장착
		
		x = super.getWidth() / 2; // frame 너비의 반   
		y = super.getHeight() / 2;  // frame 높이의 반 
	}
	
	@Override
	public void paint(Graphics g) { // 그림은 paint에서 그려지고 있다.
		// Frame에 뭔가를 그려주는 메소드로 자동 호출 
		switch (selImage) {
		case 1: 
			image = Toolkit.getDefaultToolkit().getImage("C:\\work\\jsou\\jpro1\\src\\pack7gui\\pack1.jpg"); 
			break;
		case 2: 
			image = Toolkit.getDefaultToolkit().getImage("C:\\work\\jsou\\jpro1\\src\\pack7gui\\pack2.jpg"); 
			break;
		case 3: 
			image = Toolkit.getDefaultToolkit().getImage("C:\\work\\jsou\\jpro1\\src\\pack7gui\\pack3.jpg"); 
			break;
		case 4: 
			image = Toolkit.getDefaultToolkit().getImage("C:\\work\\jsou\\jpro1\\src\\pack7gui\\pack4.jpg"); 
			break;
		case 5: 
			image = Toolkit.getDefaultToolkit().getImage("C:\\work\\jsou\\jpro1\\src\\pack7gui\\pack5.jpg"); 
			break;
		case 6: 
			image = Toolkit.getDefaultToolkit().getImage("C:\\work\\jsou\\jpro1\\src\\pack7gui\\pack6.jpg"); 
			break;
		case 7: 
			image = Toolkit.getDefaultToolkit().getImage("C:\\work\\jsou\\jpro1\\src\\pack7gui\\pack7.jpg"); 
			break;
		case 8: 
			image = Toolkit.getDefaultToolkit().getImage("C:\\work\\jsou\\jpro1\\src\\pack7gui\\pack8.jpg"); 
			break;
		}
		
		//image = Toolkit.getDefaultToolkit().getImage("C:\\work\\jsou\\jpro1\\src\\pack7gui\\pack1.jpg");   // 첫 번째 이미지를 그려넣음.
		
		g.clearRect(0, 0, getWidth(), getHeight()); // 좌상단, 우하단 설정하여 화면을 선택 후 클리어 -> 잔상 제거
		
		g.drawImage(image, x - image.getWidth(this) / 2,  y - image.getHeight(this) / 2, this);  // observer : 감시자 -> 현재 프레임에서 감시한다. 
	}
	
	@Override
	public void keyPressed(KeyEvent e) {  // 키보드를 누를 때
		int key = e.getKeyCode();
		//System.out.println("key : " + key);
		
		// 오른쪽 화살표
		if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_NUMPAD6) {
			selImage = (selImage == 1? 2: 1);  
			//x = x + 10;
			x = (x < getWidth()? x += 10: -image.getWidth(this));   // x 값이 폭보다 작으면 10씩 증가, 크면 0으로 옮김
		}
		
		// 왼쪽 화살표
		if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_NUMPAD4) {
			selImage = (selImage == 3? 4: 3); 
			
			x = (x > -image.getWidth(this)? x -= 10: getWidth());
		}
		
		// 위쪽 화살표
		if(key == KeyEvent.VK_UP || key == KeyEvent.VK_NUMPAD8) {
			selImage = (selImage == 7? 8: 7);
			
			y = (y > -image.getHeight(this)? y -= 10: getHeight());
		}
				
		// 아래쪽 화살표
		if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_NUMPAD2) {
			selImage = (selImage == 5? 6: 5);
			
			y = (y < getWidth()? y += 10: -image.getHeight(this));
		}
		
		repaint();  // paint() 호출, 화면이 새로고침
	}
	
	@Override
	public void keyReleased(KeyEvent e) { }
	
	@Override
	public void keyTyped(KeyEvent e) { }
	
	public static void main(String[] args) {
		// 키보드 이벤트 연습
		new Ex50Packman();
	}

}
