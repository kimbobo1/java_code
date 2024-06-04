package pack6thread;

public class Ex41BankMain {
	public static Ex41Bank bank = new Ex41Bank();
	
	public static void main(String[] args) {
		System.out.println("원금 : " + bank.getMoney());
		
		Ex41Tom tom = new Ex41Tom();     // 각각의 스레드가 돌고 있음. 동시에 시간은 가므로 2초 지나고 tom 결과 출력, 그리고 1초 뒤 wife 결과 출력
		Ex41Wife wife = new Ex41Wife();  // synchronize를 하게되면 tom이 끝나고 3초가 지난 뒤 wife 결과가 출력됨 -> 동기화가 되었으므로 한쪽의 자원을 사용하고 있으면 lock이 걸린다. 해당 메소드가 끝나면 다음 메소드를 실행한다.
		
		tom.start();
		wife.start();
	}

}
