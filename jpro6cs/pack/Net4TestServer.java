package pack;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

// jpro6sc 프로젝트는 class 파일과 java 파일의 위치를 같은 곳에 위치하도록 설정
public class Net4TestServer {

	public static void main(String[] args) {
		// 단순 서버
		ServerSocket ss = null;  // 소켓 객체 ss
		
		// 내 컴퓨터가 사용 중인 port number 확인
/*
		for(int i = 0; i < 65536; i++) {
			try {
				ss = new ServerSocket(i);  // 객체를 만들고, 포트를 만드려고 하는데 사용 중이면 에러가 발생할 것임.
				ss.close();  // 
			} catch (Exception e) {
				System.out.println(i + "번 port는 사용 중");
			}
		}
		System.out.println("확인 종료");
*/
		
		Socket socket = null;  // TCP 기반의 통신용 클래스(파일)
		try {
			ss = new ServerSocket(9999);   // 이거는 서버 소켓임.
			System.out.println("서버 서비스 시작..."); // 둘 사이의 통신이 가능한지만 확인
			socket = ss.accept(); // 서버 소켓으로부터 클라이언트 컴퓨터와 통신하기 위한 개별 소켓을 생성  // 무한 루프에 빠져 있는 상태. 클라이언트가 이 서버에 접속하기를 기다리고 있다.
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(),
					StandardCharsets.UTF_8)); // 소켓으로부터 데이터를 받는다.
			String data = reader.readLine();
			System.out.println("수신 자료 : " + data);  // 메시지를 일방적으로 받기만 하는 상태이다.
			
			reader.close();
			socket.close();
			ss.close();
			
		} catch (Exception e) {
			System.out.println("Server err: " + e);
		}
		
	}

}
