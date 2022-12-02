package com.person.rate.limiter.core.enums;

/**
 * @Author huangwenjun
 * @Date 2022/8/16
 * @Description 限流类型
 * 1.固定时间窗口限流算法 2.滑动时间窗口限流算法 3.令牌桶限流算法 4.漏桶限流算法
 **/
public enum RateLimiterTypeEnum {
    FIXED_TIME, SLIDING_TIME, TOKEN_BUCKET, LEAKY_BUCKET;
}
