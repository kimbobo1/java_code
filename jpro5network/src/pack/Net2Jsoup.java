package pack;

import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

// 제3자가 제공하는 라이브러리(jsoup.jar)를 사용해 웹 스크래핑
// HTML 문서 데이터 구문 분석, 자료 추출 및 조작용 오픈 소스

public class Net2Jsoup {

	public static void main(String[] args) {
		// 위키백과 사이트에서 검색 결과 읽기 : 싱글 태스킹
		//https://ko.wikipedia.org/wiki/%EB%B0%B1%EC%84%A4_%EA%B3%B5%EC%A3%BC // 백설 공주를 인코딩해야함
		
		try {
			//System.out.println(URLEncoder.encode("백설공주", "UTF-8"));  // 참고, 백설 공주를 부분으로 끊어 인코딩함.
			
			String url = "https://ko.wikipedia.org/wiki/" + URLEncoder.encode("백설공주", "UTF-8");
			// Document : 웹페이지 문서
			Document doc = Jsoup.connect(url).get();
			String text = doc.text();  // 웹 문서의 모든 텍스트를 읽어온 상태
			//System.out.println(text);
			
			//읽어온 자료에 대해 한글만 추출
			printKoreanText(text);
			
		} catch (Exception e) {
			System.out.println("err : " + e.getMessage());
		}
	}
	
	private static void printKoreanText(String text) {
		// 정규 표현식 사용 -> 한글과 공백만 얻기
		Pattern pattern = Pattern.compile("[가-힣\\s]+");  // 공백은 : s, 한글자 이상 복수개 : +
		Matcher matcher = pattern.matcher(text);
		
		while(matcher.find()) {
			String line = matcher.group().trim();  
			// trim() 메소드 : 앞뒤의 공백을 제거
			if(!line.isEmpty()) {   // 빈 줄은 제외
				System.out.println(line);
			}
		}
	}
}
