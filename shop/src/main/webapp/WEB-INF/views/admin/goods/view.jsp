<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<style>
	body { font-family:'맑은 고딕', verdana; padding:0; margin:0; }
	ul { padding:0; margin:0; list-style:none;  }
 
	div#root { width:90%; margin:0 auto; }
	
	header#header { font-size:60px; padding:20px 0; }
	header#header h1 a { color:#000; font-weight:bold; }
	
	nav#nav { padding:10px; text-align:right; }
	nav#nav ul li { display:inline-block; margin-left:10px; }
 
 		section#container { padding:20px 0; border-top:2px solid #eee; border-bottom:2px solid #eee; }
	section#container::after { content:""; display:block; clear:both; }
	aside { float:left; width:200px; }
	div#container_box { float:right; width:calc(100% - 200px - 20px); }
	
	aside ul li { text-align:center; margin-bottom:10px; }
	aside ul li a { display:block; width:100%; padding:10px 0;}
	aside ul li a:hover { background:#eee; }
	
	footer#footer { background:#f9f9f9; padding:20px; }
	footer#footer ul li { display:inline-block; margin-right:10px; } 
</style>


<style>
.inputArea { margin:10px 0; }
select { width:100px; }
label { display:inline-block; width:70px; padding:5px; }
label[for='gdsDes'] { display:block; }
input { width:150px; }
textarea#gdsDes { width:400px; height:180px; }
.oriImg { width:500px; height:auto; }
.thumbImg {}
.gdsDes img { max-width:600px; height:auto; }
</style>

</head>
<body>
<div id="root">
	<header id="header">
		<div id="header_box">
			<%@ include file="../include/header.jsp" %>
		</div>
	</header>
	
	<nav id="nav">
		<div id="nav_box">
			<%@ include file="../include/nav.jsp" %>
		</div>
	</nav>
	<section id="container">
		<aside>
			<%@include file = "../include/aside.jsp" %>
		</aside>
		<div id="container_box">
			<h2>상품 등록</h2>

				<form role="form" method="post" autocomplete="off">
				<input type="hidden" name="n" value="${goods.gdsNum}" />
					<div class="inputArea">
						<label>1차 분류</label> 
						<span class="category1"></span> 
						<label>2차분류</label> 
						<span class="category2">${goods.cateCode}</span>
					</div>

					<div class="inputArea">
						<label for="gdsName">상품명</label> 
						<span>${goods.gdsName}</span>
					</div>

					<div class="inputArea">
						<label for="gdsPrice">상품가격</label> 
						<span>
							<fmt:formatNumber value="${goods.gdsPrice}" pattern="###,###,###" />
						</span>
					</div>

					<div class="inputArea">
						<label for="gdsStock">상품수량</label>
						<span>${goods.gdsStock}</span>
					</div>

					<div class="inputArea">
						<label for="gdsDes">상품소개</label>
						<span>${goods.gdsDes}</span>
					</div>
					<div class="inputArea">
						<label for="gdsImg">이미지</label>
						<p>원본 이미지</p>
						<img src="${goods.gdsImg}" class="oriImg" />

						<p>썸네일</p>
						<img src="${goods.gdsThumbImg}" class="thumbImg" />
					</div>

					<div class="inputArea">
						<button type="button" id="modify_Btn" class="btn btn-warning">수정</button>
						<button type="button" id="delete_Btn" class="btn btn-danger">삭제</button>

						<script>
							var formObj = $("form[role='form']");

							$("#modify_Btn").click(function() {
								formObj.attr("action", "/admin/goods/modify");
								formObj.attr("method", "get")
								formObj.submit();
							});

							
							$("#delete_Btn").click(function() {
								var con = confirm("정말로 삭제하시겠습니까?");
								if (con) {
									formObj.attr("action","/admin/goods/delete");
									formObj.submit();
								}
							});
						</script>
					</div>

				</form>

			</div>
	</section>
	<footer id="footer">
		<div id="footer_box">
			<%@ include file="../include/footer.jsp" %>
		</div>
	</footer>
</div>

<script>
// 컨트롤러에서 데이터 받기
var jsonData = JSON.parse('${category}');
// 필요한 배열과 오브젝트 변수 생성
var cate1Arr = new Array();
var cate1Obj = new Object();
// 1차 분류 셀렉트 박스에 삽입할 데이터 준비
for(var i = 0; i < jsonData.length; i++) {
	
	if(jsonData[i].level == "1") {  // 레벨이 1인 데이터가 있다면 
		cate1Obj = new Object();  // 초기화
		
		// cate1Obj에 cateCode와 cateName를 저장
		cate1Obj.cateCode = jsonData[i].cateCode; 
		cate1Obj.cateName = jsonData[i].cateName;
		
		// cate1Obj에 저장된 값을 cate1Arr 배열에 저장
		cate1Arr.push(cate1Obj);
	}
}
// 1차 분류 셀렉트 박스에 데이터 삽입
var cate1Select = $("select.category1")
for(var i = 0; i < cate1Arr.length; i++) {
	// cate1Arr에 저장된 값을 cate1Select에 추가
	cate1Select.append("<option value='" + cate1Arr[i].cateCode + "'>" + cate1Arr[i].cateName + "</option>");	
}
// 클래스가 category1인 select변수의 값이 바뀌었을 때 실행
$(document).on("change", "select.category1", function(){
	
	// 필요한 배열과 오브젝트 변수를 생성
	var cate2Arr = new Array();
	var cate2Obj = new Object();
	
	// 2차 분류 셀렉트 박스에 삽입할 데이터 준비
	for(var i = 0; i < jsonData.length; i++) {
		
		if(jsonData[i].level == "2") {  // 레빌이 2인 데이터가 있다면
			cate2Obj = new Object();  // 초기화
			
			// cate2Obj에 cateCode, cateName, cateCodeRef를 저장
			cate2Obj.cateCode = jsonData[i].cateCode;
			cate2Obj.cateName = jsonData[i].cateName;
			cate2Obj.cateCodeRef = jsonData[i].cateCodeRef;
			
			// cate2Obj에 저장된 값을 cate1Arr 배열에 저장
			cate2Arr.push(cate2Obj);
		} 
	}
	
	var cate2Select = $("select.category2");
	
	/*
	for(var i = 0; i < cate2Arr.length; i++) {
			cate2Select.append("<option value='" + cate2Arr[i].cateCode + "'>"
								+ cate2Arr[i].cateName + "</option>");
	}
	*/
	
	// cate2Select의 값을 제거함(초기화)
	cate2Select.children().remove();
 
	// cate1Select에서 선택한 값을 기준으로 cate2Select의 값을 조정
	$("option:selected", this).each(function(){
		
		var selectVal = $(this).val();  // 현재 선택한 cate1Select의 값을 저장
	
		cate2Select.append("<option value='" + selectVal + "'>전체</option>");  //cate2Select의 '전체'에 현재 선택한 cate1Select와 같은 값 부여
		
		// cate2Arr의 데이터를 cate2Select에 추가
		for(var i = 0; i < cate2Arr.length; i++) {
			
			// 현재 선택한 cate1Select의 값과 일치하는 cate2Arr의 데이터를 가져옴
			if(selectVal == cate2Arr[i].cateCodeRef) {
				cate2Select.append("<option value='" + cate2Arr[i].cateCode + "'>"+ cate2Arr[i].cateName + "</option>");
			}
		}		
	});
	
});
</script>
</body>
</html>