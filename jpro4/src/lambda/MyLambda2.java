package lambda;

@FunctionalInterface
interface MyInter {
	void aaa();
}
@FunctionalInterface
interface MyInterArg {
	void bbb(int a, int b);
}
@FunctionalInterface
interface MyInterArgReturn {
	int ccc(int a, int b);
}

public class MyLambda2 {
	public static void main(String[] args) {
		// 1. 인자가 없는 추상 메소드 처리 -> Legacy한 방법
		MyInter inter = new MyInter() {
			@Override
			public void aaa() {
				System.out.println("익명 클래스의 aaa() 메소드 오버라이딩");
				
			}
		};
		inter.aaa();
		
		// 람다식으로 표현 1
		MyInter inter2 = () -> System.out.println("익명 클래스의 aaa() 메소드 오버라이딩 : 람다식 사용");  // 명령문이 하나일 경우 {} 생략 가능

		inter2.aaa();
		
		MyInter inter3 = () -> {  // 명령문이 두 개이상인 경우 {} 생략 불가
			int imsi = 10;
			System.out.println("람다식으로");
			System.out.println("복수의 명령문 처리");
			System.out.println("imsi : " + imsi);
		};
		inter3.aaa();
		
		System.out.println("---------------");
		// 2. 인자가 있는 추상 메소드 처리 : 전통적
		MyInterArg interArg = new MyInterArg() {
			
			@Override
			public void bbb(int a, int b) {
				System.out.println("두 수의 합은 " + (a + b));
			}
		};
		interArg.bbb(3, 4);
		
		// 람다식으로 표현 2 : 만약 arg가 1개일 경우, (a) -> 또는 a -> 로 사용 가능
		MyInterArg interArg2 = (a, b) -> System.out.println("두 수의 합은(람다 표현식 사용) " + (a + b));
		interArg2.bbb(3, 4);
		
		System.out.println("---------------");
		
		// 3. 인자가 있고 반환형도 있는 추상 메소드 처리 : 전통적
		MyInterArgReturn argReturn = new MyInterArgReturn() {
			
			@Override
			public int ccc(int a, int b) {
				System.out.println("ccc() 메소드 처리");
				return a + b;
			}
		};
		int result = argReturn.ccc(5, 6);
		System.out.println("두 수를 더한 결과(result) : " + result);
		
		// 람다식으로 표현 3
		MyInterArgReturn argReturn2 = (m, n) -> (m + n); // 수행할 문장이 하나인 경우, return도 생략이 가능, 수행할 문장이 여러 개라면 return문 생략 불가
		
//		MyInterArgReturn argReturn2 = (m, n) -> {
//			System.out.println("ccc() 메소드 처리");
//			return m + n;
//		};
		int result2 = argReturn2.ccc(5, 6);
		System.out.println("두 수를 더한 결과(result2) : " + result2);
	}

}
