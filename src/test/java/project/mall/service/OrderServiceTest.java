package project.mall.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.mall.domain.*;
import project.mall.domain.enumtype.Category;
import project.mall.domain.enumtype.OrderStatus;
import project.mall.exception.NotEnoughStockException;
import project.mall.repository.OrderRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //Given
        Member member = Member.create("member", "123", "123@naver.com", "01012345678");
        em.persist(member);

        UploadFile file = UploadFile.create("hi","hello");
        Item item = Item.create("item", file ,Category.CLOTH, 10000, 100);
        em.persist(item);

        Address address = Address.create(member, "0000", "서울시 양천구 신월로",
                "00동 0000호", member.getPhone(), "나", "우리집", "없음");
        em.persist(address);

        int orderCount = 3;

        //When
        Long orderId = orderService.order(member.getId(), address.getId(), item.getId(), orderCount);

        //Then
        Order order = orderRepository.findById(orderId).get();

        assertEquals(OrderStatus.ORDER, order.getStatus(), "상품 주문시 상태는 ORDER");
        assertEquals(1, order.getOrderItems().size(), "주문한 상품 종류 수가 정확해야 한다.");
        assertEquals(30000, order.getTotalPrice(), "주문 가격은 가격 * 수량이다.");
        assertEquals(97, item.getStockQuantity(), "주문 수량만큼 재고가 줄어야한다.");
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception {
        //Given
        Member member = Member.create("member", "123", "123@naver.com", "01012345678");
        em.persist(member);

        UploadFile file = UploadFile.create("hi","hello");
        Item item = Item.create("item", file, Category.CLOTH, 10000, 100);
        em.persist(item);

        Address address = Address.create(member, "0000", "서울시 양천구 신월로",
                "00동 0000호", member.getPhone(), "나", "우리집", "없음");
        em.persist(address);

        int orderCount = 101;

        //When
        NotEnoughStockException exception = assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), address.getId(), item.getId(), orderCount);
        });

        //Then
        assertEquals("재고가 부족합니다.",exception.getMessage());
    }

    @Test
    public void 주문취소() {
        //Given
        Member member = Member.create("member", "123", "123@naver.com", "01012345678");
        em.persist(member);

        UploadFile file = UploadFile.create("hi","hello");
        Item item = Item.create("item", file, Category.CLOTH, 10000, 100);
        em.persist(item);

        Address address = Address.create(member, "0000", "서울시 양천구 신월로",
                "00동 0000호", member.getPhone(), "나", "우리집", "없음");
        em.persist(address);

        int orderCount = 50;

        Long orderId = orderService.order(member.getId(), address.getId(), item.getId(), orderCount);

        //When
        orderService.cancel(orderId);

        //Then
        Order order = orderRepository.findById(orderId).get();

        assertEquals(OrderStatus.CANCEL, order.getStatus(), "주문 취소시 상태는 CANCEL 이다.");
        assertEquals(100, item.getStockQuantity(), "주문 취소시 상품은 재고가 취소 수량만큼 증가한다.");
    }
}