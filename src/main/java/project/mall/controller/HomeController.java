package project.mall.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import project.mall.SessionConst;
import project.mall.argumentresolver.Login;
import project.mall.domain.Member;
import project.mall.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;

//    @GetMapping("/")
    public String homeLogin(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {
        log.info("homeLogin");

        //세션에 회원 데이터가 없으면 home
        if (loginMember == null) return "home";

        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);

        return "home";
    }
    @GetMapping("/")
    public String homeLoginV2(@Login Member loginMember, Model model) {
        log.info("homeLogin");

        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);

        return "home";
    }
}

