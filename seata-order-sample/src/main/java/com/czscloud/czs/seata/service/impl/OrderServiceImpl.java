package com.czscloud.czs.seata.service.impl;

import com.czscloud.czs.seata.service.DistributionService;
import com.czscloud.czs.seata.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private DistributionService distributionService;

    private final Map<Integer,String> shopMap= new HashMap<Integer,String> (){{
        put(1,"菜品1");
        put(2,"菜品2");
        put(3,"菜品3");
    }};
    @GlobalTransactional //全局事务注解
    @Transactional //spring 事务注解
    @Override
    public Integer createOrder(Integer id) {
        System.out.println("id："+id);
        if(shopMap.containsKey(id)){
            String orderId = UUID.randomUUID().toString().replace("-","");
            System.out.println(orderId);
            int update =jdbcTemplate.update("insert into t_order(order_id,shop_id) values(?,?)",new Object[]{orderId,id});
            Integer result = distributionService.distribution(orderId);
            if(result <= 0){
                throw new RuntimeException("分配配送員失敗!");
            }
            return update;
        }
        return 0;
    }
}
