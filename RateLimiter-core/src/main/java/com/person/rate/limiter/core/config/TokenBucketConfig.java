package com.person.rate.limiter.core.config;

import lombok.Data;

/**
 * @Author huangwenjun
 * @Date 2022/9/26
 * @Description
 **/
@Data
public class TokenBucketConfig {

    /**
     * 令牌桶最大令牌数
     */
    private Integer bucketSize;
    /**
     * 令牌添加个数
     */
    private Integer addTokenSpeed;
    /**
     * 令牌添加间隔时间，单位ms
     */
    private long timeInterval;

}
