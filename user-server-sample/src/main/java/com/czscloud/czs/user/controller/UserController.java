package com.czscloud.czs.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    private final Map<Integer,String> userInfo = new HashMap<Integer,String>(){
        {
            put(1,"Zhangsan");
            put(2,"Lisi");
        }
    };
    @RequestMapping("/user/findById")
    public String findById(@RequestParam("id") Integer id){
        return userInfo.getOrDefault(id,null);
    }
}
