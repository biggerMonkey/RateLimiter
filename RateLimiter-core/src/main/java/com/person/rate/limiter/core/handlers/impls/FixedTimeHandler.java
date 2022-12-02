package com.person.rate.limiter.core.handlers.impls;

import com.person.rate.limiter.core.config.RateLimiterConfig;
import com.person.rate.limiter.core.enums.RateLimiterTypeEnum;
import com.person.rate.limiter.core.handlers.RateLimiterHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author huangwenjun
 * @Date 2022/8/16
 * @Description
 **/
@Slf4j
@Service
public class FixedTimeHandler implements RateLimiterHandler {
    /**
     * 上次请求时间
     */
    private long lastTime = System.currentTimeMillis();
    /**
     * 计数器
     */
    private AtomicLong counter = new AtomicLong(0);

    @Override
    public boolean handler(RateLimiterConfig config) {
        long currentTime = System.currentTimeMillis();
        boolean compareTime = currentTime - lastTime < config.getFixed().getTime();
        System.out.println("请求次数：" + counter + " 上次请求时间：" + lastTime + " 当前请求时间：" + currentTime);
        lastTime = currentTime;
        //在规定时间内，请求数小于规定数量，请求数增加，允许请求进入
        if (compareTime
                && counter.longValue() < config.getFixed().getNums()) {
            counter.getAndIncrement();
            return true;
        }
        //在规定时间内，请求数大于等于规定数量，请求数重置为0，拒绝请求
        //超过规定时间，请求数重置为0，允许请求进入
        counter.set(0);
        return !compareTime;
    }

    @Override
    public boolean isSupport(RateLimiterTypeEnum rateLimiterType) {
        return RateLimiterTypeEnum.FIXED_TIME.equals(rateLimiterType);
    }
}
