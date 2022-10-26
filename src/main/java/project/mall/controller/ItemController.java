package project.mall.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import project.mall.domain.Item;
import project.mall.domain.UploadFile;
import project.mall.domain.dto.CreateItemForm;
import project.mall.file.FileStore;
import project.mall.service.ItemService;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final FileStore fileStore;

    @GetMapping("items/create")
    public String createItemForm(@ModelAttribute("createItemForm")CreateItemForm form) {
        return "items/createItem";
    }

    @PostMapping("items/create")
    public String createItem(@ModelAttribute CreateItemForm form, BindingResult result) throws IOException {

        if(result.hasErrors()) return "items/createItem";

        UploadFile attachImageFile = fileStore.storeItemImageFile(form.getImageFile());

        itemService.save(Item.create(form.getName(), attachImageFile, form.getCategory(), form.getPrice(), form.getStockQuantity()));

        return "redirect:/";
    }
}
