package com.obs.testbe.controller;

import com.obs.testbe.model.Item;
import com.obs.testbe.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "Java BE Test");
        return "index";
    }

    @GetMapping("/item/list")
    public String getItemList(Model model,
                              @RequestParam(required = false) String keyword,
                              @PageableDefault(size = 5, page = 0) Pageable pageable)
    {
        try {
            Page<Item> paging;
            if (keyword == null) {
                paging = itemService.getItemList(pageable);
            } else {
                paging = itemService.getItemListByName(keyword, pageable);
                model.addAttribute("keyword", keyword);
            }
            model.addAttribute("paging", paging);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }
        return "item_list";

    }

    @GetMapping("/item/del")
    public String deleteItem(@RequestParam(required = true) String id,
                             Model model,
                             RedirectAttributes redirectAttributes)
    {
        try {
            itemService.deleteItemById(id);

            redirectAttributes.addFlashAttribute("message", "The Item with id=" + id + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/item/list";
    }

    @GetMapping("/item/add")
    public String addItem(Model model)
    {
        try {
            Item item = new Item();
            model.addAttribute("pageTitle", "Item - Add");
            model.addAttribute("item", item);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }
        return "item_add_edit";
    }

    @GetMapping("/item/edit")
    public String editItem(Model model,
                          @RequestParam(required = true) String id)
    {
        try {
            Item item = new Item();
            item=itemService.getItemById(id);
            model.addAttribute("pageTitle", "Item - Edit");
            model.addAttribute("item", item);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }
        return "item_add_edit";
    }

    @PostMapping("/item/save")
    public String saveItem(Item item, RedirectAttributes redirectAttributes) {
        try {
            itemService.saveItem(item);

            redirectAttributes.addFlashAttribute("message", "The Item " + item.getName() + " has been saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }

        return "redirect:/item/list";
    }
}
