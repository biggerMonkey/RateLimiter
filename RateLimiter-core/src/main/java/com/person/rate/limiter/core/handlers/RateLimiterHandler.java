package com.person.rate.limiter.core.handlers;

import com.person.rate.limiter.core.config.RateLimiterConfig;
import com.person.rate.limiter.core.enums.RateLimiterTypeEnum;

/**
 * @Author huangwenjun
 * @Date 2022/8/16
 * @Description
 **/
public interface RateLimiterHandler {

    public boolean handler(RateLimiterConfig config);

    public boolean isSupport(RateLimiterTypeEnum rateLimiterType);
}
