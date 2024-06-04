// 변수 내보내기
export let user = '홍길동'; 

// JSON 형식의 자료 export
const name = '사과';
const price = 5600;
export let msg = {name, price};  // JSON 타입의 객체를 export

// 함수 export
export function sayHi(user) {
	return `안녕 ${user}님`;
}

export let func = function add(a, b) {
	return a + b;
}

function f1(user) {
	return `안녕? ${user}님`;
}

function f2(user) {
	return `반가워 ${user}`;
}

export {f1, f2}; // 두 개 함수 한 번에 export

// default 값 export => default는 1회 가능
export default '졸려도 이겨내세요';

// 배열 export
export let months = ['5월', '6월', '7월'];

// 상수 export
export const Cur_YEAR = 2024;

// 클래스 export
export class HelloClass {
	constructor(msg) {
		this.msg = msg;
	}
}

// 동적으로 함수 export
export function hi() {
	alert('안녕');
}

export function bye() {
	alert('안녕');
}
