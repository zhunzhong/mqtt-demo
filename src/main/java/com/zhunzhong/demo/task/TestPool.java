package com.zhunzhong.demo.task;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: zhunzhong
 * @date: 2022-09-09 17:07
 * @description: todo
 */
public class TestPool {

    @Autowired
    private ScheduledThreadPoolExecutor scheduledThreadPool;

    public void test(){
        scheduledThreadPool.scheduleAtFixedRate(new TestTask(),100,100, TimeUnit.SECONDS);
    }

}
