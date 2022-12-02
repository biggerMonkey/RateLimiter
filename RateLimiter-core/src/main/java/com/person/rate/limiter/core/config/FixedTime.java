package com.person.rate.limiter.core.config;

import lombok.Data;

/**
 * @Author huangwenjun
 * @Date 2022/8/16
 * @Description
 **/
@Data
public class FixedTime {
    /**
     * 窗口时间长度，单位毫秒
     */
    private int time;
    /**
     * 窗口时间长度内允许的请求数
     */
    private int nums;
}
