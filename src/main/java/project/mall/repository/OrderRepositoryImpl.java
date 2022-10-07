package project.mall.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import project.mall.domain.Order;
import project.mall.domain.OrderSearch;
import project.mall.domain.QMember;
import project.mall.domain.QOrder;

import javax.persistence.EntityManager;
import java.util.List;


public class OrderRepositoryImpl implements OrderRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public OrderRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    // 회원 아이디, 주문 상태
    public List<Order> search(OrderSearch orderSearch) {
        QOrder o = new QOrder("o");
        QMember m = new QMember("m");

        return  queryFactory
                .selectFrom(o)
                .join(o.member, m)
                .where(m.userId.eq(orderSearch.getUserId()),
                        o.status.eq(orderSearch.getOrderStatus()))
                .fetch();
    }
}
