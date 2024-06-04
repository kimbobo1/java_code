package pack;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

// Echo Server : 클라이언트의 요청에 계속 반응을 보이는 서버

public class Net5EchoServer {
	ServerSocket ss;
	Socket socket;
	PrintWriter out;
	BufferedReader reader;
	
	public Net5EchoServer() {
		try {
			ss = new ServerSocket(8888); // 서버 객체 생성
			System.out.println("서버 서비스 중...");
			
			socket = ss.accept();  // 클라이언트가 접속하기를 대기.. -> 접속하면 바로 socket 객체를 만든다

			out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);  // out 객체 생성, 소켓을 통해 나감
		
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)); // input 객체 생성
		} catch (Exception e) {
			System.out.println("Net5EchoServer err : " + e);
			System.exit(0);
		}
	}
	
	public void receiveSendData() {
		try {
			String msg = reader.readLine();  // 클라이언트의 자료(데이터) 수신
			System.out.println("수신된 메세지 : " + msg);
			
			out.println("서버가 보낸 메세지 : " + msg + " 확인 완료");  // 클라이언트로 자료 송신
		} catch (Exception e) {
			System.out.println("receiveSendData err : " + e);
		} finally {			
			try {
				reader.close(); out.close(); socket.close(); ss.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	
	public static void main(String[] args) {
		while(true) {
			Net5EchoServer server = new Net5EchoServer();
			
			server.receiveSendData();
		}
	}

}
