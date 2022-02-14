package com.czscloud.czs.seata.fallback;

import com.czscloud.czs.seata.service.DistributionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DistributionServiceFallback implements DistributionService {

    @Override
    public Integer distribution(String orderId) {
        System.out.println("兜底方法");
        return 0;
    }
}
