package com.zhunzhong.demo.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * @author: zhunzhong
 * @date: 2023-01-29 10:57
 * @description: todo
 */
@Service
public class AsyncRequestService {


    private String msg = null;

    @Async
    public Future<String> getValue() throws InterruptedException {

        while (true){
            synchronized (this){
                if (msg != null){
                    String resultMsg = msg;
                    msg = null;
                    return new AsyncResult(resultMsg);
                }
            }
            Thread.sleep(100);
        }
    }

    public synchronized void postValue(String msg) {
        this.msg = msg;
    }
}
