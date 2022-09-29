package project.mall.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.mall.domain.Member;
import project.mall.repository.MemberRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        //Given
        Member member = Member.create("taehee", "123", "213", "21321");

        //When
        Long saveId = memberService.join(member);

        //Then
        assertEquals(member.getId(), memberRepository.findById(saveId).get().getId());
    }

    @Test
    public void 중복_회원명_예외() throws Exception {
        //Given
        Member member1 = Member.create("member1", "123", "213", "21321");
        Member member2 = Member.create("member1", "123", "214", "21322");

        //When
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            memberService.join(member1);
            memberService.join(member2);
        });

        //Then
        assertEquals("이미 존재하는 회원명입니다.", exception.getMessage());
    }

    @Test
    public void 중복_이메일_예외() throws Exception {
        //Given
        Member member1 = Member.create("member1", "123", "213", "21321");
        Member member2 = Member.create("member2", "123", "213", "21322");

        //When
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            memberService.join(member1);
            memberService.join(member2);
        });

        //Then
        assertEquals("이미 존재하는 이메일입니다.", exception.getMessage());
    }
    @Test
    public void 중복_전화번호_예외() throws Exception {
        //Given
        Member member1 = Member.create("member1", "123", "213", "21321");
        Member member2 = Member.create("member2", "123", "214", "21321");

        //When
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            memberService.join(member1);
            memberService.join(member2);
        });

        //Then
        assertEquals("이미 존재하는 전화번호입니다.", exception.getMessage());
    }

    @Test
    public void 전체_회원_조회() throws  Exception {
        //Given
        Member member1 = Member.create("member1", "123", "213", "21321");
        Member member2 = Member.create("member2", "123", "214", "21324");
        memberService.join(member1);
        memberService.join(member2);

        //When
        List<Member> members = memberService.findMembers();

        //Then
        assertEquals(2, members.size());
    }

}