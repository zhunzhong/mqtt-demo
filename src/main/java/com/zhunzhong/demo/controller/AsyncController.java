package com.zhunzhong.demo.controller;

import com.zhunzhong.demo.service.AsyncRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author: zhunzhong
 * @date: 2023-01-29 10:53
 * @description: todo
 */
@Slf4j
@RestController
@RequestMapping("/async")
public class AsyncController {

    private AsyncRequestService asyncRequestService;

    @GetMapping("/value")
    public String getValue() {

        String msg =  null;
        Future<String> result = null;
        try{
            result = asyncRequestService.getValue();
            msg = result.get(10, TimeUnit.SECONDS);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (result != null){
                result.cancel(true);
            }
        }

        return msg;
    }

    @PostMapping("/value")
    public void postValue(String msg) {
        asyncRequestService.postValue(msg);
    }
}
