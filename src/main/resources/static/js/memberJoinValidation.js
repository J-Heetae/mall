window.onload = function() {

  var userId_check_flag = false; // 아이디 중복 점검 플래그 변수
  var pwd_check_flag = false; // 패쓰워드 점검 플래그 변수
  var pwd_ch_check_flag = false; // 패쓰워드확인 점검 플래그 변수
  var email_check_flag = false; // 이메일 점검 플래그 변수 
  var phone_check_flag = false; // 연락처 점검 플래그 변수

  var userId = document.querySelector('#userId');
  var pwd = document.querySelector('#pwd');
  var pwd_ch = document.querySelector('#pwd_ch');
  var email = document.querySelector('#email');
  var phone = document.querySelector('#phone');

//  var timeout;
//  var delay_ajax = 1000;
//  var delay =500;

  if(userId.value != '') idCheck();
  if(pwd.value != '') pwdCheck();
  if(pwd_ch.value != '') pwd_chCheck();
  if(email.value != '') emailCheck();
  if(phone.value != '') phoneCheck();

  /*$('#userId').on('keyup' ,function(e){

    if(timeout) clearTimeout(timeout);

    timeout = setTimeout(function() {
      idCheck();
    }, delay_ajax);
  });


  $('#pwd').on('keyup' ,function(e){

    if(timeout) clearTimeout(timeout);

    timeout = setTimeout(function() {
      pwdCheck();
    }, delay);
  });

  $('#pwd_ch').on('keyup' ,function(e){

    if(timeout) clearTimeout(timeout);

    timeout = setTimeout(function() {
      pwd_chCheck();
    }, delay);
  });

  $('#email').on('keyup' ,function(e){

    if(timeout) clearTimeout(timeout);

    timeout = setTimeout(function() {
      emailCheck();
    }, delay_ajax);
  });

  $('#phone').on('keyup' ,function(e){

    if(timeout) clearTimeout(timeout);

    timeout = setTimeout(function() {
      phoneCheck();
    }, delay_ajax);
  });*/

  $('#userId').on('keyup focusout' ,function(e){
        idCheck();
    });


    $('#pwd').on('keyup focusout' ,function(e){
        pwdCheck();
    });

    $('#pwd_ch').on('keyup focusout' ,function(e){
        pwd_chCheck();
    });

    $('#email').on('keyup focusout' ,function(e){
        emailCheck();
    });

    $('#phone').on('keyup focusout' ,function(e){
        phoneCheck();
    });


///////////////////////////////////////////////////////////////////////////////////////////

  //아이디 점검
  function idCheck() {

    console.log('idCheck');
    
    var userId = document.querySelector('#userId');
    var errorMessage = document.querySelector('#join_userId_errorMessage');

    // 폼 점검(form validation)
    var valid = new RegExp(/^[a-z0-9-_]{5,20}$/).test(userId.value);

      // ajax 전송 여부
    if (valid) {
      // ajax
      $.ajax({
        url : "/members/id-duplicate-check",
        type : "POST",
        dataType: "json",
        data : {
          userId : userId.value
        },
        success : function(data) {
          if (data) {

            errorMessage.innerText = '사용 가능한 아이디입니다';
            errorMessage.removeAttribute('class', 'errorMessage');
            errorMessage.setAttribute('class', 'successMessage');
            userId_check_flag = true;
            flagCheck();

          } else {

            errorMessage.innerText = '이미 존재하는 아이디입니다.'
            errorMessage.removeAttribute('class', 'successMessage');
            errorMessage.setAttribute('class', 'errorMessage');
            userId_check_flag = false;

          }
        },
        error : function(xhr, status, error) {
          console.error("xhr : " + xhr);
          console.error("status : " + status);
          console.error("error : " + error);
        }
      }); // ajax
    } else {

    errorMessage.innerText = '5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.'
    errorMessage.removeAttribute('class', 'successMessage');
    errorMessage.setAttribute('class', 'errorMessage');
    userId_check_flag = false;
    }

  };

///////////////////////////////////////////////////////////////////////////////////////////

//비밀번호 점검
function pwdCheck() {

  console.log('pwdCheck');

  var pwd = document.querySelector('#pwd');
  var errorMessage = document.querySelector('#join_pwd_errorMessage');

  // 폼 점검(form validation)
  var valid = new RegExp(/(?=.*\d{1,14})(?=.*[~`!@#$%\^&*()-+=]{1,14})(?=.*[a-zA-Z]{1,14}).{8,16}$/).test(pwd.value);

  if(valid) {
    errorMessage.innerText = '사용 가능한 비밀번호입니다';
    errorMessage.removeAttribute('class', 'errorMessage');
    errorMessage.setAttribute('class', 'successMessage');
    pwd_check_flag = true;
    flagCheck();

  }else {
    errorMessage.innerText = '8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.'
    errorMessage.removeAttribute('class', 'successMessage');
    errorMessage.setAttribute('class', 'errorMessage');
    pwd_check_flag = false;
  }
};

//비밀번호 확인
function pwd_chCheck() {

  console.log('pwd_chCheck');

  var pwd = document.querySelector('#pwd');
  var pwd_ch = document.querySelector('#pwd_ch');
  var valid = pwd.value == pwd_ch.value;
  var errorMessage = document.querySelector('#join_pwd_ch_errorMessage');

  if(valid) {
    errorMessage.innerText = '비밀번호가 일치합니다.';
    errorMessage.removeAttribute('class', 'errorMessage');
    errorMessage.setAttribute('class', 'successMessage');
    pwd_ch_check_flag = true;
    flagCheck();

  }else {
    errorMessage.innerText = '비밀번호가 일치하지 않습니다.'
    errorMessage.removeAttribute('class', 'successMessage');
    errorMessage.setAttribute('class', 'errorMessage');
    pwd_ch_check_flag = false;
  }
};

///////////////////////////////////////////////////////////////////////////////////////////

  //이메일 점검
  function emailCheck() {

    console.log('email_Check');
    
    var email = document.querySelector('#email');
    var errorMessage = document.querySelector('#join_email_errorMessage');

    // 폼 점검(form validation)
    var valid = new RegExp(/^[a-zA-Z0-9_+.-]+@([a-zA-Z0-9-]+\.)+[a-zA-Z0-9]{2,4}$/).test(email.value);

    // ajax 전송 여부
    if (valid) {
      // ajax
      $.ajax({
        url : "/members/email-duplicate-check",
        type : "POST",
        dataType: "json",
        data : {
          email : email.value
        },
        success : function(data) {
          if (data) {

            errorMessage.innerText = '사용 가능한 이메일입니다';
            errorMessage.removeAttribute('class', 'errorMessage');
            errorMessage.setAttribute('class', 'successMessage');
            email_check_flag = true;
            flagCheck();

          } else {

            errorMessage.innerText = '이미 사용중인 이메일입니다.'
            errorMessage.removeAttribute('class', 'successMessage');
            errorMessage.setAttribute('class', 'errorMessage');
            email_check_flag = false;

          }
        },
        error : function(xhr, status, error) {
          console.error("xhr : " + xhr);
          console.error("status : " + status);
          console.error("error : " + error);
        }
      }); // ajax
    } else {

    errorMessage.innerText = '이메일 주소가 올바르지 않습니다.'
    errorMessage.removeAttribute('class', 'successMessage');
    errorMessage.setAttribute('class', 'errorMessage');
    email_check_flag = false;
    }
  };

///////////////////////////////////////////////////////////////////////////////////////////

  //전화번호 점검
  function phoneCheck() {
    
    console.log('phoneCheck');

    var phone = document.querySelector('#phone');
    var errorMessage = document.querySelector('#join_phone_errorMessage');

    var valid = new RegExp(/^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/).test(phone.value);

    if(valid) {
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

              errorMessage.innerText = '사용 가능한 번호입니다.';
              errorMessage.removeAttribute('class', 'errorMessage');
              errorMessage.setAttribute('class', 'successMessage');
              phone_check_flag = true;
              flagCheck();

            } else {

              errorMessage.innerText = '이미 사용중인 번호입니다.'
              errorMessage.removeAttribute('class', 'successMessage');
              errorMessage.setAttribute('class', 'errorMessage');
              phone_check_flag = false;

            }
          },
          error : function(xhr, status, error) {
            console.error("xhr : " + xhr);
            console.error("status : " + status);
            console.error("error : " + error);
          }
        }); // ajax
    }
  };

///////////////////////////////////////////////////////////////////////////////////////////

  //모든 폼 제대로 입력됐나 확인
  function flagCheck() {

    console.log('flagCheck');

    if(userId_check_flag == true &&
      pwd_check_flag == true &&
      pwd_ch_check_flag == true &&
      email_check_flag == true &&
      phone_check_flag == true) {

        $('#join_submit').css({
          'pointer-events':'auto',
          'background-color':'black'
        });
      }
  }

}