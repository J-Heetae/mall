$(document).ready(function() {
    $("#imageFile").on('change', function() {
        var fileName = $("#imageFile").val();

//        뒤에서부터 처음 \가 나오는 이후로 문자열 자르기(파일이름 추출)
        var modifyFileName = fileName.substr(fileName.lastIndexOf("\\")+1);

        $(".upload-name").val(modifyFileName);
    });
})
