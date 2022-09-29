package project.mall.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.mall.domain.autditing.BaseTimeEntity;
import project.mall.domain.enumtype.Category;
import project.mall.exception.NotEnoughStockException;

import javax.persistence.*;

@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Category category; // ENUM [CLOTH(옷), SHOES(신발)]

    private int price;

    private int stockQuantity;

    //==생성 메서드==//
    private Item(String name, Category category, int price, int stockQuantity) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public static Item create(String name, Category category, int price, int stockQuantity) {
        return new Item(name, category, price, stockQuantity);
    }

    //==비지니스 로직==//
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("재고가 부족합니다.");
        }
        this.stockQuantity = restStock;
    }

}
