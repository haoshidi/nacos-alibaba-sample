package com.czscloud.czs.seata.controller;

import com.czscloud.czs.seata.service.OrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderController {
    @Resource
    private OrderService orderService;

    @RequestMapping("/createOrder")
    public Integer createOrder(@RequestParam("id") Integer id){
        return orderService.createOrder(id);
    }

}
