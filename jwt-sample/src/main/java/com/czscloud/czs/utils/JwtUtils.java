package com.czscloud.czs.utils;

import com.czscloud.czs.constant.JwtConstant;
import com.czscloud.czs.entity.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Component
public class JwtUtils {
    //过期时间，毫秒，30分钟
    @Value("${jwt.expire.time}")
    private long EXPIRE;

    @Value("${jwt.subject.name}")
    private String SUBJECT;

    //秘钥
    @Value("${jwt.secret.key}")
    private String APPSECRET;
    /**
     * 根据用户信息生成token
     */
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("id", user.getId());
        claims.put("loginId", user.getLoginId());
        claims.put("created", new Date());
        claims.put("holdtime",generateLoginDate());
        claims.put("group",generateGroup());
        return generateToken(claims);
    }
    /**
     * 生成token的group
     */
    private Object generateGroup() {
        String group = UUID.randomUUID().toString();
        group = group.replace("-","");
        return group;
    }

    /**
     * 生成token的免登录时间
     */
    public Date generateLoginDate() {
        //有效期内可刷新token
        Calendar calendar = new GregorianCalendar();
        //当天+2
        calendar.add(12,10);

        return calendar.getTime();
    }

    /**
     * 根据负责生成JWT的token
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setSubject(SUBJECT)
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, APPSECRET)
                .compact();
    }

    /**
     * 生成token的过期时间
     */
    public Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + EXPIRE);
    }
    /**
     * 从token中获取登录用户名
     */
    public String getUserNameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        String username = (String) claims.get("loginId");
        return username;
    }
    /**
     * 从token中获取JWT中的负载
     */
    public Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(APPSECRET)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (ExpiredJwtException e) {
            String id = (String) e.getClaims().get("id");
            String username = (String) e.getClaims().get("loginId");
            String group = (String)e.getClaims().get("group");
            log.error("JWT载荷中 用户ID:{} 用户名:{} 所处组:{}", id, username,group);

            claims=e.getClaims();
        } catch (MalformedJwtException e){
            log.error("Json格式错误 {}",e.getLocalizedMessage());
        } catch (SignatureException e){
            log.error("Json格式错误 {}",e.getLocalizedMessage());
        } catch(IllegalArgumentException e){
            log.error("错误 {}",e.getLocalizedMessage());
        }
        return claims;
    }
    /**
     * 从token中获取group
     */
    public String getGroupFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        String group = (String)claims.get(JwtConstant.CLAIM_KEY_GROUP);
        log.error("token中的用户组 {}", group);
        return group;
    }

    public String getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        String userId = (String)claims.get(JwtConstant.CLAIM_KEY_USERID);
        log.error("token中的用户Id {}", userId);
        return userId;
    }

    public long getLoginDate(String token) {
        Date date = getHoldTime(token);
        return date.getTime();
    }

    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        claims.put(JwtConstant.CLAIM_KEY_CREATED, new Date());
        claims.put(JwtConstant.CLAIM_KEY_HOLDTIME,generateLoginDate());
        //新的group key 区分黑名单中的key
        claims.put(JwtConstant.CLAIM_KEY_GROUP,generateGroup());
        return generateToken(claims);
    }

    public Date getHoldTime(String token) {
        Claims claims = getClaimsFromToken(token);
        Long dateTime = (Long) claims.get(JwtConstant.CLAIM_KEY_HOLDTIME);
        Date date = new Date(dateTime);
        log.info("原数据值：{} 该token免登录时间截止至 {}",dateTime,
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        return date;
    }
}
