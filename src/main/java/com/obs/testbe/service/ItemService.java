package com.obs.testbe.service;

import com.obs.testbe.helper.Constant;
import com.obs.testbe.helper.ResponseWrapperDTO;
import com.obs.testbe.model.Item;
import com.obs.testbe.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public Page<Item> getItemList(Pageable pageable){
        return itemRepository.findAll(pageable);
    }

    public Page<Item> getItemListByName(String name, Pageable pageable){
        return itemRepository.findByNameContainingIgnoreCase(name,pageable);
    }

    public Item getItemById(String id){
        return itemRepository.findById(id).orElseThrow();
    }

    public Object deleteItemById(String id){
        Item item = itemRepository.findById(id).orElseThrow();
        item.setIsDeleted(Constant.YES);
        itemRepository.save(item);
        return ResponseWrapperDTO.builder()
                .data(item)
                .status(Constant.Success)
                .messages("Item successfully deleted.").build();
    }

    public Object saveItem(Item item){
        Item itemSave = new Item();
        if(item.getId() > 0){
            itemSave = itemRepository.findById(String.valueOf(item.getId())).orElseThrow();
        }
        itemSave.setName(item.getName());
        itemSave.setPrice(item.getPrice());
        itemSave.setIsDeleted(Constant.NO);
        itemRepository.save(itemSave);
        return ResponseWrapperDTO.builder()
                .data(item)
                .status(Constant.Success)
                .messages("Item successfully saved.").build();
    }
}
