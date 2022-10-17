package project.mall.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import project.mall.domain.enumtype.Category;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
public class CreateItemForm {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Category category;
    private int price;
    private int stockQuantity;
    private MultipartFile imageFile;
}
