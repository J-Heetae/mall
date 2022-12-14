package project.mall.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.mall.domain.Member;
import project.mall.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    @Transactional
    public Long join(Member member) {
        memberRepository.save(member);

        log.info("MemberService.join 성공");

        return member.getId();
    }

    /**
     * 로그인
     * @return null 로그인 실패
     */
    public Member login(String userId, String pwd) {

        return memberRepository.findByUserId(userId)
                .filter(m -> m.getPwd().equals(pwd))
                .orElse(null);
    }

    @Transactional
    public void changePhone(Long userId, String phone) {
        Member member = findById(userId);

        if(member != null) {
            member.phoneChange(phone);

            System.out.println("회원 연락처 수정 성공");
        } else {
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findById(Long memberId) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        return findMember.orElse(null);
    }
}
