package com.person.rate.limiter.starter.config;

import com.person.rate.limiter.core.config.FixedTime;
import com.person.rate.limiter.core.enums.RateLimiterTypeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author huangwenjun
 * @Date 2022/8/16
 * @Description
 **/
@Data
@ConfigurationProperties(prefix = "rate.limiter")
public class RateLimiterProperties {
    /**
     * 是否启用
     */
    private Boolean enabled = false;
    /**
     * 限流器类型
     */
    private RateLimiterTypeEnum type;
    /**
     * 固定窗口配置
     */
    private FixedTime fixed;

}