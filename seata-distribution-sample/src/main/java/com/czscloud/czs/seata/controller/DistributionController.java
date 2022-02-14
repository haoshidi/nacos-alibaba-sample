package com.czscloud.czs.seata.controller;

import com.czscloud.czs.seata.service.DistributionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class DistributionController {
    @Resource
    private DistributionService distributionService;

    @RequestMapping("/distribution")
    public Integer distribution(@RequestParam("orderId") String orderId){
        System.out.println("----"+orderId);
        return distributionService.distribution(orderId);
    }
}
