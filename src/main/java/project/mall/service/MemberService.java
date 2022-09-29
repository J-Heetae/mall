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
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        log.info("MemberService.join 성공");
        return member.getId();
        
    }

    private void validateDuplicateMember(Member member) {
        Optional<Member> findMemberByUserId = memberRepository.findByUserId(member.getUserId());
        if(findMemberByUserId.isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원명입니다.");
        }

        Optional<Member> findMemberByEmail = memberRepository.findByEmail(member.getEmail());
        if(findMemberByEmail.isPresent()) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }

        Optional<Member> findMemberByPhone = memberRepository.findByPhone(member.getPhone());
        if(findMemberByPhone.isPresent()) {
            throw new IllegalStateException("이미 존재하는 전화번호입니다.");
        }
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
}
