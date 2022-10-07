package project.mall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.mall.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
}
