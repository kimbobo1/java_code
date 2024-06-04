package Personal;

public class Main {
	public static void main(String[] args){
		Runnable r1 = new MyRunnable(); 
		Runnable r2 = new MyRunnable();
		Thread t1 = new Thread(r1);
		t1.start();
		Thread t2 = new Thread(r2);
		t2.start();
	}

}
