package cn.zbq.springcloud.gateway.gateway;

import lombok.Data;

import java.time.LocalTime;

/**
 * 时间段配置类
 *
 * @author Zbq
 * @since 2019/12/2 21:41
 */
@Data
public class TimeBetweenConfig {
    /**
     * 开始时间
     */
    private LocalTime start;
    /**
     * 结束时间
     */
    private LocalTime end;
}
