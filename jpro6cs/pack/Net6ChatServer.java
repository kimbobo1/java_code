package pack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 멀티 채팅 서버 : Thread + Socket
public class Net6ChatServer {
	private static final int port = 5000;
	//private static List<Socket> clients = new ArrayList<Socket>();
	
	// 스레드에 최적화된 리스트(스레드용 arrayList)
	// CopyOnWriteArrayList : 컨텐츠를 읽어 어딘가에 전달할 때, 컨텐츠를 복사해서 전달함. 스레드에서 안심하고 처리가 가능. -> 신뢰도가 높아짐(데이터 누수 X)
	private static List<Socket> clients = new CopyOnWriteArrayList<Socket>(); // 클라이언트가 들어올 때마다 여기에 추가됨
	
	// ExecuterService를 이용하면 Thread pool을 생성해 병렬처리를 할 수 있다.
	private static ExecutorService pool = Executors.newFixedThreadPool(4);  // 인수로 pool의 크기를 준다.
	
	
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(port);
		System.out.println("채팅 서버 서비스 시작...");
		
		try {
			while(true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("새 접속자와 연결 : " + clientSocket.getInetAddress());  // 주소 확인
				clients.add(clientSocket); // 새로운 사람이 들어올 때마다 clients 리스트에 추가되어야 함
				
				// 스레드 풀 객체가 스레드를 실행
				pool.execute(new ClientHandler(clientSocket));
			}
		} finally {
			serverSocket.close();
		}
	}

	static class ClientHandler implements Runnable{
		private Socket socket;
		private BufferedReader in;
		private PrintWriter out;
		
		public ClientHandler(Socket socket) throws IOException{
			this.socket = socket; 
			in = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
			out = new PrintWriter(socket.getOutputStream(), true);
		}
		
		@Override
		public void run() {
			try {
				String name = in.readLine();  // 클라이언트(접속자)명 받기
				if(name == null) {
					throw new IOException("클라이언트 연결이 끊어졌습니다");
				}
				System.out.println(name + "님이 접속했습니다");
				broadcastMessage("^^;" + name + "님 입장 ^^");
				
				String message;
				
				while((message = in.readLine()) != null) {  // 메시지 수신
					broadcastMessage(name + "님 메세지: " + message);
				}
			} catch (Exception e) {
				System.out.println("접속자 연결 오류" + e.getMessage());
			} finally {
				try {
					if(socket != null) {
						socket.close();
						clients.remove(socket);
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		
		
		// 스레드에 의해 호출됨?
		private void broadcastMessage(String message) {
			for(Socket client: clients) {
				try {
					if(!client.isClosed()) {
						// 클라이언트로 송신할 데이터를 위한 PrintWriter 객체를 생성해 송신 후 자동으로 flush함.
						PrintWriter clientOut = new PrintWriter(client.getOutputStream(), true);
						clientOut.println(message);
					}
				} catch (Exception e) {
					System.out.println("broadcastMessage err : " + e.getMessage());
				}
			}
		}
	}
}


