package project.mall.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.mall.domain.Item;
import project.mall.domain.enumtype.Category;
import project.mall.repository.ItemRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    ItemService itemService;
    @Autowired
    ItemRepository itemRepository;

    @Test
    public void 상품등록() throws Exception {
        //Given
        Item item = new Item("item",10000);

        //When
        Long saveItem = itemService.save(item);

        //Then
        assertEquals(item, itemRepository.findById(saveItem).get());
    }

    @Test
    public void 전체_상품_조회() throws Exception {
        //Given
        Item item1 = new Item("item1",10000);
        Item item2 = new Item("item2",10000);

        //When
        itemService.save(item1);
        itemService.save(item2);

        //Then
        assertEquals(2, itemService.findItems().size());
    }
    
}