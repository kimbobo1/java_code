package Personal;

public class MyRunnable implements Runnable{
	@Override
	public void run() {
		for(int i = 0; i < 50; i++) {
            System.out.print(i + " ");
            try {
				Thread.sleep(100);
			} catch (Exception e) {
				
			}
        }
	}
}
