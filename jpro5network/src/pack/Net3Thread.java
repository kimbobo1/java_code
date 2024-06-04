package pack;

import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

// 멀티 스레드로 멀티 태스킹 : 복수 개의 문서 읽기
public class Net3Thread implements Runnable {
	private String url;
	private String title;

	public Net3Thread(String url, String title) {
		this.url = url;
		this.title = title;
	}

	@Override
	public void run() {
		try {
			Document doc = Jsoup.connect(url).get(); // 네트워크를 통해 다른 컴에 접속 후 자료 읽기
			String text = doc.text(); // 웹 문서의 모든 텍스트를 읽어온 상태

			System.out.println("-------------------");
			System.out.println("문서 제목 : " + title);

			printKoreanText(text);
		} catch (Exception e) {
			System.out.println("read err : " + e);
		}
	}

	private static void printKoreanText(String text) {
		// 정규 표현식 사용 -> 한글과 공백만 얻기
		Pattern pattern = Pattern.compile("[가-힣\\s]+"); // 공백은 : s, 한글자 이상 복수개 : +
		Matcher matcher = pattern.matcher(text);

		while (matcher.find()) {
			String line = matcher.group().trim();
			// trim() 메소드 : 앞뒤의 공백을 제거
			if (!line.isEmpty() && line.length() > 1) { // 빈 줄, 한 글자는 제외
				System.out.println(line);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		String[] titles = { "백설공주", "인어공주" };

		String[] urls = { "https://ko.wikipedia.org/wiki/" + URLEncoder.encode(titles[0], "UTF-8"), // 아스키 코드 값 외에는
																									// 인코딩함.
				"https://ko.wikipedia.org/wiki/" + URLEncoder.encode(titles[1], "UTF-8") };

		for (int i = 0; i < urls.length; i++) {
			Thread thread = new Thread(new Net3Thread(urls[i], titles[i]));
			thread.start();
		}

		System.out.println("프로그램 종료");
	}
}
