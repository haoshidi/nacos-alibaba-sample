package com.czscloud.czs.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Result {

    private Boolean success;

    private Integer code;

    private String message;

    private Map<String,Object> data = new HashMap<String,Object>();

    public static Result ok(){
        Result r = new Result();
        r.setSuccess(true);
        r.setCode(20000);
        r.setMessage("操作成功");
        return r;
    }
    public static Result error(){
        Result r = new Result();
        r.setSuccess(false);
        r.setCode(20001);
        r.setMessage("操作失败");
        return r;
    }

    //链式编程
    public Result success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public Result message(String message){
        this.setMessage(message);
        return this;
    }

    public Result code(Integer code){
        this.setCode(code);
        return this;
    }

    public Result data(Map<String,Object> map){
        this.setData(map);
        return this;
    }

    public Result data(String key,Object value){
        this.data.put(key,value);
        return this;
    }

}
