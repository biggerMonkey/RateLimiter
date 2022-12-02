package com.person.rate.limiter.core.handlers;

import com.person.rate.limiter.core.config.RateLimiterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author huangwenjun
 * @Date 2022/8/16
 * @Description 限速器类型选择
 **/
@Service
public class RateLimiterManager {

    @Autowired
    private List<RateLimiterHandler> handlers;

    /**
     * 固定窗口限流器
     *
     * @param config
     * @return
     */
    public synchronized boolean handler(RateLimiterConfig config) {
        if (!config.getEnabled()) {
            return true;
        }
        for (RateLimiterHandler handler : handlers) {
            if (!handler.isSupport(config.getType())) {
                continue;
            }
            return handler.handler(config);
        }
        return true;
    }
}
