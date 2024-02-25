package com.obs.testbe.controller;

import com.obs.testbe.model.Item;
import com.obs.testbe.model.Order;
import com.obs.testbe.service.ItemService;
import com.obs.testbe.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ItemService itemService;

    @GetMapping("/order/list")
    public String getOrderList(Model model,
                              @RequestParam(required = false) String keyword,
                              @PageableDefault(size = 5, page = 0) Pageable pageable)
    {
        try {
            Page<Order> paging;
            if (keyword == null) {
                paging = orderService.getOrderList(pageable);
            } else {
                paging = orderService.getOrderListByOrderNo(keyword, pageable);
                model.addAttribute("keyword", keyword);
            }
            model.addAttribute("paging", paging);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }
        return "order_list";

    }

    @GetMapping("/order/del")
    public String deleteOrder(@RequestParam(required = true) String id,
                             Model model,
                             RedirectAttributes redirectAttributes)
    {
        try {
            orderService.deleteOrderById(id);

            redirectAttributes.addFlashAttribute("message", "The Order with id=" + id + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/order/list";
    }

    @GetMapping("/order/add")
    public String addOrder(Model model)
    {
        try {
            Order order = new Order();
            model.addAttribute("pageTitle", "Order - Add");
            model.addAttribute("order", order);

            List<Item> itemList = itemService.getItemList(PageRequest.of(0, 50)).stream().toList();
            model.addAttribute("itemList", itemList);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }
        return "order_add_edit";
    }

    @GetMapping("/order/edit")
    public String editOrder(Model model,
                          @RequestParam(required = true) String id)
    {
        try {
            Order order = new Order();


            order=orderService.getOrderById(id);
            model.addAttribute("pageTitle", "Order - Edit");
            model.addAttribute("order", order);

            List<Item> itemList = itemService.getItemList(PageRequest.of(0, 50)).stream().toList();
            model.addAttribute("itemList", itemList);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }
        return "order_add_edit";
    }

    @PostMapping("/order/save")
    public String saveOrder(Order order, RedirectAttributes redirectAttributes) {
        try {
            orderService.saveOrder(order);

            redirectAttributes.addFlashAttribute("message", "The Order " + order.getOrderNo() + " has been saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }

        return "redirect:/order/list";
    }
}
