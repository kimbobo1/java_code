package pack;

public class Students {
	private int num, kor, eng;
	private String name;

	public Students(int num, String name, int kor, int eng) {
		this.num = num;
		this.name = name;
		this.kor = kor;
		this.eng = eng;
	}

	public int getNum() {
		return num;
	}

	public String getName() {
		return name;
	}

	public int getKor() {
		return kor;
	}

	public int getEng() {
		return eng;
	}
	
	public int getTotal() {
		return kor + eng;
	}
}
