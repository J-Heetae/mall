package project.mall.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.mall.domain.autditing.BaseTimeEntity;
import project.mall.domain.enumtype.DeliveryStatus;

import javax.persistence.*;

@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // ENUM [READY(준비), COMP(배송)]

    //==생성 메서드==//
    private Delivery(Address address) {
        this.address = address;
        this.status = DeliveryStatus.READY;
    }

    public static Delivery create(Address address) {
        return new Delivery(address);
    }
}
