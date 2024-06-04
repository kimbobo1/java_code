package pack;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Net5Client {
	Socket socket;
	PrintWriter out;
	BufferedReader reader;
	
	public Net5Client() {
		try {
			socket = new Socket("127.0.0.1", 8887);

			out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);  // out 객체 생성, 소켓을 통해 나감
			
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)); // input 객체 생성
		} catch (Exception e) {
			System.out.println("Net5Client err : " + e);
		}
	}
	
	public void sendReceiveData() {
		try {
			Scanner scanner = new Scanner(System.in);
			System.out.print("전송 메시지 입력 : ");
			String data = scanner.nextLine();
			out.println(data);  // 서버로 전송
			
			String re_data = reader.readLine();  // 서버가 보낸 자료 수신
			System.out.println("수신된 자료는 " + re_data);
		} catch (Exception e) {
			System.out.println("sendReceiveData err : " + e);
		} finally {
			try {
				reader.close();
				out.close();
				socket.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	
	public static void main(String[] args) {
		Net5Client client = new Net5Client();
		
		client.sendReceiveData();
	}

}
