package com.person.rate.limiter.starter.interceptor;

import com.alibaba.fastjson.JSON;
import com.person.rate.limiter.core.config.RateLimiterConfig;
import com.person.rate.limiter.core.handlers.RateLimiterManager;
import com.person.rate.limiter.starter.config.RateLimiterProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author huangwenjun
 * @Date 2022/8/16
 * @Description 限流拦截器
 **/
@Slf4j
public class RateLimterInterceptor implements HandlerInterceptor {

    private RateLimiterProperties rateLimiterProperties;

    private RateLimiterManager rateLimiterManager;


    public RateLimterInterceptor(RateLimiterProperties rateLimiterProperties, RateLimiterManager rateLimiterManager) {
        this.rateLimiterProperties = rateLimiterProperties;
        this.rateLimiterManager = rateLimiterManager;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean limiterRes = rateLimiterManager.handler(convert(rateLimiterProperties));
        if (!limiterRes) {
            createResponse(response);
        }
        return limiterRes;
    }

    private RateLimiterConfig convert(RateLimiterProperties properties) {
        RateLimiterConfig config = new RateLimiterConfig();
        config.setEnabled(properties.getEnabled());
        config.setType(properties.getType());
        config.setFixed(properties.getFixed());
        return config;
    }

    private void createResponse(HttpServletResponse response) {
        response.setStatus(200);
        response.setHeader("Content-Type", "application/json");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            Map<String, Object> result = new HashMap<>();
            result.put("code", 1);
            result.put("msg", "限流器拦截，稍后重试");
            writer.write(JSON.toJSONString(result));
            writer.flush();
        } catch (IOException ex) {
            log.error(ex.getMessage());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

    }
}
