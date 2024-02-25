package com.obs.testbe.controller;

import com.obs.testbe.model.Order;
import com.obs.testbe.service.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.obs.testbe.helper.Constant.API_URL;

@RestController
@RequiredArgsConstructor
public class OrderAPIController {
    private final OrderService orderService;

    @GetMapping(value = API_URL+"/order/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listing Order with pagination", notes = "Listing Order with pagination")
    public Object getOrderList(@PageableDefault(size = 10, page = 0) Pageable pageable)
    {
        return orderService.getOrderList(pageable);
    }

    @GetMapping(value = API_URL+"/order", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "get Listing Order by OrderNo", notes = "get Listing Order by OrderNo")
    public Object getOrderListByOrderNo(@RequestParam(name = "orderNo") String orderNo,
                              @PageableDefault(size = 10, page = 0) Pageable pageable)
    {
        return orderService.getOrderListByOrderNo(orderNo,pageable);
    }

    @GetMapping(value = API_URL+"/order/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "get Order by ID", notes = "get Order by ID")
    public Object getOrder(@PathVariable(name = "id") String id)
    {
        return orderService.getOrderById(id);
    }

    @DeleteMapping(value = API_URL+"/order/del/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "delete order by ID", notes = "delete order by ID")
    public Object deleteOrder(@PathVariable(name = "id") String id)
    {
        return orderService.deleteOrderById(id);
    }

    @PostMapping(value = API_URL+"/order/save", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "save order", notes = "save order")
    @ResponseStatus(HttpStatus.OK)
    public Object saveOrder(@RequestBody Order order)
    {
        return orderService.saveOrder(order);
    }
}
