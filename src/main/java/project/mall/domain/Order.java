package project.mall.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.mall.domain.autditing.BaseTimeEntity;
import project.mall.domain.enumtype.DeliveryStatus;
import project.mall.domain.enumtype.OrderStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Delivery delivery;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    //==연관 관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==생성 메서드==//
    private Order(Member member, Delivery delivery, OrderStatus status, OrderItem... orderItems) {
        this.member = member;
        this.delivery = delivery;
        for(OrderItem orderItem : orderItems) {
            this.orderItems.add(orderItem);
        }
        this.status = status;
    }

    public static Order create(Member member, Delivery delivery, OrderItem... orderItems) {
        return new Order(member, delivery, OrderStatus.ORDER, orderItems);
    }

    //==비지니스 로직==//
    /** 주문 취소 */
    public void cancel() {
        if(delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송이 시작되어 취소가 불가능합니다.");
        } else {
            this.setStatus(OrderStatus.CANCEL);
            for(OrderItem o : orderItems) {
                o.cancel();
            }
        }
    }

    //==조회 로직==//
    public int getTotalPrice() {
        int totalPrice = 0;

        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }

        return totalPrice;
    }
}
