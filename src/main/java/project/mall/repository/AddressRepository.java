package project.mall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.mall.domain.Address;
import project.mall.domain.Item;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
