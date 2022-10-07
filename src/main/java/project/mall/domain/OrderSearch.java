package project.mall.domain;

import lombok.Data;
import project.mall.domain.enumtype.OrderStatus;

@Data
public class OrderSearch {

    private String userId;
    private OrderStatus orderStatus;
}
