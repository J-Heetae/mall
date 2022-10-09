package project.mall.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.mall.repository.MemberRepository;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberRestController {

    /**
     * 아이디 중복 점검
     * @return ture = 사용 가능(중복 없음)
     *         false = 사용 불가(중복 있음)
     */
    private final MemberRepository memberRepository;

    @PostMapping("/members/id-duplicate-check")
    public Boolean userIdDuplicateCheck(@RequestParam("userId") String userId, HttpServletResponse response) {
       
        log.info("아이디 중복 점검 ID = " + userId);

        try {
            if(memberRepository.findByUserId(userId).isEmpty()) return true;
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    /**
     * 이메일 중복 점검
     * @return ture = 사용 가능(중복 없음)
     *         false = 사용 불가(중복 있음)
     */
    @PostMapping("/members/email-duplicate-check")
    public Boolean emailDuplicateCheck(@RequestParam("email") String email, HttpServletResponse response) {
        
        log.info("이메일 중복 점검 email = " + email);

        try {
            if(memberRepository.findByEmail(email).isEmpty()) return true;
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    /**
     * 전화번호 중복 점검
     * @return ture = 사용 가능(중복 없음)
     *         false = 사용 불가(중복 있음)
     */
    @PostMapping("/members/phone-duplicate-check")
    public Boolean phoneDuplicateCheck(@RequestParam("phone") String phone, HttpServletResponse response) {
        
        log.info("전화번호 중복 점검 phone = " + phone);

        try {
            if(memberRepository.findByPhone(phone).isEmpty()) return true;
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}