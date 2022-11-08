window.onload = function() {

  var email_check_flag = false; // 이메일 점검 플래그 변수
  var phone_check_flag = false; // 연락처 점검 플래그 변수

  var email = document.querySelector('#email');
  var phone = document.querySelector('#phone');

  var emailErrorMessage = document.querySelector('#update_email_errorMessage');
  var phoneErrorMessage = document.querySelector('#update_phone_errorMessage');

  var originalEmail = email.value;
  var originalPhone = phone.value;

    $('#email').on('keyup focusout' ,function(e){
        validateEmail();
        emailFlagCheck();
    });

    $('#phone').on('keyup focusout' ,function(e){
        validatePhone();
        phoneFlagCheck();
    });

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

///////////////////////////////////////////////////////////////////////////////////////////

  // 이메일이 원래 이메일과 다른지 확인, validate
  function validateEmail() {
    var valid = new RegExp(/^[a-zA-Z0-9_+.-]+@([a-zA-Z0-9-]+\.)+[a-zA-Z0-9]{2,4}$/).test(email.value);

    if(email.value != originalEmail) {

      if(valid) {
        emailDuplicateCheck();

      } else {
        emailErrorMessage.innerText = '이메일 주소가 올바르지 않습니다.';
        emailErrorMessage.removeAttribute('class', 'successMessage');
        emailErrorMessage.setAttribute('class', 'errorMessage');
        changeEmailFlag(false);
      }

    } else {
        emailErrorMessage.innerText = '';
        changeEmailFlag(false);
    }

  }

  function changeEmailFlag(flag) {

    if(flag) {
      email_check_flag = true;
    } else {
      email_check_flag = false;
    }

    emailFlagCheck();
  }

  //이메일 점검
  function emailDuplicateCheck() {

    // ajax 전송 여부
    $.ajax({
      url : "/members/email-duplicate-check",
      type : "POST",
      dataType: "json",
      data : {
        email : email.value
      },
      success : function(data) {
        if (data) {

          emailErrorMessage.innerText = '사용 가능한 이메일입니다';
          emailErrorMessage.removeAttribute('class', 'errorMessage');
          emailErrorMessage.setAttribute('class', 'successMessage');
          changeEmailFlag(true);

        } else {

          emailErrorMessage.innerText = '이미 사용중인 이메일입니다.';
          emailErrorMessage.removeAttribute('class', 'successMessage');
          emailErrorMessage.setAttribute('class', 'errorMessage');
          changeEmailFlag(false);

        }
      },
      error : function(xhr, status, error) {
        console.error("xhr : " + xhr);
        console.error("status : " + status);
        console.error("error : " + error);
      }
    }); // ajax
  };

///////////////////////////////////////////////////////////////////////////////////////////

  /** 숫자 제외한 모든 문자 제거, 전화번호 길이가 11으로 일치해야 phoneDuplicateCheck() 호출*/
  function validatePhone() {

    phone.value = phone.value.replace(/[^0-9]/g, '');

    var valid = new RegExp(/^01([0|1|6|7|8|9])?([0-9]{3,4})?([0-9]{4})$/).test(phone.value);

    if(phone.value != originalPhone) {
      if(phone.value.toString().length == 11
        && valid
        ) {
          phoneDuplicateCheck();
        } else {
          removePhoneInnerText();
          changePhoneFlag(false);
        }
    } else {
      removePhoneInnerText();
      changePhoneFlag(false);
    }
  }

  function removePhoneInnerText() {
    phoneErrorMessage.innerText = ''
    changePhoneFlag(false);
  }

  function changePhoneFlag(flag) {
      if(flag) {
        phone_check_flag = true;
      } else {
        phone_check_flag = false;
      }
      phoneFlagCheck();
    }

  /** 전화번호 중복 점검 */
  function phoneDuplicateCheck() {

    // ajax
    $.ajax({
      url : "/members/phone-duplicate-check",
      type : "POST",
      dataType: "json",
      data : {
        phone : phone.value.replace(/[^0-9]/g, '')
      },
      success : function(data) {
        if (data) {

          phoneErrorMessage.innerText = '사용 가능한 번호입니다.';
          phoneErrorMessage.removeAttribute('class', 'errorMessage');
          phoneErrorMessage.setAttribute('class', 'successMessage');
          changePhoneFlag(true);

        } else {

          phoneErrorMessage.innerText = '이미 사용중인 번호입니다.'
          phoneErrorMessage.removeAttribute('class', 'successMessage');
          phoneErrorMessage.setAttribute('class', 'errorMessage');
          changePhoneFlag(false);

        }
      },
      error : function(xhr, status, error) {
        console.error("xhr : " + xhr);
        console.error("status : " + status);
        console.error("error : " + error);
      }
    }); // ajax
  };

///////////////////////////////////////////////////////////////////////////////////////////

  //모든 폼 제대로 입력됐나 확인
  function emailFlagCheck() {
    if(email_check_flag == true) {
        $('#email_update_button').css({
          'pointer-events':'auto',
          'background-color':'black'
        });
    } else {
        $('#email_update_button').css({
         'pointer-events':'none',
         'background-color':'gray'
        });
    }
  }

  function phoneFlagCheck() {
     if(phone_check_flag == true) {
         $('#phone_update_button').css({
           'pointer-events':'auto',
           'background-color':'black'
         });
     } else {
        $('#phone_update_button').css({
           'pointer-events':'none',
           'background-color':'gray'
         });
     }
  }

}