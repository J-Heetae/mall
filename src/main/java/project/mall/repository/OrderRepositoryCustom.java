package project.mall.repository;

import project.mall.domain.Order;
import project.mall.domain.OrderSearch;

import java.util.List;

public interface OrderRepositoryCustom {

    List<Order> search(OrderSearch orderSearch);
}
