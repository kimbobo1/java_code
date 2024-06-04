package lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class PracLambda {

	public static void main(String[] args) {
		List<String> list = Arrays.asList("Apple", "Banana", "Cherry");  // 배열을 리스트 형태로 변환, (List는 인터페이스 ArrayList는 클래스)
		
	    // 람다 표현식 사용
		list.forEach(i -> System.out.println(i));  // forEach에는 consumer가 있다.
		list.forEach(System.out::println);
		
		Consumer<Integer> con = num -> System.out.println(num);
		
		con.accept(10);
		System.out.println();

	}

}
