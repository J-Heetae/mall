package project.mall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.mall.domain.Item;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {
}
