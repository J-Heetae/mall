package project.mall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.mall.domain.*;
import project.mall.repository.AddressRepository;
import project.mall.repository.ItemRepository;
import project.mall.repository.MemberRepository;
import project.mall.repository.OrderRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final ItemRepository itemRepository;

    /** 주문 */
    @Transactional
    public Long order(Long memberId, Long addressId, Long itemId, int count) {

        //엔티티 조회
        Member member = memberRepository.findById(memberId).get();
        Item item = itemRepository.findById(itemId).get();
        Address address = addressRepository.findById(addressId).get();


        //배송정보 생성
        Delivery delivery = Delivery.create(address);

        //주문상품 생성
        OrderItem orderItem = OrderItem.create(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.create(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);
        return order.getId();
    }

    /** 주문 취소 */
    @Transactional
    public void cancel(Long orderId) {

        //엔티티 조회
        Order order = orderRepository.findById(orderId).get();

        //주문 취소
        order.cancel();
    }

    /**
     * 주문 검색
     * 회원 아이디, 주문 상태
     * */
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.search(orderSearch);
    }
}
