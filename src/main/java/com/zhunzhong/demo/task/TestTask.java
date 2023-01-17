package com.zhunzhong.demo.task;

import com.zhunzhong.demo.common.Test;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: zhunzhong
 * @date: 2022-09-09 17:06
 * @description: todo
 */
@Slf4j
public class TestTask implements Runnable {

    private AtomicInteger addTimeFlag = new AtomicInteger(0);

    @Override
    public void run() {
        do {
            System.out.println("task run!");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        } while (true);
    }

    public static void main(String[] args) {
        System.out.println("====================");
        Thread testTaskThread = new Thread(new TestTask());
        testTaskThread.start();
        int n = 1;
        while (true) {
            n++;
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (n == 10) {
                testTaskThread.interrupt();
            }
        }
    }
}
