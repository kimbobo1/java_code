package pack6thread;

public class Ex41Wife extends Thread{
	
	@Override
	public void run() {
		Ex41BankMain.bank.minusMoney(2000);  
		
		System.out.println("부인 출금 후 잔고 : " + Ex41BankMain.bank.getMoney());
	}

}
