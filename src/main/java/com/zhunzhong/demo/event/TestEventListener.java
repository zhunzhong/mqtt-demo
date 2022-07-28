package com.zhunzhong.demo.event;


import com.zhunzhong.demo.common.EventEnum;
import com.zhunzhong.demo.common.Test;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestEventListener {


    @EventListener
    public void eventHandle1(EventEnum eventEnum) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("thread:{}, call eventHandle1  {}", Thread.currentThread().getId(), eventEnum.getValue());
    }

    @EventListener
    public void eventHandle2(EventEnum eventEnum) {
        log.info("thread:{}, call eventHandle2  {}", Thread.currentThread().getId(), eventEnum.getValue());
    }

    @EventListener
    public void eventObj(Test test) {
        log.info("thread:{}, call eventObj  {}", Thread.currentThread().getId(), test.toString());
    }
}
