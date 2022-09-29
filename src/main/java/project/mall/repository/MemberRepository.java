package project.mall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.mall.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserId(String userId);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByPhone(String phone);
}
