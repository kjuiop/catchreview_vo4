window.name = "regist";



$( function() {
	$(".datepicker").datepicker({
		changeMonth: true,
		changeYear: true,
		dateFormat: 'yymmdd'
	});
});

//우편번호 검색
function sample6_execDaumPostcode() {
	new daum.Postcode({
		oncomplete : function(data) {
			alert('들어옴.');
			// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

			// 각 주소의 노출 규칙에 따라 주소를 조합한다.
			// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
			var addr = ''; // 주소 변수

			//사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
			if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
				addr = data.roadAddress;
			} else { // 사용자가 지번 주소를 선택했을 경우(J)
				addr = data.jibunAddress;
			}

			document.getElementById("txt_qna1").value = addr;
			document.getElementById("txt_qna2").focus();
		}
	}).open();
}

//가게사진 등록 페이지 이동 기능



window.onload=function(){
	$('#btn_photoInput').on('click',function() {
		myWindow = window.open("storePictureRegist.jsp","tinyWindow", "width=1250, height=848");
	});
}



