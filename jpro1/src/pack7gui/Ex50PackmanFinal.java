package pack7gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;

public class Ex50PackmanFinal extends JFrame implements KeyListener, Runnable {
	Image img;  // 캐릭터 이미지
	Image food; // 먹이 이미지
	int sel = 1;
	int xx = 100, yy = 100; // 캐릭터 좌표
	int fx = 200, fy = 100; // 먹이 좌표
	Thread thread;
	int chk = 0;
	int point = 0;
	Clip clip;

	public Ex50PackmanFinal() {
		super("상하좌우 화살표를 사용하시오");

		// 창 아이콘 그림 변경
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("C:/work/jsou/jpro1/src/pack7gui/pack1.jpg"));

		setLayout(null);
		setResizable(false);
		setBounds(200, 200, 400, 400);
		setBackground(Color.WHITE);
		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addKeyListener(this); // frame에 KeyListener 장착

		repaint();
		bgmSnd();

		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		Dimension frameSize = this.getSize(); // Frame의 크기

		food = Toolkit.getDefaultToolkit().getImage("C:/work/jsou/jpro1/src/pack7gui/food.png");
		if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_NUMPAD6) {
			sel = (sel == 1) ? 2 : 1;
			xx = (xx < frameSize.width) ? xx += 10 : -img.getWidth(this);
		}

		if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_NUMPAD4) {
			sel = (sel == 3) ? 4 : 3;
			xx = (xx > -img.getWidth(this)) ? xx -= 10 : frameSize.width;
		}
		
		if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_NUMPAD2) {
			sel = (sel == 5) ? 6 : 5;
			yy = (yy < frameSize.height) ? yy += 10 : -img.getHeight(this);
		}
		
		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_NUMPAD8) {
			sel = (sel == 7) ? 8 : 7;
			yy = (yy > -img.getHeight(this)) ? yy -= 10 : getHeight();
		}
		
		chk++;
		eatChk();
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		String fn = "C:/work/jsou/jpro1/src/pack7gui/pack" + String.valueOf(sel) + ".jpg";
		img = Toolkit.getDefaultToolkit().getImage(fn);

		g.clearRect(0, 0, getWidth(), getHeight());
		g.drawImage(food, fx, fy, this); // 먹이 그림
		g.drawImage(img, xx, yy, this);  // 캐릭터 그림
	}

	// 배경 음악 재생
	private void bgmSnd() {
		File bgm;
		AudioInputStream stream;
		AudioFormat format;
		DataLine.Info info;
		bgm = new File("C:/work/jsou/jpro1/src/pack7gui/beginning.wav");

		try {
			stream = AudioSystem.getAudioInputStream(bgm);
			format = stream.getFormat();
			info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);
			FloatControl gainControl = (FloatControl) clip
					.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(-20.0f);
			clip.start();
		} catch (Exception e) {

		}
	}

	@Override
	public void run() {// 이동 시 음악 재생용 스레드
		while (true) {
			try {
				if (clip.getFrameLength() == clip.getFramePosition())
					bgmSnd();
				if (chk > 0) {
					movingSnd(sel);  // 이동 시 음악 재생
					thread.notify(); // 재생횟수 0 이상일 시 재생 시작
					if (chk > 10)
						chk = 10;  // 이동음 재생횟수 많을 시 제한.
				} else {
					thread.wait(); // 재생 횟수 0 일때 재생 중지
				}
			} catch (Exception e) {

			}
		}
	}

	// 이동시 음악 재생
	public void movingSnd(int num) {
		try {
			// 입 벌리거나 닫았을 때 소리 변경
			double snd = num % 2 + 1;
			byte[] buf = new byte[1];
			AudioFormat af = new AudioFormat((float) 44100, 8, 1, true, false);
			SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
			//SourceDataLine은 데이터를 기입 할 수 있는 데이터 선이다. 믹서에 소스 역할을 한다.
			//응용 프로그램은 바이트 버퍼링을 처리하고 믹서에 전달 소스 데이터 라인, 오디오 바이트를 기록한다.
			//믹서는 샘플을 사운드 카드 오디오 출력 장치를 통해 나타낼 수 있다.
			sdl.open();
			sdl.start();
			// 누적 이동음에 따라 소리 길이 재설정 /chk
			for (int i = 0; i < 100 / chk * (float) 44100 / 1000; i++) {
				// 누적 이동음에 따라 주파수 재설정
				double angle = i / ((float) 44100 / 440) * snd * (1 + chk) * Math.PI;
				buf[0] = (byte) (Math.sin(angle) * 100);
				sdl.write(buf, 0, 1);
			}
			sdl.drain();
			sdl.stop();
		} catch (Exception e) {

		}
		chk--; // 남은 소리 재생 횟수 감수
	}

	// 먹었는지 확인
	public void eatChk() {
		Dimension frameSize = this.getSize(); // Frame의 크기
		if ((fx < xx + 40) && (fx > xx - 10) && (fy < yy + 40) && (fy > yy - 10)) { // 좌표 확인
			// 먹이 좌표 재설정
			fx = (int) (Math.random() * (frameSize.width - 30) + 10);
			fy = (int) (Math.random() * (frameSize.height - 30) + 10);

			point++;  // 점수 추가 후 포인트 획득
			setTitle("Point : " + point);

			eatSnd(); // 먹이 획득 음악 재생
		}
	}

	// 먹었을 때 소리 재생
	private void eatSnd() {
		File bgm;
		AudioInputStream stream;
		AudioFormat format;
		DataLine.Info info;
		bgm = new File("c:/work/pack/eat.wav");

		try {
			stream = AudioSystem.getAudioInputStream(bgm);
			format = stream.getFormat();
			info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);
			clip.start();
		} catch (Exception e) {

		}
	}

	public static void main(String[] args) {
		new Ex50PackmanFinal();
	}

	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}
}
