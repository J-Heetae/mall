package project.mall.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import project.mall.SessionConst;
import project.mall.argumentresolver.Login;
import project.mall.domain.Member;
import project.mall.domain.dto.JoinForm;
import project.mall.domain.dto.LoginForm;
import project.mall.repository.MemberRepository;
import project.mall.service.MemberService;

import javax.servlet.http.*;
import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("members/join")
    public String joinMemberForm(@ModelAttribute("joinForm") JoinForm form) {
        return "members/joinMember";
    }

    @PostMapping("members/join")
    public String join(@Valid @ModelAttribute JoinForm form, BindingResult result, HttpServletRequest request) {

        if(result.hasErrors()) return "members/joinMember";

        Long joinMemberId = memberService.join(Member.create(form.getUserId(), form.getPwd(), form.getEmail(), form.getPhone()));

        //로그인 성공 처리
        log.info("login 성공");

        //세션 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession(true); //default가 true
        //세션에 로그인 회원 ID 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, joinMemberId);

        return "redirect:/";
    }

    @GetMapping("members/login")
    public String loginMemberForm(@ModelAttribute("loginForm") LoginForm form) {
        return "members/loginMember";
    }

    @PostMapping("members/login")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult result, HttpServletRequest request) {
        log.info("MemberController.login");

        if(result.hasErrors()) return "members/loginMember";

        Member loginMember = memberService.login(form.getUserId(), form.getPwd());

        if(loginMember == null) {
            result.reject("loginFail", "일치하는 계정이 없습니다. 로그인 정보를 확인해주세요.");
            return "members/loginMember";
        } else {
            //로그인 성공 처리
            log.info("login 성공");

            Long loginMemberId = loginMember.getId();

            //세션 있으면 있는 세션 반환, 없으면 신규 세션 생성
            HttpSession session = request.getSession(true); //default가 true
            //세션에 로그인 회원 ID 보관
            session.setAttribute(SessionConst.LOGIN_MEMBER, loginMemberId);

            return "redirect:/";
        }
    }

    @GetMapping("members/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if(session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }

    private void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    @GetMapping("members/myPage")
    public String myPage(@Login Long loginMemberId, Model model) {
        if(loginMemberId != null) {
            Optional<Member> optionalMember = memberRepository.findById(loginMemberId);
            if(optionalMember.isPresent()) {
                model.addAttribute("member", optionalMember.get());
            }
        } else {
            return "members/loginMember";
        }

        return "members/myPage";
    }

    /**
     * 회원 연락처 변경
     *
     * @param loginMemberId 세션의 로그인 회원 아이디
     */
    @PostMapping("members/phoneChange")
    public String phoneChange(@Login Long loginMemberId, HttpServletRequest request, Model model) {
        String phone = request.getParameter("phone");
        memberService.changePhone(loginMemberId, phone);

        Optional<Member> optionalMember = memberRepository.findById(loginMemberId);

        if(optionalMember.isPresent()) {
            model.addAttribute("member", optionalMember.get());
        }

        return "members/myPage";
    }
}