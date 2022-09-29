package project.mall.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.mall.domain.autditing.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int count;

    //==생성자 메서드==//
    private OrderItem(Item item, int orderPrice, int count) {
        this.item = item;
        this.orderPrice = orderPrice;
        this.count = count;
    }

    public static OrderItem create(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem(item, orderPrice, count);

        item.removeStock(count);

        return orderItem;
    }

    //==비지니스 로직==//
    /** 주문 취소*/
    public void cancel() {
        getItem().addStock(count);
    }

    //==조회 로직==//
    /** 주문상품 전체 가격 조회 */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
