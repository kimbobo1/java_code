package lambda;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

// Stream 인터페이스 : 컬렉션, 배열 등의 저장 요소를 하나씩 참조하여 인터페이스(람다식)를 적용하며, 반복 처리가 가능하게 한다. 반복자 역할.
// 정렬, 집계, 빅데이터 처리 등도 가능하다.
// 1회용, 내부 반복으로 작업 처리. 원본 데이터를 변경하지 않음.
// https://hstory0208.tistory.com/entry/Java%EC%9E%90%EB%B0%94-Stream%EC%8A%A4%ED%8A%B8%EB%A6%BC%EC%9D%B4%EB%9E%80
public class MyStream {

	public MyStream() {
		stream(); // Stream 생성
		collectionStream(); // 컬렉션에 스트림 적용
		vo(); // VO 클래스 사용
	}

	// test1()
	private void stream() {
		// 1) Collection으로 스트림 생성
		List<String> list = Arrays.asList("a", "b", "c");
		Stream<String> listStream = list.stream();  // stream이 생성됨
		
		// 2) 배열로 스트림 생성
		Stream<String> stream1 = Stream.of("a", "b", "c");
		Stream<String> stream2 = Stream.of(new String[]{"a", "b", "c"});
		Stream<String> stream3 = Arrays.stream(new String[]{"a", "b", "c"});
		Stream<String> stream4 = Arrays.stream(new String[]{"a", "b", "c"}, 0, 3);  // 배열 요소 : 0 이상 3미만
		
		// 3) 원시(기본형 데이터) stream 생성
		IntStream istream = IntStream.range(5, 10);  // 기본형에 대해서도 스트림 사용 가능  // 5 이상 10 미만의 데이터를 갖는 스트림을 가짐
		//DoubleStream ...
		istream.forEach(para -> System.out.print(para + " "));  // 람다식 사용하여 출력, 5 6 7 8 9
	
		System.out.println();
	}

	// test2()
	private void collectionStream() {
		List<String> list = Arrays.asList("레밍스","팩맨","마리오");  // 아직 스트림을 만들지 않음.
//		list.add("소닉");   // 런타임 에러 발생(새로운 요소 추가 불가, 기존 요소 삭제 불가) -> 데이터 변경 불가, Exception in thread "main" java.lang.UnsupportedOperationException 
		
		// 외부 반복자 사용. 전통적인 방법.
		Iterator<String> iter = list.iterator(); 
		while(iter.hasNext()) {
			System.out.println(iter.next());
		}
		System.out.println();
		
		// 향상된 for문 사용. 전통적인 방법.
		for(String str : list) {
			System.out.println(str);
		}
		
		for(String str : list) {
			System.out.println(str);
		}
		
		System.out.println();
		
		// Stream 사용
		Stream<String> stream = list.stream();  // Stream 객체를 컬렉션으로부터 얻어옴.
		stream.forEach(str -> System.out.println(str));  // 내부 반복을 이용하여 순차적으로 처리.
//		stream.forEach(str -> System.out.println(str));  // err 발생 : 1회용(단발성), Exception in thread "main" java.lang.IllegalStateException: stream has already been operated upon or closed
	
		// 새로운 스트림 객체 생성 후 출력
		list.stream().forEach(str -> System.out.println(str));
		list.stream().forEach(System.out::println);  // list.stream().forEach(str -> System.out.println(str)); 과 동일. (:: <- 스트림을 람다식으로 표현하여 출력하고 있다는 것을 의미)
		System.out.println();
		
		// 스트림을 사용하여 체이닝 작업 : 모든 필요한 작업을 단일 스트림 파이프라인(일련의 처리 단계)에서 처리 가능.
		// 어떤 스트림의 요소들의 합을 구하는 과정에서 요소 값을 먼저 출력하고 싶은 경우
		int sum = IntStream.of(1, 3, 5, 7).peek(System.out::println).sum();  // sum(), average(), max(), min() 메소드 등을 지원함.
		System.out.println("합은 " + sum);
		
		list.stream().peek(System.out::println).forEach(System.out::println);  // 내부 반복이 일어남.
		// peek 내부에서 출력 후, 값을 변경하지 않은 채로, forEach() 내부에서 출력 -> 레밍스레밍스 팩맨팩맨 마리오마리오 순으로 출력
		
		// 병렬 처리(parallelStream() 메소드)
		System.out.println("\n병렬 처리");
		Stream<String> streamPar = list.parallelStream();  // 병렬 스트림 객체 생성(병렬 처리용)
		streamPar.forEach(str -> System.out.println(str));  // 순서가 변경됨(랜덤 순서).
		
		// 정렬(sorted() 메소드)
		System.out.println("\n정렬");
		Stream<String> streamSort = list.stream().sorted();  // ascending(오름차순)이 디폴트
		Stream<String> streamSortDesc = list.stream().sorted(Comparator.reverseOrder()); // descending(내림차순)
		
		streamSort.forEach(System.out::println);
		streamSortDesc.forEach(System.out::println);
		
		Stream<String> streamSortDistinct = list.stream().distinct().sorted(); // distinct() : 중복 제거 
	}
	
	// test3()
	private void vo() {
		System.out.println("\n\nVO 클래스로 컬렉션 작성");
		List<Student> slist = Arrays.asList(
			new Student("와리오","남", 22),
			new Student("루이지","남", 25),
			new Student("마리오","남", 28),
			new Student("피치","여", 20),
			new Student("마라라","여", 22)
		);
		
		Stream<Student> stream = slist.stream();  // 스트림 객체 생성
		stream.forEach(p -> {
			System.out.println(p.getName() + " " + p.getGender() + " "+ p.getAge());
		});
		
		System.out.println("컬렉션 자료에 대한 중간 처리, 최종 처리가 가능함");
		// 각 개인의 나이를 출력하고, 최종 나이의 평균 출력
		// 방법 1
		double avg = slist.stream().  // 스트림 객체 생성
				mapToInt(Student :: getAge)  // Student 객체를 age 값으로 매핑해 age로 새 스트림 생성
				.average()  // 내부적으로 age 요소의 평균을 OptionalDouble에 저장, 값이 없을 수도 있다는 가정  // Optional<T> 클래스는 null이 올수 있는 값을 감싸는 wrapper 클래스
				.getAsDouble(); // OptionalDouble에 저장된 값 읽기
		// .으로 연결하는 위 과정이 체이닝 작업.
		System.out.println("(방법1)나이 전체 평균은 " + avg);
				
		// 방법 2
		Double avg2 = slist.stream().collect(Collectors.averagingInt(Student :: getAge));
		System.out.println("(방법2)나이 전체 평균은 " + avg2);
		
		// 방법 3
		OptionalDouble result = slist.stream().mapToDouble(Student :: getAge).average();
		result.ifPresent(res -> System.out.println("(방법3)나이 전체 평균은 " + res));  // 참일 경우에 대해서는 ifPresent() 메소드를 사용.
		
		// 필터 처리
		// 남자 나이 평균
		double avgM = slist.stream().filter(m -> m.getGender().equals("남")).mapToInt(Student :: getAge).average().getAsDouble();  // predicate : 함수형 인터페이스 중 하나, 하나의 입력을 바다 boolean 값을 반환
		// filter() : 인수에 해당하는 값만 필터링하는 메소드
		// mapToInt() : 스트림을 IntStream으로 변환해주는 메소드, Stream 에서 sum() 을 사용하려면 IntStream, LongStream, DoubleStream 와 같은 기본형 (Primitive Type) 특화 스트림을 사용해야 합니다.
		// getAsDouble() : OptionalDouble에 저장된 값을 읽는 메소드
		System.out.println("남자 나이 평균 : " + avgM);
		
		// '마' 성을 가진 자료 출력
		slist.stream().filter(ir -> ir.getName().startsWith("마")).forEach(irum -> System.out.println(irum.getName()));
		// '오' 로 끝나는 이름을 가진 사람 목록 출력
		slist.stream().filter(ir -> ir.getName().endsWith("오")).forEach(irum -> System.out.println(irum.getName()));
	}
	
	// 내부 클래스 생성
	class Student {
		private String name;
		private String gender;
		private int age;
		
		public Student(String name, String gender, int age) {
			this.name = name;
			this.gender = gender;
			this.age = age;
		}
		
		public String getName() {
			return name;
		}
		
		public String getGender() {
			return gender;
		}
		
		public int getAge() {
			return age;
		}
	}

	public static void main(String[] args) {
		new MyStream();
	}

}
