package cn.zbq.springcloud.gateway.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

/**
 * 自定义一个过滤器工厂
 * <p>
 * 类名必须以GatewayFilterFactory结尾
 *
 * @author Zbq
 * @since 2019/12/4 22:51
 */
@Slf4j
@Component
public class PreLogGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {
    @Override
    public GatewayFilter apply(NameValueConfig config) {
        GatewayFilter filter =((exchange, chain) -> {

            log.info("请求进来了...{},{}", config.getName(), config.getValue());

            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().build();
            ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
            return chain.filter(modifiedExchange);
        });
        return new OrderedGatewayFilter(filter, 1000);
    }
}
