let xhr, ele, popup, disp;

function goFunc(element) {
	//console.log(element);  // element는 <tr></tr>과 같이 태그 전체를 가리킴
	ele = element;
	//console.log(ele.id);  // id가 갖고 있는 값을 출력
	disp = document.getElementById("disp");
	popup = document.getElementById("popup");
	
	xhr = new XMLHttpRequest();
	xhr.open("post", "jq7data.jsp", true);
	xhr.onreadystatechange = function() {
		if(xhr.readyState === 4) {
			if(xhr.status === 200) {
				let datas = xhr.responseXML;
				//console.log(datas);
				//console.log(datas.getElementsByTagName("make")[0].firstChild.nodeValue);
				
				setOffsets();  // 툴킷박스 출현위치 지정하는 함수
				
				let jdata = datas.getElementsByTagName("make")[0].firstChild.nodeValue;
				let row = createRowFunc(jdata);
				disp.appendChild(row);
			} 
		}
	}
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");  // post 방식의 경우 필수!
	xhr.send("buser=" + ele.id);  // 부서명이 서버에 전달
}

function createRowFunc(jdata) {
	let r = document.createElement("tr");  // tr 태그 생성
	let c = document.createElement("td");
	let text = document.createTextNode(jdata);
	c.appendChild(text);
	r.appendChild(c);
	return r;
}

function clsFunc() {
	//console.log("b");
	for(let i = disp.childNodes.length - 1; i >= 0; i--) {
		disp.removeChild(disp.childNodes[i]);
	}
	
	popup.style.border = "none";
}


function setOffsets(){
  // offsetWidth 속성은 요소의 가로 값(보더/패딩 포함)을 가져온다.
  // 데이터 값을 수정할 수 없고, 값만 가져올 수 있는 읽기 전용 속성
  let end = ele.offsetWidth;
  let top = calcOffSetFunc(ele, "offsetTop");
  
  // offsetTop 속성은 요소의 Y축 위치 값(문서 기준)을 가져온다.
  // 읽기 전용 속성
  popup.style.left = end + 10 + "px";
  popup.style.top = top + "px";
  popup.style.border = "red 1px double";
  popup.style.color = "purple";
}

 

function calcOffSetFunc(field, attr){
  let offset = 0;
  while(field){
    offset += field[attr]; // 부모 노드의 offsetTop 속성을 누적
    field = field.offsetParent; // 부모 좌표를 구할 수 있다.
  }
  return offset;
}