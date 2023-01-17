package com.zhunzhong.demo.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author: zhunzhong
 * @date: 2022-08-30 15:02
 * @description: todo
 */
@Slf4j
@Component
public class VtLedThread extends Thread implements InitializingBean {



    public VtLedThread() {
        super("VtLedThread");
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        try {
            while (true) {
               log.info("测试");
               Thread.sleep(100000);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.start();
    }
}
