package com.obs.testbe.service;

import com.obs.testbe.helper.Constant;
import com.obs.testbe.helper.ResponseWrapperDTO;
import com.obs.testbe.model.Order;
import com.obs.testbe.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Page<Order> getOrderList(Pageable pageable){
        return orderRepository.findAll(pageable);
    }

    public Page<Order> getOrderListByOrderNo(String orderNo, Pageable pageable){
        return orderRepository.findByOrderNo(orderNo,pageable);
    }

    public Order getOrderById(String id){
        return orderRepository.findById(id).orElseThrow();
    }

    public Object deleteOrderById(String id){
        Order order = orderRepository.findById(id).orElseThrow();
        order.setIsDeleted(Constant.YES);
        orderRepository.save(order);
        return ResponseWrapperDTO.builder()
                .data(order)
                .status(Constant.Success)
                .messages("Order successfully deleted.").build();
    }

    public Object saveOrder(Order order){
        Order orderSave = new Order();
        if(order.getId() > 0){
            orderSave = orderRepository.findById(String.valueOf(order.getId())).orElseThrow();
        }
        orderSave.setOrderNo(order.getOrderNo());
        orderSave.setItemId(order.getItemId());
        orderSave.setQuantity(order.getQuantity());
        orderSave.setIsDeleted(Constant.NO);
        orderRepository.save(orderSave);
        return ResponseWrapperDTO.builder()
                .data(order)
                .status(Constant.Success)
                .messages("Order successfully saved.").build();
    }
}
