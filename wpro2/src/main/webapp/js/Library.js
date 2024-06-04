class Library {
	constructor(name) {  // 매개변수 : 도서관명
		this.name = name;
		this.catalog = [];  // 초기 카탈로그용 빈 배열
	}
	
	// 도서관에 책이나 잡지 추가
	addItem(item) {
		this.catalog.push(item);
		console.log(`${item.title} 이(가) 도서관에 추가되었습니다.`);
	}
	
	// 제목으로 도서관에서 책이나 잡지 검색
	findItem(title) {
		console.log(this.catalog.find(item => item.title === title));
		// catalog 배열에서 제목이 일치하는 첫 번째 항목
		return this.catalog.find(item => item.title === title);
	}
	
	// 도서관의 책이나 잡지 모두 출력
	listItems() {
		console.log(`${this.name} 도서관의 책 목록`);
		this.catalog.forEach(item => console.log(item.toString()));
	}
	
}

export default Library;