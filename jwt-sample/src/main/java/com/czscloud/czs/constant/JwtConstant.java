package com.czscloud.czs.constant;

public class JwtConstant {
    //http请求头部信息
    public static final String tokenHeader = "Authorization";
    //用户信息
    public static final String CLAIM_KEY_USERID = "id";
    public static final String CLAIM_KEY_USERNAME = "loginId";
    public static final String CLAIM_KEY_CREATED = "created";
    public static final String CLAIM_KEY_HOLDTIME = "holdtime";
    //用于区分token，充当存入redis中的key
    public static final String CLAIM_KEY_GROUP = "group";
}
