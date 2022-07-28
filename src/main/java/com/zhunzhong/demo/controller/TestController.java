package com.zhunzhong.demo.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.zhunzhong.demo.common.EventEnum;
import com.zhunzhong.demo.statemachine.TestEvents;
import com.zhunzhong.demo.statemachine.TestStates;
import com.zhunzhong.demo.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private StateMachine<TestStates, TestEvents> stateMachine;

    public volatile Map<String, String> filePathMap = Maps.newHashMap();

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @GetMapping("/map")
    @ResponseBody
    public String map() {
        int count = 1000;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            final int finalI = i;
            executorService.submit(() -> {
                filePathMap.put(String.valueOf(finalI), String.valueOf(finalI));
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("size:{}", filePathMap.entrySet().size());
        log.info(JSON.toJSONString(filePathMap));
        return "success";
    }

    @GetMapping("/event")
    @ResponseBody
    public String event() {
        SpringUtils.applicationContext.publishEvent(EventEnum.EVENT1);
        return "success";
    }

    @GetMapping("/stateMachine")
    @ResponseBody
    public String stateMachine() {
        stateMachine.start();
        stateMachine.sendEvent(TestEvents.PAY);
        stateMachine.sendEvent(TestEvents.RECEIVE);
        return "success";
    }


}
