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
        Item item = Item.create("item", Category.CLOTH, 10000, 100);

        //When
        Long saveItem = itemService.save(item);

        //Then
        assertEquals(item, itemRepository.findById(saveItem).get());
    }

    @Test
    public void 전체_상품_조회() throws Exception {
        //Given
        Item item1 = Item.create("item1", Category.CLOTH, 10000, 100);
        Item item2 = Item.create("item2", Category.CLOTH, 10000, 100);

        //When
        itemService.save(item1);
        itemService.save(item2);

        //Then
        assertEquals(2, itemService.findItems().size());
    }
    
}