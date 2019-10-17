
  var left = Math.ceil((window.screen.width-750)/2);
  
function addReview(reviewNum){
	myWindow = window.open('../review/register?reviewNum='+reviewNum, '', 'width=720, height=500, left='+left);
};

function reviewModify(reviewNum){
	myWindow = window.open('../review/addModify.jsp?reviewNum='+reviewNum, "tinyWindow", "width=700, height=398, left="+left);
};

function reviewDelete(reviewNum){
	if(confirm("리뷰를 삭제하시겠습니까?")){
		location.href="../review/funcReviewDelete.jsp?reviewNum="+reviewNum;
	}
};



window.onload=function(){
	
	$('#btn_write').on('click', function(){
		myWindow = window.open("../review/register?storeNum="+$('#storeNum').attr('value')+"&storeName="+$('#storeName').attr("value"), "tinyWindow", "width=700, height=398, left="+left)
	});
	

	$('#input_box').click(function() {
		$('#txt_reply').removeClass('reply_event');
	});
	
	$('#photo .reprePicture').on('click', function(){
		var storeNum = $('#storeNum').val();
		myWindow = window.open("storePicture.jsp?storeNum=" + storeNum, "tinyWindow", "width=1252, height=933");
	});
	
	var storeName = $('#storeName').val();
	var address1 = $('#storeAddress1').val();
	var address2 = $('#storeAddress2').val();
	var address = address1 + " " + address2;
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = {
        center: new daum.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };  
// 지도를 생성합니다    
var map = new daum.maps.Map(mapContainer, mapOption); 

// 주소-좌표 변환 객체를 생성합니다
var geocoder = new daum.maps.services.Geocoder();
// 주소로 좌표를 검색합니다
geocoder.addressSearch(address, function(result, status) {
    // 정상적으로 검색이 완료됐으면 
     if (status === daum.maps.services.Status.OK) {

        var coords = new daum.maps.LatLng(result[0].y, result[0].x);

        // 결과값으로 받은 위치를 마커로 표시합니다
        var marker = new daum.maps.Marker({
            map: map,
            position: coords
        });

        // 인포윈도우로 장소에 대한 설명을 표시합니다
        var infowindow = new daum.maps.InfoWindow({
            content: '<div style="width:150px;text-align:center;padding:6px 0;">' + storeName + '</div>'
        });
        infowindow.open(map, marker);

        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
        map.setCenter(coords);
    } 
}); 


}