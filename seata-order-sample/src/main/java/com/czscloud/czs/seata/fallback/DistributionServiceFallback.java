package com.czscloud.czs.seata.fallback;

import com.czscloud.czs.seata.service.DistributionService;
import org.springframework.stereotype.Component;

@Component
public class DistributionServiceFallback implements DistributionService {
    @Override
    public Integer distribution(String orderId) {
        System.out.println("兜底方法");
        return 0;
    }
}
