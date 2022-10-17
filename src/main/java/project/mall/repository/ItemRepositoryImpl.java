package project.mall.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import project.mall.domain.Item;
import project.mall.domain.QItem;

import javax.persistence.EntityManager;
import java.util.List;

public class ItemRepositoryImpl implements ItemRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public ItemRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    // 시간순으로 가장 나중에 등록된 상품 3개 찾기
    @Override
    public List<Item> searchNewItem() {

        QItem i = new QItem("i");

        return queryFactory
                .selectFrom(i)
                .orderBy(i.createdDate.desc())
                .offset(0)
                .limit(3)
                .fetch();
    }
}
