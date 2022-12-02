package com.person.rate.limiter.core.config;

import com.person.rate.limiter.core.enums.RateLimiterTypeEnum;
import lombok.Data;

/**
 * @Author huangwenjun
 * @Date 2022/8/16
 * @Description
 **/
@Data
public class RateLimiterConfig {
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
    /**
     * 滑动窗口配置
     */
    private SlidingTime sliding;
    /**
     * 漏桶限流器配置
     */
    private LeakyBucket leaky;
    /**
     * 令牌桶限流器配置
     */
    private TokenBucketConfig tokenBucketConfig;
}
