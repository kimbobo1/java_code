package pack;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Net4TestClient {

	public static void main(String[] args) {
		try {
//			InetAddress ia = InetAddress.getByName("127.0.0.1");
//			//System.out.println(ia);
//			Socket socket = new Socket(ia, 9999);  // (접속할 주소, 포트번호)를 인수로 전달
			
			Socket socket = new Socket("127.0.0.1", 9999); // 서버와 접속. -> 서버의 accept() 가 받는다. 그럼 server의 socket이 new 됨. 이 socket과 1대1로
			
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
			writer.println(".....test" + "\n");  // 127.0.0.1 주소에 전달, 서버로 자료 전송

			writer.close(); // 단발성
			socket.close(); 
		} catch (Exception e) {
			System.out.println("client err : " + e);
		}
		
		// 특정 컴퓨터의 접속 후 메세지 전달용
		
	}

}
