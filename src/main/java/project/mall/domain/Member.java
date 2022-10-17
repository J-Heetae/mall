package project.mall.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.mall.domain.autditing.BaseTimeEntity;
import project.mall.domain.enumtype.Authority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

//    @Enumerated(EnumType.STRING)
    private Authority authority;

    private String userId; //회원이 설정한 아이디
    private String pwd;

    private String email;

    private String phone;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Address address;

    //==생성 메서드==//
    private Member(Authority authority, String userId, String pwd, String email, String phone) {
        this.authority = authority;
        this.pwd = pwd;
        this.userId = userId;
        this.email = email;
        this.phone = phone;
    }

    public static Member create(String userId, String pwd, String email, String phone) {
        return new Member(Authority.MEMBER, userId, pwd, email, phone);
    }
}