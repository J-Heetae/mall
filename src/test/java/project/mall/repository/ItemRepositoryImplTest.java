package project.mall.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.mall.domain.Item;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemRepositoryImplTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void 최신상품_3개_찾기() {
        Item item1 = new Item("item1", 1000);
        Item item2 = new Item("item2", 1000);
        Item item3 = new Item("item3", 1000);
        Item item4 = new Item("item4", 1000);

        em.persist(item1);
        em.persist(item2);
        em.persist(item3);
        em.persist(item4);

        List<Item> items = itemRepository.searchNewItem();

        assertThat(items.size()).isEqualTo(3);
        assertThat(items.get(0).getName()).isEqualTo("item4");
        assertThat(items.get(1).getName()).isEqualTo("item3");
        assertThat(items.get(2).getName()).isEqualTo("item2");
    }

}