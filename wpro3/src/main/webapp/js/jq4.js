$(document).ready(function(){
	// HTML 문서를 text로 읽기
	$('#test1').click(function() {
		//alert("a");
		$("#disp").empty();
		
		//$("#disp").load("jq4.html");  // 비동기 방식 : jq4.html을 읽어 id가 disp인 태그에 출력
		$("#disp").hide().load("jq4.html", function() {
			$(this).fadeIn(); // fadeIn() : 서서히 출력되도록 하는 이펙트 처리 메소드
		});
	});
	
	// json 읽기
	$('#test2').click(function() {
		$("#disp").empty();
		
		$.getJSON('jq4json.jsp', function(datas) {  // getJSON() : json 전용 메소드
			//alert(datas);
			//$("#disp").text(datas); object 출력
			let items = [];
			let str = "<ul>";
			$.each(datas.sangpum, function(index, data) {  // index는 기본적으로 0부터 시작
				//alert(index + " " + data);
				
				 let s = "<li>" + (index + 1) + ")" + data.code + " " + data.sang
				       + " " + data.su + " " + data.dan + "</li>";
				
				items.push(s);
			});
			str +=  items.join('') + "</ul>";
			// 배열의 모든 항목을 하나의 문자열로 결합
			
			$("#disp").html(str);
		}); 
	});
	
	$('#test3').click(function() {
		//alert("test3");
		$.get('jq4xml.jsp', function(datas){
		//$.post('jq4xml.jsp', function(datas){	
			//alert(datas);   // object XMLDocument
			// find() : 노드를 찾을 때 사용, 하위 element를 찾을(검색) 때 사용
			//alert($(datas).find("sangpum").length);  
			$("#disp").empty();
			
			$(datas).find("sangpum").each(function() {
				let sangpum = $(this);
				let str = "<div>";
				str += sangpum.find('code').text() + " ";  // code의 1번이 해당
				str += sangpum.find('sangirum').text() + " ";
				str += sangpum.find('su').text() + " ";
				str += sangpum.find('danga').text();
				str += "</div>";
				$('#disp').append(str);
			});
		}).fail(function() {
			$('#disp').text('test3 처리 오류');
		});
	});
	
	// json 읽기2
	$('#test4').on("click", function() {
		//alert('4');
		$("#disp").empty();
		
		// $.ajax() : 
		$.ajax({  
			type:"get",  // post 요청 방식
			url:'jq4json.jsp',
			//data:{'code':3, 'sang':'book'},  // jq4json.jsp?code=3&sang=book
			dataType:"json", // 반환 데이터 타입 -> json 타입을 받는다.
			success:function(datas) {
				//alert(datas);
				let str = "";
				let count = 0;
				str += "<table border='1'>";
				str += "<tr><th>코드</th><th>품명</th><th>수량</th><th>단가</th></tr>";
				
				$.each(datas.sangpum, function(idx, data){// datas로부터 idx와 data를 하나씩 받아온다
					str += "<tr>";
					str += "<td>" + data["code"] + "</td>";
					str += "<td>" + data["sang"] + "</td>";
					str += "<td>" + data["su"] + "</td>";
					str += "<td>" + data.dan + "</td>";
					str += "</tr>";
					count++;
				});
				str += "</table>";
				$("#disp").append(str).append(`건수 : ${count}`);
			},error:function() {
				$('#disp').text('test4 처리 오류');
			}
		});
	});
	
	// XML 읽기2
	$('#test5').on("click", function() {
		$("#disp").empty();
		
		// $.ajax() : 
		$.ajax({  
			type:"post",  // post 요청 방식
			url:'jq4xml.jsp',
			dataType:"xml", // 반환 데이터 타입 -> json 타입을 받는다.
			success:function(datas) {
				let count = 0;
				let str = "<table border='1'>";
				str += "<tr><th>코드</th><th>품명</th><th>수량</th><th>단가</th></tr>";
				
				$(datas).find('sangpum').each(function() { // 
					str += "<tr>";
					str += "<td>" + $(this).find("code").text() + "</td>";
					str += "<td>" + $(this).find("sangirum").text() + "</td>";
					str += "<td>" + $(this).find("su").text() + "</td>";
					str += "<td>" + $(this).find("danga").text() + "</td>";
					str += "</tr>";
					count++;
				});
				str += "</table>";
				$("#disp").append(str)
				$("#disp").append(`건수(xml) : ${count}`);
			},error:function() {
				$('#disp').text('test4 처리 오류');
			}
		});
	});
});