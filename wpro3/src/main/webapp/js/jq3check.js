$(document).ready(function() {
	$('#id_err').hide();
	$('#age_err1').hide();
	$('#age_err2').hide();
	$('#pwd_err1').hide();
	$('#pwd_err2').hide();
	
	$('#btnSend').click(function() {
		// 입력 자료 오류 검사 후, 서버로 전송
		// id 검사
		let id = $('#userid').val();  // document.querySelector("#userid").value;와 동일
		//alert(id);
		if(id.length < 2 || isNaN(id) === false) {
			//alert('에러 메시지');
			$('#id_err').show();
			return false;
		} else $('#id_err').hide();
		
		
		
		// age 검사
		let age = $('#age').val();
		if(age.length < 1) {
			$('#age_err1').show();
			return false;
		} else $('#age_err1').hide(); 
		
		// age는 숫자만 허용.
		/* 방법 1: 반복문 사용
		for(let i = 0; i < age.length; i++) {
			let data = age.charAt(i).charCodeAt(0);  // age에서 하나씩 꺼냄. -> ascii 코드값이 나온다. 
			//alert(data);
			if(data < 48 || data > 57) {  // 숫자가 아님
				$('#age_err2').show();
				return false;  // return false; -> 둘 중 하나만 작성.
			} else $('#age_err2').hide();
		}*/
		
		/* 방법 2: 사용자 정의 하수 작성 후 호출
		if(!isPositiveInteger(age) || age < 15 || age > 100) {
			$('#age_err2').show();
			return false;
		} else $('#age_err2').hide(); */
		
		// 방법중 하나 : 사용자 정의 함수(정규표현식 사용) 작성 후 호출	
		if(!isValidAge(age)) {
			$('#age_err2').show();
			return false;
		} else $('#age_err2').hide();
		
		
		
		// 비밀번호 입력
		let pwd1 = $('#pwd1').val();
		if(pwd1.length < 1) {
			// next() : nextSibling을 의미
			// prev() : previousSibling을 의미
			$('#pwd1').next().show();
			return false;
		} else $('#pwd1').next().hide();
		
		let pwd2 = $('#pwd2').val();
		if(pwd1 !== pwd2) {
			$('#pwd_err2').show();
		} else $('#pwd_err2').hide();
		
		// form 태그에서 입력한 자료를 서버 파일로 전송
		// attr(속성명, 속성값) : 태그의 속성 전달
		$('#signFrm').attr('action', 'jq3.jsp').attr('method','post').submit();
	});
});

function isPositiveInteger(para) {
	let num = Number(para);
	return Number.isInteger(num) && num > 0;
}

function isValidAge(para) {  // /^(1[5-9] -> 15-19  [2-9][0-9] -> 20 ~ 99
	let ageRegex = /^(1[5-9]|[2-9][0-9]|100)$/;
	// test() : 주어진 문자열일 정규 표현식을 만족하는지 판별하고, 그 여부를 boolean 값으로 반환
	return ageRegex.test(para); // age가 정규표현식과 일치하는지 테스트한 값 반환
}