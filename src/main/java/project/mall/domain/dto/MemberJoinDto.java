package project.mall.domain.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberJoinDto {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "필수정보 입니다.")
    @Pattern(regexp = "^[a-z0-9-_]{5,20}$",
            message = "5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.")
    private String userId; //회원이 설정한 아이디

    @NotBlank(message = "필수정보 입니다.")
    @Pattern(regexp =  "\\s{1,20}|(?=.*[a-zA-Z])((?=.*\\d)(?=.*\\W)).{8,16}",
            message = "8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String pwd;

    @NotBlank(message = "필수정보 입니다.")
    @Pattern(regexp =  "^[a-zA-Z0-9_!#$%&'\\*+/=?{|}~^.-]+@[a-zA-Z0-9.-]+$",
            message = "이메일 주소가 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "필수정보 입니다.")
    @Pattern(regexp =  "^01(?:0|1|[6-9])\\\\d{7,8}$",
            message = "번호가 올바르지 않습니다.")
    private String phone;
}
