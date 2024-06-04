import Book from './Book.js';

class Magazine extends Book {  // Book 클래스를 상속받은 Magazine
	constructor(title, author, isbn, issueNumber) {
		super(title, author, isbn);
		this.issueNumber = issueNumber;  // 잡지 호수
	}
	
	toString() {  // 책 정보를 문자열로 반환
		return `책 제목 : ${this.title}, 저자 : ${this.author}, ISBN : ${this.isbn}, 호수 : ${this.issueNumber}`;
	}
}

export default Magazine; 