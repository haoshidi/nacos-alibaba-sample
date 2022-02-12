package com.czscloud.czs.sample.service;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserRoutePredicateFactory extends AbstractRoutePredicateFactory<UserRoutePredicateFactory.Config> {

    public static final String[] KEY_ARRAY={"minId","maxId"};

    public UserRoutePredicateFactory() {
        super(UserRoutePredicateFactory.Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {//读取配置文件里的值，赋值到Config对象属性值
        return Arrays.asList(KEY_ARRAY);
    }

    @Override
    public Predicate<ServerWebExchange> apply(UserRoutePredicateFactory.Config config) {
        return new GatewayPredicate() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                String id = serverWebExchange.getRequest().getQueryParams().getFirst("id");
                if(null != id){
                    int numberId = Integer.parseInt(id);
                    if(numberId > config.getMinId() && numberId<config.getMaxId()){
                        return true;
                    }
                }
                return false;
            }
        };
    }

    @Validated
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
