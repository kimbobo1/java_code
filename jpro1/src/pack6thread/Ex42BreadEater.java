package pack6thread;

public class Ex42BreadEater extends Thread{  // 빵 소비자
	private Ex42BreadPlate plate;
	
	public Ex42BreadEater(Ex42BreadPlate plate) {   // new Ex42BreadEater(new Ex42BreadPlate());
		this.plate = plate;
	}
	
	@Override
	public void run() {
		for(int i = 0; i < 30; i++) {
			plate.eatBread();
		}
	}
}
