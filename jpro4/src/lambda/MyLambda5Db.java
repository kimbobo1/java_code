package lambda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class MyLambda5Db {

	public MyLambda5Db() {
		// Consumer 인터페이스는 accept() 메소드를 가지고 있음 -> 단일 메소드라 추상 메소드를 가지고 있는데 그것이 accept() 메소드. 반환형은 없음, 데이터를 사용만 함.
		// 주로 Collection API의 forEach에서 사용
		abc();

		System.out.println();

		// 드라이버 로딩
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (Exception e) {
			System.out.println("Driver Loading fail : " + e);
			return;
		}

		queryTable("select * from sangdata", rs -> {
			System.out.println("처리 2");
			try {
				while (rs.next()) {
					System.out.println(rs.getString("code") + " " + rs.getString("sang") + " " + rs.getString("su")
					+ " " + rs.getString("dan"));
				}
			} catch (Exception e) {
				System.out.println("err : " + e);
			}
		});
	}

	// Consumer 인터페이스 연습
	private void abc() {
		Consumer<Integer> printInt = i -> System.out.println(i); // Consumer 인터페이스 객체 생성, i 값을 accept() 메소드로 입력받으면 출력하는 람다 표현식.
		
		int i = 5;
		printInt.accept(i);  // 화살표 좌측의 변수 값이 accept() 메소드의 인수, 타입은 <> 괄호 안의 타입과 동일해야 함.
		
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
		numbers.forEach(printInt); // 인수로 Consumer 타입을 가진다.
		
		//numbers.forEach(i -> System.out.println(i)); ??
	}

	private void queryTable(String sql, Consumer<ResultSet> consumer) { 
		// try 문에 매개변수를 줄 수 있다. try~with~resources 문법에 준함.
		try (Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/test", "root", "123");
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
				System.out.println("처리 1");
				consumer.accept(rs);  // 인수로 ResultSet 타입 변수 rs가 들어감. 
				System.out.println("처리 3");
				rs.close();
				pstmt.close();
				conn.close();
		} catch (Exception e) {
			System.out.println("queryTable err : " + e);
		}
	}

	public static void main(String[] args) {
		new MyLambda5Db();

	}

}
