package project.mall.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.mall.argumentresolver.Login;
import project.mall.domain.Item;
import project.mall.domain.Member;
import project.mall.repository.ItemRepository;
import project.mall.repository.MemberRepository;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    final MemberRepository memberRepository;
    final ItemRepository itemRepository;


    @GetMapping("/")
    public String homeLoginV2(@Login Long loginMemberId, Model model) {
        log.info("homeLogin");

        List<Item> items = itemRepository.searchNewItem();
        if(items.size() == 3) {
            model.addAttribute("items", items);
        }

        //세션이 유지되면 로그인으로 이동
        if(loginMemberId != null) {
            Member loginMember = memberRepository.findById(loginMemberId).get();
            model.addAttribute("member", loginMember);
        }

        return "home";
    }
}

