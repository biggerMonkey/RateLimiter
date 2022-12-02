package com.person.rate.limiter.core.config;

import lombok.Data;

/**
 * @Author huangwenjun
 * @Date 2022/8/16
 * @Description
 **/
@Data
public class SlidingTime {
    /**
     * 窗口大小，单位为10ms
     * eg：windowSize =5 ，表示5*10ms内请求数不超过nums，超过则抛弃
     */
    private int windowSize;
    /**
     * 窗口时间长度内允许的请求数
     */
    private int nums;
}
