$(document).ready(function() {
	$("#btnSearch").click(function() {
		$("#show").empty();
		$("#showCount").empty();
		
		let bname = $("#bname").val();
		
		$.ajax({
			type:"get",
			url:"jq6.jsp",
			data:{"bname":bname}, // search는 문자열 name은 변수
			dataType:"json",
			success:function(datas){
				let str = "<table border='1'>";
				str += "<tr><th>사번</th><th>직원명</th><th>직급</th><th>관리고객</th></tr>";
				let count = 0;
				$.each(datas, function(idx, entry) {
					str += "<tr>";
					str += "<td>" + entry["jikwon_no"] + "</td>";
					if(Number(entry["jikwon_gogek"]) === 0) {
						str += "<td>" + entry["jikwon_name"] + "</td>";
					}
					else str += "<td><a href='javascript:func(" + entry["jikwon_no"] + ")'>" + entry["jikwon_name"] + "</a></td>";
					str += "<td>" + entry["jikwon_jik"] + "</td>";
					str += "<td>" + entry["jikwon_gogek"] + "</td>";
					str += "</tr>";
					count++;
				});
				str += "</table>";
				$("#show").append(str);
				$("#showCount").append(count);
			},
			error:function() {
				$('#show').text("에러 : " + status);
			}
		});
	});
});

function func(para) {
	//alert(para);
	$("#gogek").empty();
	
	$.ajax({
			type:"get",
			url:"jq6gogek.jsp",
			data:{"arg":para}, // search는 문자열 name은 변수
			dataType:"json",
			success:function(datas){
				let str = "<table border='1'>";
				str += "<tr><th>고객명</th><th>고객전화</th></tr>";
				let count = 0;
				$.each(datas, function(idx, entry) {
					str += "<tr>";
					str += "<td>" + entry["gogek_name"] + "</td>";
					str += "<td>" + entry["gogek_tel"] + "</td>";
					str += "</tr>";
					count++;
				});
				str += "</table>";
				$("#show").append(str);
				
			},
			error:function() {
				$('#show').text("에러 : " + status);
			}
		});
}