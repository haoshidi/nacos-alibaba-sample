package com.czscloud.czs.seata.service.impl;

import com.czscloud.czs.seata.service.DistributionService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class DistributionServiceImpl implements DistributionService {
    @Resource
    private JdbcTemplate jdbcTemplate;

    private final List<Integer> distributorList = new ArrayList<Integer>(){{
        add(1);
        add(2);
        add(3);
    }};

    @Transactional //spring事务
    @Override
    public Integer distribution(String orderId) {
        System.out.println("orderId:"+orderId);
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Integer distributor = distributorList.get(RandomUtils.nextInt(0,distributorList.size()-1));
        int update = jdbcTemplate.update("insert into order_distribution(order_id,distributor) values(?,?)",new Object[]{
                orderId,distributor
        });

        return update;
    }
}
