package pack;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

// URL 클래스로 특정 웹서버 컴의 문서 읽기
// 인터넷이 가능한 서버들의 자원에 접근하여 주소 및 기타 정보를 다루는 클래스
public class Net1URL {
	
	public static void main(String[] args) {
		try {
			URL url = new URL("https://www.daum.net");
			// https://www.daum.net:80/index.html
			// http 보안용 서버 : https. tcp 프로토콜 기반 응용 프로그램 계층에서 사용
			InputStream is = url.openStream();  // 데이터의 흐름 = 스트림
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			//System.out.println(br.read());
			
			// 읽은 문서 파일로 저장
			PrintWriter pw = new PrintWriter(System.out, true); 			
			PrintWriter fw = new PrintWriter(new FileOutputStream("C:/work/ok.html")); 	// 해당 경로에 ok.html 파일로 저장
			// 내 컴퓨터에 있는 html을 읽는 방법 file:///
			// parsing
			// parser
			String line = "";   // 한 줄씩 읽기 위해 선언
			while((line = br.readLine()) != null) {  // 줄이 끝나기 전까ek지 반복
				pw.println(line);  // console로 출력
				fw.println(line);  // file로 저장
				fw.flush();  // 버퍼 비우기
			}
			br.close(); is.close(); pw.close(); fw.close();
		} catch (Exception e) {
			System.out.println("err : " + e);
		}

	}

}
