package com.person.rate.limiter.core.handlers.impls;

import com.person.rate.limiter.core.config.RateLimiterConfig;
import com.person.rate.limiter.core.config.TokenBucketConfig;
import com.person.rate.limiter.core.enums.RateLimiterTypeEnum;
import com.person.rate.limiter.core.handlers.RateLimiterHandler;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

/**
 * @Author huangwenjun
 * @Date 2022/8/16
 * @Description 令牌桶限流器
 **/
@Service
public class TokenBucketHandler implements RateLimiterHandler {

    private Token addToken;

    private final AtomicInteger currentBucketSize = new AtomicInteger(100);

    @Override
    public boolean handler(RateLimiterConfig config) {
        init(config);
        IntUnaryOperator updateFunction = operand -> {
            int next = operand - 1;
            return Math.max(next, 0);
        };
        return currentBucketSize.getAndUpdate(updateFunction) > 0;

    }

    @Override
    public boolean isSupport(RateLimiterTypeEnum rateLimiterType) {
        return RateLimiterTypeEnum.TOKEN_BUCKET.equals(rateLimiterType);
    }

    public void init(RateLimiterConfig config) {
        if (addToken == null) {
            synchronized (this) {
                if (addToken == null) {
                    this.addToken = new Token(config.getTokenBucketConfig());
                    this.addToken.start();
                }
            }
        }
    }

    class Token extends Thread {

        private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        private TokenBucketConfig tokenBucket;

        public Token(TokenBucketConfig tokenBucket) {
            this.tokenBucket = tokenBucket;
        }

        @Override
        public void run() {
            scheduledExecutorService.scheduleAtFixedRate(() -> {
                int currentSize = currentBucketSize.get();
                if (currentSize < tokenBucket.getBucketSize()) {
                    int step = Math.min(tokenBucket.getAddTokenSpeed(), tokenBucket.getBucketSize() - currentSize);
                    currentBucketSize.addAndGet(step);
                }
            }, 0, tokenBucket.getTimeInterval(), TimeUnit.MILLISECONDS);
        }

    }
}
