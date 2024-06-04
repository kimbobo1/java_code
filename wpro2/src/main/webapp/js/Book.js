// 일반 도서
class Book {
	constructor(title, author, isbn) {
		this.title = title;
		this.author = author;
		this.isbn = isbn;
	}
	
	toString() {  // 책 정보를 문자열로 반환
		return `책 제목 : ${this.title}}, 저자 : ${this.author}, ISBN : ${this.isbn}`;
	}
}

export default Book;  // Book 클래스를 모듈로 export