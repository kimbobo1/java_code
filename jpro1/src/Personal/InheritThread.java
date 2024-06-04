package Personal;

public class InheritThread extends Thread {
    @Override
    public void run() {
        for(int i = 0; i < 50; i++) {
        	System.out.println(i + ":" + Thread.currentThread().getName());
            try {
				Thread.sleep(100);
			} catch (Exception e) {
				
			}
        }
    }
}
