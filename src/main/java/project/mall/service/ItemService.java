package project.mall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.mall.domain.Item;
import project.mall.repository.ItemRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Long save(Item item) {
        itemRepository.save(item);
        return item.getId();
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }
}
