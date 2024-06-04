package Personal;

public class Main2 {

	public static void main(String[] args) {
		Thread th1 = new InheritThread();
        th1.start();
        Thread th2 = new InheritThread();
        th2.start();
	}

}
