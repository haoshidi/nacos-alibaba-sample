package com.czscloud.czs.seata.service;

import com.czscloud.czs.seata.fallback.DistributionServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "${distribution.name}" , fallback = DistributionServiceFallback.class)
public interface DistributionService {

    @RequestMapping(value = "/distribution")
    Integer distribution(@RequestParam("orderId") String orderId);

}
