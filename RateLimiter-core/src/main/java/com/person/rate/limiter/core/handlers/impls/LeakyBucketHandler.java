package com.person.rate.limiter.core.handlers.impls;

import com.person.rate.limiter.core.config.LeakyBucket;
import com.person.rate.limiter.core.config.RateLimiterConfig;
import com.person.rate.limiter.core.enums.RateLimiterTypeEnum;
import com.person.rate.limiter.core.handlers.RateLimiterHandler;
import org.springframework.stereotype.Service;

/**
 * @Author huangwenjun
 * @Date 2022/8/16
 * @Description 漏桶限流器 恒定速度出去，当桶满时，拒绝请求
 **/
@Service
public class LeakyBucketHandler implements RateLimiterHandler {

    /**
     * 当前桶大小
     */
    private long currentBucketSize = 0;


    @Override
    public boolean handler(RateLimiterConfig config) {
        LeakyBucket leaky = config.getLeaky();
        //超过桶大小，拒绝请求
        if (currentBucketSize > leaky.getBucketSize()) {
            return false;
        }
        currentBucketSize++;
        return false;
    }

    @Override
    public boolean isSupport(RateLimiterTypeEnum rateLimiterType) {
        return RateLimiterTypeEnum.LEAKY_BUCKET.equals(rateLimiterType);
    }
}
