package com.hmdp.utils;

/**
 * @Author:SpongeBOb
 * @Date:2023/1/11
 * @Description:实现redis分布式锁
 * @Version:java_15
 */

public interface ILock {
    /**
     * 尝试获取锁
     * @param timeoutSec    所持有的时间，过期后自动释放
     * @return true代表获取成功，false代表获取失败
     */
    boolean tryLock(long timeoutSec);

    /**
     * 释放锁
     */
    void unlock();
}
