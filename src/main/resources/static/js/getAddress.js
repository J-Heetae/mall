window.onload = function() {
    $('#search_address_button').on('click', function(e){
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.querySelector('#zipcode').value = data.zonecode;
                document.querySelector('#address1').value = data.roadAddress;

                // 커서를 상세주소 필드로 이동한다.
                document.querySelector('#address2').focus();
            }
        }).open();
    })
}