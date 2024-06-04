package debugtest;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import debug.Calculator;

// 테스트는 이 파일에서
public class CalculatorTest {
	// CalculatorTest calculator = new CalculatorTest();
	Calculator calculator;

	@Before // @Test 전에 초기화하는 용도로 사용
	public void setup() {
		calculator = new Calculator();
	}

	@Test
	public void testPlus() { // 녹색 불이 들어오면 단위 테스트에 성공했다는 것을 의미
		assertTrue(calculator.plus(8, 4) == 12); // 맞으면 true, 틀리면 false

		int a = 8, b = 8;
		assertEquals(a, b); // 기본형 변수인 두 값이 같은지 확인, 같지 않으면 JUnit에서 x표시가 나타남

		int[] arr = { 0, 0 };
		int arr2[] = new int[2];
		assertArrayEquals(arr, arr2); // 두 배열의 요소값 비교, 같으면 테스트 성공, 다르면 Failures
	}

	@Ignore
	@Test
	public void testMulti() { // 해당 메소드는 Ignore 어노테이션에 의해 무시된다.
		fail("Not yet implemented");
	}

	@Test(timeout = 1000) // 1초 단위로 수행시간 검사(일정 시간 내 작업이 정상적으로 완료되는지 확인)
	public void testDivide() {
		assertTrue(calculator.divide(8, 4) == 2);
		//assertTrue(calculator.divide(10, 4) == 2.5);

		for (int i = 0; i < 10000; i++) {  // 수행시간이 1초 이내인지 확인하기 위해 사용, 이 과정이 1초 단위 내로 끝나지 않으면 에러가 발생(timeout=1000이므로)
			System.out.println(i + " ");
		}
	}
}
