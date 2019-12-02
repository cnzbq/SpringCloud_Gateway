package cn.zbq.springcloud.gateway.gateway;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * 自定义时间谓词工厂
 * 注意：
 * -->1：类名必须以RoutePredicateFactory结尾，Spring Cloud Gateway规定
 * -->2：TimeBetween必须与配置文件中的保持一致
 * <p/>
 * 时间格式  源码 org.springframework.format.datetime.standard.DateTimeFormatterRegistrar#getFallbackFormatter
 *
 * @author Zbq
 * @since 2019/12/2 21:33
 */
@Component
public class TimeBetweenRoutePredicateFactory
        extends AbstractRoutePredicateFactory<TimeBetweenConfig> {

    public TimeBetweenRoutePredicateFactory() {
        super(TimeBetweenConfig.class);
    }

    /**
     * 路由谓词工厂的核心方法
     *
     * @param config 配置
     * @return Predicate
     */
    @Override
    public Predicate<ServerWebExchange> apply(TimeBetweenConfig config) {
        LocalTime start = config.getStart();
        LocalTime end = config.getEnd();

        // 匿名内部类写法
        /*return new Predicate<ServerWebExchange>() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                LocalTime now = LocalTime.now();
                return now.isAfter(start) && now.isBefore(end);
            }
        };*/
        // lambda 写法
        return serverWebExchange -> {
            LocalTime now = LocalTime.now();
            return now.isAfter(start) && now.isBefore(end);
        };
    }

    /**
     * 控制配置类和配置文件的关系
     *
     * @return list
     */
    @Override
    public List<String> shortcutFieldOrder() {
        // start 会作为第一个参数，对应配置文件中的“上午9:00”，end作为第二个参数，对应配置文件的“下午17;00”
        return Arrays.asList("start", "end");
    }

    /**
     * 获取time类型的时间格式
     */
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        System.out.println(formatter.format(LocalTime.now()));
    }
}
