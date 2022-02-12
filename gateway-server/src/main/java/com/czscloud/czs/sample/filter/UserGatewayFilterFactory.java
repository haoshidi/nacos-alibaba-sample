package com.czscloud.czs.sample.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Component
public class UserGatewayFilterFactory extends AbstractGatewayFilterFactory<UserGatewayFilterFactory.Config> {

    public static final String[] KEY_ARRAY={"minId","maxId"};

    public UserGatewayFilterFactory() {
        super(UserGatewayFilterFactory.Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(KEY_ARRAY);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                String id = exchange.getRequest().getQueryParams().getFirst("id");
                if(null != id){
                    int numberId = Integer.parseInt(id);
                    if(numberId > config.getMinId() && numberId<config.getMaxId()){
                        return chain.filter(exchange);
                    }
                }
                byte [] bytes = ("您不能访问"+id+"用户数据").getBytes(StandardCharsets.UTF_8);
                DataBuffer wrap = exchange.getResponse().bufferFactory().wrap(bytes);
                exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);//标记不能访问的状态码
                return exchange.getResponse().writeWith(Flux.just(wrap));
            }
        };
    }

    public static class Config {
        private Integer minId;
        private Integer maxId;

        public Integer getMinId() {
            return minId;
        }

        public void setMinId(Integer minId) {
            this.minId = minId;
        }

        public Integer getMaxId() {
            return maxId;
        }

        public void setMaxId(Integer maxId) {
            this.maxId = maxId;
        }
    }
}
