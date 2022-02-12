package com.czscloud.czs.shop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ShopController {
    private final Map<Integer,String> shopInfo = new HashMap<Integer,String>(){
        {
            put(1,"这是苹果");
            put(1024,"这是芒果");
        }
    };
    @RequestMapping("/shop/findById")
    public String findById(@RequestParam("id") Integer id){
        return shopInfo.getOrDefault(id,null);
    }
}
