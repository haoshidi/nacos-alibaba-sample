package com.czscloud.czs.controller;

import com.czscloud.czs.constant.JwtConstant;
import com.czscloud.czs.entity.Result;
import com.czscloud.czs.entity.User;
import com.czscloud.czs.service.UserService;
import com.czscloud.czs.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class ClientController {
    @Autowired
    JwtUtils jwtUtils;
    @Value("${jwt.user.format}")
    private String  jwtUsername;
    @Value("${jwt.token.format}")
    private String jwtToken;
    @Value("${jwt.blacklist.format}")
    private String jwtBlacklist;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public Result login(@RequestParam("loginId") String loginId, @RequestParam("password") String password) {
        if (StringUtils.isEmpty(loginId) || StringUtils.isEmpty(password)) {
            return Result.error().message("账号或密码不能为空！");
        } else {
            //判断用户是否存在
            User user = userService.findByLogin(loginId,password);
            if(user == null){
                return Result.error().message("账号或密码错误！");
            }
            String key = String.format(jwtUsername,user.getId());
            log.error("redis key: {}",key);
            //判断redis中是否存在该用户名
            String name = (String) redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(name)){
                return Result.error().message(name+" 已经登录！");
            }
            //成功生成token
            String token= jwtUtils.generateToken(user);
            //用户名有效时间 - 用户免登录时间
            //得到jwt中的截止时间
            long time=jwtUtils.generateLoginDate().getTime();
            long expired = time-new Date().getTime();
            //信息放入redis - set key value EX 10
            redisTemplate.opsForValue().set(key,user.getLoginId(),expired, TimeUnit.MILLISECONDS);
            //存当前id对应正在使用的token
            //hset key field value
            redisTemplate.opsForHash().put(jwtToken,user.getId(),token);
            log.error("redis hashKey: {} field: {} token:{}","JWT_TOKEN",user.getId(),token);
            return Result.ok().data("token",token);
        }
    }

    /**
     * 再次登录
     * @param loginId,password
     * @param request
     * @return
     */
    @PostMapping("/relogin")
    public Result relogin(@RequestParam("loginId") String loginId, @RequestParam("password") String password,HttpServletRequest request){
        if (StringUtils.isEmpty(loginId) || StringUtils.isEmpty(password)) {
            return Result.error().message("账号或密码不能为空！");
        }else{
            //判断用户是否存在
            User user = userService.findByLogin(loginId,password);
            if(user == null){
                return Result.error().message("账号或密码错误！");
            }
            String token = request.getHeader(JwtConstant.tokenHeader);
            //删除用户名
            String userKey = String.format(jwtUsername,user.getId());
            redisTemplate.delete(userKey);
            //删除用户token
            redisTemplate.opsForHash().delete(jwtToken,user.getId());
            //token放入黑名单
            String group = jwtUtils.getGroupFromToken(token);
            long time= jwtUtils.generateLoginDate().getTime();
            long expired = time - new Date().getTime();
            log.error("黑名单 - 原始数据: {} redis {} 截止时间: {}",time,userKey,
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time)));
            System.out.println(jwtBlacklist+","+group);
            String blackKey = String.format(jwtBlacklist,group);
            //判断token是否已过期
            if(expired >0 ) {
                redisTemplate.opsForValue().set(blackKey, token, expired, TimeUnit.MILLISECONDS);
            }
            //重新生成用户名有效时间 - 用户免登录时间
            String newToken = jwtUtils.generateToken(user);
            //得到jwt中的截止时间
            time=jwtUtils.generateLoginDate().getTime();
            expired = time-new Date().getTime();

            log.error("重新登录 原始数据-: {} redis {} 截止时间: {}",time,userKey,
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time)));
            //信息放入redis - set key value EX 10
            redisTemplate.opsForValue().set(userKey,user.getLoginId(),expired,TimeUnit.MILLISECONDS);
            //存当前id对应正在使用的token
            //hset key field value
            redisTemplate.opsForHash().put(jwtToken,user.getId(),newToken);
            log.error("redis hashKey: {} field: {} token:{}",jwtToken,user.getId(),token);
            return Result.ok().data("token",newToken);
        }
    }
    @GetMapping("/getInfo")
    public Result getInfo(HttpServletRequest request){
        String token = request.getHeader(JwtConstant.tokenHeader);
        log.info("请求头 {}",token);
        String username = jwtUtils.getUserNameFromToken(token);
        return Result.ok().data("username",username);
    }
    @GetMapping("/logout")
    public Result logout(HttpServletRequest request){

        String token = request.getHeader(JwtConstant.tokenHeader);

        log.info("logout 请求头 {}",token);

        String id = jwtUtils.getUserIdFromToken(token);
        //删除登录的用户名
        String userKey = String.format(jwtUsername,id);
        redisTemplate.delete(userKey);

        //删除id当前使用的token
        redisTemplate.opsForHash().delete(jwtToken,id);
        //token放入黑名单
        String group = jwtUtils.getGroupFromToken(token);
        long time= jwtUtils.getLoginDate(token);
        long expired = time - new Date().getTime();
        log.error("logout 原始数据: {} redis {} 截止时间: {}",time,userKey,
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time)));
        String blackKey = String.format(jwtBlacklist,group);
        if (expired>0) {
            redisTemplate.opsForValue().set(blackKey, token, expired, TimeUnit.MILLISECONDS);
        }

        return Result.ok().message("注销成功");
    }

    @GetMapping(value = "/token/refresh")
    public Object refreshToken(HttpServletRequest request) {
        //1、获取请求头中的Authorization完整值
        String oldToken = request.getHeader(JwtConstant.tokenHeader);
        String refreshToken = "";

        //2、是否可以进行刷新（未过有效时间/是否在免登录范围）
//        if(!jwtUtils.canRefresh(oldToken)|| jwtUtils.isHoldTime(oldToken)){
//            return R.error().message("jwt还未失效，无需刷新").code(20001);
//        }

        //再次获得免登录机会
        long time = jwtUtils.generateLoginDate().getTime();
        long expired = time - new Date().getTime();

        refreshToken =  jwtUtils.refreshToken(oldToken);

        String id = jwtUtils.getUserIdFromToken(refreshToken);
        //原token放入黑名单
        String group = jwtUtils.getGroupFromToken(oldToken);
        String key = String.format(jwtBlacklist,group);
        if (expired>0) {
            redisTemplate.opsForValue().set(key, oldToken, expired, TimeUnit.MILLISECONDS);
        }
        //当前使用的token进行修改
        redisTemplate.opsForHash().put(jwtToken,id,refreshToken);
        //更新用户有效时间, 如果被注销，重新写入redis
        String userkey = String.format(jwtUsername,id);
        String username = jwtUtils.getUserNameFromToken(refreshToken);
        if (expired>0) {
            redisTemplate.opsForValue().set(userkey,username, expired, TimeUnit.MILLISECONDS);
        }

        Date date = jwtUtils.getHoldTime(refreshToken);

        //将新的token交给前端
        return Result.ok().data("token",refreshToken).data("date",date);
    }
}
