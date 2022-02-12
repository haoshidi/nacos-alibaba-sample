package com.czscloud.czs.sample.filter;

import jdk.nashorn.internal.objects.Global;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

//@Component
public class TokenGlobalFilter implements GlobalFilter, Ordered {
    private final String TOKEN_VALUE="123456";
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getQueryParams().getFirst("token");
        System.out.println(token);
        if(null != token){
            if(TOKEN_VALUE.equals(token)){
                return chain.filter(exchange);//放行
            }
        }
        byte [] bytes = ("您不能访问"+exchange.getRequest().getPath()+"地址").getBytes(StandardCharsets.UTF_8);
        DataBuffer wrap = exchange.getResponse().bufferFactory().wrap(bytes);
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);//标记不能访问的状态码(401,UNAUTHORIZED)
        return exchange.getResponse().writeWith(Flux.just(wrap));
    }

    @Override
    public int getOrder() {//排序，数据越小，优先级越高
        return 0;
    }
}
