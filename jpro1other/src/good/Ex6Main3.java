package good;

import pack1.Ex6Bank;

public class Ex6Main3 {

	public static void main(String[] args) {
		// 프로젝트가 다른 곳에서 Ex6Bank를 사용하려면
		Ex6Bank kildong = new Ex6Bank();
		kildong.dePosit(5000);
		kildong.withDraw(2000);
		System.out.println("kildong 예금액 : " + kildong.getMoney());
	}

}
