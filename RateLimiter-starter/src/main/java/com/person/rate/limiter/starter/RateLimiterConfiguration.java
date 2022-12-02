package com.person.rate.limiter.starter;

import com.person.rate.limiter.core.handlers.RateLimiterManager;
import com.person.rate.limiter.starter.config.RateLimiterProperties;
import com.person.rate.limiter.starter.interceptor.RateLimterInterceptor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @Author huangwenjun
 * @Date 2022/8/16
 * @Description
 **/
@Configuration
@EnableConfigurationProperties(RateLimiterProperties.class)
@ConditionalOnProperty(prefix = "rate.limiter", name = "enabled", havingValue = "true", matchIfMissing = true)
@ComponentScan(value = {"com.person.rate.limiter"})
public class RateLimiterConfiguration implements InitializingBean {
    /**
     * 属性参数
     */
    public static RateLimiterProperties properties;

    @Resource
    private RateLimiterProperties rateLimiterProperties;

    @Override
    public void afterPropertiesSet() {
        properties = rateLimiterProperties;
    }

    /**
     * MVC拦截器
     */
    @EnableWebMvc
    @Configuration
    @ConditionalOnProperty(prefix = "rate.limiter", name = "enabled", havingValue = "true", matchIfMissing = true)
    @ConditionalOnMissingBean(name = "rateLimiterWebMvcConfiguration")
    public static class RateLimiterWebMvcConfiguration implements WebMvcConfigurer {
        @Autowired
        private RateLimiterProperties rateLimiterProperties;

        @Autowired
        private RateLimiterManager rateLimiterManager;

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new RateLimterInterceptor(rateLimiterProperties, rateLimiterManager))
                    .addPathPatterns("/**")
                    .order(-1);
        }
    }
}
