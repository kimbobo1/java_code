package domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// sangdata 테이블과 연결
@Entity  // JPA를 사용해 테이블과 매핑할 클래스에 붙여주는 어노테이션
@Table(name="sangdata")  // sangpumTable 클래스를 sangdata와 연결
public class SangpumTable {
	@Id
	@Column(name="code")  // 원본 테이블의 칼럼명
	private int code;
	
	@Column(name="sang", nullable = false)  // sang 칼럼은 null을 허용하지 않음.
	private String sang;
	
	private int su;

	private int dan;
	
	public SangpumTable() {  
		// 빈 생성자라도 생성자는 있어야 함.
		// JPA에서는 생성자가 필수
	}
	
	public SangpumTable(int code, String sang, int su, int dan) {  
		this.code=code;
		this.sang=sang;
		this.su=su;
		this.dan=dan;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getSang() {
		return sang;
	}

	public void setSang(String sang) {
		this.sang = sang;
	}

	public int getSu() {
		return su;
	}

	public void setSu(int su) {
		this.su = su;
	}

	public int getDan() {
		return dan;
	}

	public void setDan(int dan) {
		this.dan = dan;
	}
}
