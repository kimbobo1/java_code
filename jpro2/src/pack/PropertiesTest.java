package pack;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesTest {

	public static void main(String[] args) {
		// properties 파일 읽기
		// properties 파일이란 무엇인가?
		// 개발자들이 소스에 직접 값을 하드 코딩하는 경우가 종종있다.
		// 하지만 이렇게 하드코딩할 경우 하드 코딩된 변수가 많아지면 유지보수도 힘들어질 뿐더러
		// 암호화에 사용되는 key값들을 하드코딩하는 경우 보안적인 측면에서 
		// 이런 경우 파일명.properties에 정보를 담은 후 읽어 줄 수 잇다.
		Properties prop = new Properties();
		
		try {
			prop.load(new FileInputStream("C:\\work\\jsou\\jpro2\\src\\pack\\ex.properties"));
			System.out.println(prop.getProperty("mes1"));
			System.out.println(prop.getProperty("mes2"));
		} catch (Exception e) {
			System.out.println("err : "  + e);
		}

	}

}
