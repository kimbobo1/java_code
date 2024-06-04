package pack7gui;

import java.awt.BufferCapabilities;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URI;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class Ex49Notepad extends JFrame implements ActionListener {
	private JTextArea txtMemo = new JTextArea("", 10, 30);
	private String copyText;   // 복사한 문자열을 잠시 보관하기 위해 선언

	// menu 용
	private JMenuItem mnuNew, mnuSave, mnuOpen, mnuExit;
	private JMenuItem mnuCopy, mnuPaste, mnuCut, mnuDel, mnuFontSize;
	private JMenuItem mnuAbout, mnuEtc1, mnuEtc2;
	
	// popup 메뉴 용
	private JPopupMenu popup;
	private JMenuItem m_white, m_blue, m_yellow;

	public Ex49Notepad() {
		super("메모장");

		initLayout();
		menuLayout();

		setBounds(800, 200, 400, 350);
		setVisible(true);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(Ex49Notepad.this, "정말 종료할까요?", "종료", JOptionPane.YES_NO_OPTION);
				
				if(result == JOptionPane.YES_OPTION) 
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 윈도우 이벤트에 대해서만 실행이 가능, 버튼에서 종료를 하는 경우는 윈도우 이벤트가 아니므로 실행이 되지 않음 이떄는 System.exit을 사용해야함
				else
					setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
		});
	}

	private void initLayout() {
		JScrollPane scrollPane = new JScrollPane(txtMemo); // 스크롤할 대상을 인수로 할당
		txtMemo.setFont(new Font("돋움체", Font.PLAIN, 16));
		add("Center", scrollPane); // 스크롤바 추가된 textArea
	}

	private void menuLayout() {
		JMenuBar menuBar = new JMenuBar(); // 메뉴바 생성

		// 첫 번째 메뉴
		JMenu mnuFile = new JMenu("파일"); // 주메뉴
		mnuNew = new JMenuItem("새 문서"); // 부메뉴
		mnuNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		mnuOpen = new JMenuItem("열기...");
		mnuOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		mnuSave = new JMenuItem("저장...");
		mnuSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		mnuExit = new JMenuItem("종료");
		mnuFile.add(mnuNew);
		mnuFile.add(mnuOpen);
		mnuFile.add(mnuSave);
		mnuFile.addSeparator(); // 구분선 추가
		mnuFile.add(mnuExit);

		// 두 번째 메뉴
		JMenu mnuEdit = new JMenu("편집"); // 주메뉴
		mnuCopy = new JMenuItem("복사");
		mnuCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));   // shortcut key 설정
		mnuPaste = new JMenuItem("붙여넣기");
		mnuPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		mnuCut = new JMenuItem("잘라내기");
		mnuCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		mnuDel = new JMenuItem("삭제");
		mnuFontSize = new JMenuItem("글꼴 크기...");
		mnuEdit.add(mnuCopy);
		mnuEdit.add(mnuPaste);
		mnuEdit.add(mnuCut);
		mnuEdit.add(mnuDel);
		mnuEdit.addSeparator(); 
		mnuEdit.add(mnuFontSize);
		
		// 세 번째 메뉴
		JMenu mnuShow = new JMenu("보기");
		mnuAbout = new JMenuItem("메모장이란...");
		mnuEtc1 = new JMenuItem("계산기");
		mnuEtc1.setMnemonic(KeyEvent.VK_C);
		mnuEtc2 = new JMenuItem("카페");
		mnuShow.add(mnuAbout);
		mnuShow.add(mnuEtc1);
		mnuShow.add(mnuEtc2);
		
		// 메뉴바에 주메뉴 등록
		menuBar.add(mnuFile); 
		menuBar.add(mnuEdit);
		menuBar.add(mnuShow);

		// JFrame에 메뉴바를 등록
		setJMenuBar(menuBar); 
		
		// 메뉴에 리스너 장착
		mnuNew.addActionListener(this);
		mnuOpen.addActionListener(this);
		mnuSave.addActionListener(this);
		mnuExit.addActionListener(this);
		mnuCopy.addActionListener(this);
		mnuPaste.addActionListener(this);
		mnuCut.addActionListener(this);
		mnuDel.addActionListener(this);
		mnuFontSize.addActionListener(this);
		mnuAbout.addActionListener(this);
		mnuEtc1.addActionListener(this);
		mnuEtc2.addActionListener(this);
		
		
		// 팝업 메뉴
		popup = new JPopupMenu();
		JMenu menuColor = new JMenu("배경색 선택");
		m_white = new JMenuItem("하양");
		m_blue = new JMenuItem("파랑");
		m_yellow = new JMenuItem("노랑");
		menuColor.add(m_white);
		menuColor.add(m_blue);
		menuColor.add(m_yellow);
		
		m_white.addActionListener(this);
		m_blue.addActionListener(this);
		m_yellow.addActionListener(this);
		popup.add(menuColor);
		txtMemo.add(popup);   // txtMemo에 팝업 메뉴 추가
		
		txtMemo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {   // mouseClicked외 mousePressed도 있음
				// 마우스 오른쪽 버튼 클릭 시..
//				if(e.getModifiers() == MouseEvent.BUTTON3_MASK) {    // Button1 : 왼쪽 마우스, Button2 : 가운데 마우스, Button3: 오른쪽 마우스
//					popup.show(txtMemo, e.getX(),e.getY());  // 우클릭 시, 해당 지점에 팝업 띄우기
//				}
				
				// getModifiersEx() : 마우스 또는 키보드 이벤트가 발생할 때 해당 이벤트와 함께
				// 이떤 추가 키(예 : Shift, Control)가 눌렀는지를 확인하는 데 사용
				if((e.getModifiersEx() & MouseEvent.BUTTON3_DOWN_MASK) == MouseEvent.BUTTON3_DOWN_MASK) {
					popup.show(txtMemo, e.getX(), e.getY());
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println(e.getActionCommand());  // 출력값 확인을 위한 코드
		// System.out.println(e.getSource());
		if(e.getSource() == mnuNew) { // 새 문서
			txtMemo.setText("");
			setTitle("제목 없음");
		} else if(e.getSource() == mnuOpen) { // 열기
			// 파일 열기를 위한 경로명과 파일명 얻기는 os 지원 창을 사용한다.
			FileDialog dialog = new FileDialog(this,"열기", FileDialog.LOAD);  // 현재 창에서 띄움.
			dialog.setDirectory(".");  // 현재 경로가 보이도록 함.
			dialog.setVisible(true);
			if(dialog.getFile() == null) return;  // 경로명에 파일을 주지 않으면 작업 X
			String dfName = dialog.getDirectory() + dialog.getFile();
			
			try {
				BufferedReader reader = new BufferedReader(new FileReader(dfName));
				txtMemo.setText("");
				String line = "";
				while((line = reader.readLine()) != null) {
					txtMemo.append(line + "\n");
				}
				reader.close();
				
				setTitle(dialog.getFile() + " - 메모장");
			} catch (Exception e2) {
				System.out.println(e2.getMessage());  // 개발자용
				JOptionPane.showMessageDialog(this, e2.getMessage(), "에러", JOptionPane.WARNING_MESSAGE);  // 사용자용
			}
		} else if(e.getSource() == mnuSave) { // 저장
			// 파일 저장을 위한 경로명과 파일명 얻기는 os 지원 창을 사용한다.
			FileDialog dialog = new FileDialog(this,"저장", FileDialog.SAVE);  // 현재 창에서 띄움.
			dialog.setDirectory(".");  // 현재 경로가 보이도록 함.
			dialog.setVisible(true);
			if(dialog.getFile() == null) return;  // 경로명에 파일을 주지 않으면 작업 X
			String dfName = dialog.getDirectory() + dialog.getFile();
			// System.out.println("dfName : " + dfName);
			
			try {
//				BufferedWriter writer = new BufferedWriter(new FileWriter("C:/work/a.txt"));  // 윈도우 타입으로 경로 및 파일 지정 : C:\\work\\a.txt
				BufferedWriter writer = new BufferedWriter(new FileWriter(dfName));
				writer.write(txtMemo.getText());
				writer.close();
				setTitle(dialog.getFile() + " - 메모장");
			} catch (Exception e2) {
				System.out.println(e2.getMessage());  // 개발자가 보기 위함
				JOptionPane.showMessageDialog(this, e2.getMessage(), "에러", JOptionPane.WARNING_MESSAGE);  // 사용자에게 보여주기 위함
			}
		} else if(e.getSource() == mnuExit) { // 종료
			int result = JOptionPane.showConfirmDialog(Ex49Notepad.this, "정말 종료할까요?", "종료", JOptionPane.YES_NO_OPTION);
			
			if(result == JOptionPane.YES_OPTION) 
				System.exit(0);
		} else if(e.getSource() == mnuCopy) { // 복사
			System.out.println(txtMemo.getSelectedText());  // getText()와의 차이. getSelectedText()는 일부만 
			copyText = txtMemo.getSelectedText();
		} else if(e.getSource() == mnuPaste) { // 붙여넣기
			//txtMemo.setText(copyText);
			int position = txtMemo.getCaretPosition();
			txtMemo.insert(copyText, position);  // copyText를 position에 붙여넣기 가능
		} else if(e.getSource() == mnuCut) { // 잘라내기
			copyText = txtMemo.getSelectedText();   // 선택 부분은 copyText와 동일
			
			// copyText에 저장된 부분은 txtMemo에서 지움
			int start = txtMemo.getSelectionStart(); // 잘라내기를 시작할 지점
			int end = txtMemo.getSelectionEnd();  // 잘라내기를 끝낼 지점
			txtMemo.replaceRange("",start,end);  // 시작지점과 끝 지점 사이를 공백으로 채움 -> 잘라낼 부분을 공백으로 대체
		} else if(e.getSource() == mnuDel) { // 삭제
			int start = txtMemo.getSelectionStart(); // 삭제를 시작할 지점
			int end = txtMemo.getSelectionEnd();  // 삭제를 끝낼 지점
			txtMemo.replaceRange("",start,end);  // 시작지점과 끝 지점 사이를 공백으로 채움 -> 삭제할 부분을 공백으로 대체
		} else if(e.getSource() == mnuFontSize) { // 글꼴 크기
			String fontSize = JOptionPane.showInputDialog(this, "글자 크기: ", "16");
			if(fontSize != null) {
				try {
					int fSize = Integer.parseInt(fontSize);
					txtMemo.setFont(new Font(txtMemo.getFont().getName(), txtMemo.getFont().getStyle(), fSize));					
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(this, "글자 크기를 입력하시오");		
				}
			}
		} else if(e.getSource() == mnuAbout) { // 메모장이란
			new Ex49MemoAbout(this);  // 자신 프레임을 가지고 간다.
			System.out.println("About 호출 후 상황");  // Modal과 Modaless 구분을 위해 출력
		} else if(e.getSource() == mnuEtc1) { // 기타 1
			// exe (실행파일) 실행하기
			try {
				Runtime runtime = Runtime.getRuntime();
				runtime.exec("calc.exe");
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(this, e2.getMessage());
			}
		} else if(e.getSource() == mnuEtc2) { // 기타 2
			// 브라우저 실행하기
			try {
//				Desktop 클래스는 Java 응용 프로그램 URI 나 파일을 처리하기 위해 기본 등록된 관련 응용 프로그램을 실행 할 수 있습니다.
//				지원하는 기능은 아래와 같습니다.
//				1. 기본 브라우저를 통해서 URL 전시
//				2. 메일 클라이언트 실행
//				3. 기본 응용프로그램을 통해서 파일을 열거나 편집
				String url = "https://cafe.daum.net/flowlife";
				// DeskTop 지원 확인
				if(Desktop.isDesktopSupported()) {  // 데스크탑을 지원하는지 확인하는 코드
					Desktop desktop = Desktop.getDesktop();   // getDesktop이 new 하고 있다. 싱글턴
					if(desktop.isSupported(Desktop.Action.BROWSE)) {   // 데스크탑이 지원되는지 확인  // 클래스의 포함 관계
						// URI는 문자열, 특정 리소스를 가리킴
						desktop.browse(new URI(url));
					}
				} else {
					JOptionPane.showMessageDialog(this, "브라우저를 지원하지 않음");
				}
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(this, e2.getMessage());
			}
		} 
		
		
		
		else if(e.getSource() == m_white) { // 팝업 메뉴용
			txtMemo.setBackground(Color.white);
		} else if(e.getSource() == m_blue) {
			txtMemo.setBackground(Color.blue);
		} else if(e.getSource() == m_yellow) {
			txtMemo.setBackground(Color.yellow);
		}
	}

	public static void main(String[] args) {
		// 간단한 메모장 만들기
		new Ex49Notepad();
	}

}
