package com.person.rate.limiter.core.config;

import lombok.Data;

/**
 * @Author huangwenjun
 * @Date 2022/8/16
 * @Description
 **/
@Data
public class LeakyBucket {
    /**
     * 最大桶大小
     */
    private Long bucketSize;
    /**
     * 流出速度 xx个/s
     */
    private Long outerNums;

    public Long getBucketSize() {
        return bucketSize == null ? 1000 : bucketSize;
    }
}
