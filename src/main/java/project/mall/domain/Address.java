package project.mall.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.mall.domain.autditing.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "address_id")
    private Long id;

    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private List<Delivery> deliveries = new ArrayList<>();

    private String zipcode;

    private String address1; // 기본주소

    private String address2; // 상세주소

    private String phone;

    private String name; // 주문자

    private String addressName; // 배송지명

    private String request;


    //==생성 메서드==//
    private Address(Member member, String zipcode, String address1, String address2
            , String phone, String name, String addressName, String request) {
        this.member = member;
        this.zipcode = zipcode;
        this.address1 = address1;
        this.address2 = address2;
        this.phone = phone;
        this.name = name;
        this.addressName = addressName;
        this.request = request;
    }

    public static Address create(Member member, String zipcode, String address1, String address2
            , String phone, String name, String addressName, String request) {
        return new Address(member, zipcode, address1, address2, phone, name, addressName, request);
    }
}
