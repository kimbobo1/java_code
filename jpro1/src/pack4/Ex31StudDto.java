package pack4;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Getter
//@Setter
//@NoArgsConstructor  // argument가 없는 생성자가 생성된다.
//@AllArgsConstructor // argument가 있는 생성자가 생성된다.
@Data
public class Ex31StudDto {
	private String name;
	private int kor;
	private int eng;
	
}
