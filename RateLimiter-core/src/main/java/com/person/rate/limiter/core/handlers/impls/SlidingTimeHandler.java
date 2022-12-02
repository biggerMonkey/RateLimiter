package com.person.rate.limiter.core.handlers.impls;

import com.person.rate.limiter.core.config.RateLimiterConfig;
import com.person.rate.limiter.core.enums.RateLimiterTypeEnum;
import com.person.rate.limiter.core.handlers.RateLimiterHandler;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author huangwenjun
 * @Date 2022/8/16
 * @Description 滑动窗口限流器
 **/
@Service
public class SlidingTimeHandler implements RateLimiterHandler {

    /**
     * 滑动窗口,请求数量
     */
    private ConcurrentHashMap<Long, Long> counterMap = new ConcurrentHashMap<>();

    @Override
    public boolean handler(RateLimiterConfig config) {
        long currentTime = System.currentTimeMillis();
        //计算单元格编号，10毫秒为一个单元格
        long boxNum = currentTime / 10;
        counterMap.putIfAbsent(boxNum, 1L);
        long totalCounter = 0;
        Iterator<Map.Entry<Long, Long>> iterator = counterMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, Long> entry = iterator.next();
            if (entry.getKey() <= boxNum - config.getSliding().getWindowSize()) {
                iterator.remove();
            } else {
                totalCounter += entry.getValue();
            }
        }
        return totalCounter < config.getSliding().getNums();
    }

    @Override
    public boolean isSupport(RateLimiterTypeEnum rateLimiterType) {
        return RateLimiterTypeEnum.SLIDING_TIME.equals(rateLimiterType);
    }
}
