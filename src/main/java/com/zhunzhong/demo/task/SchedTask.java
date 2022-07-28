package com.zhunzhong.demo.task;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
public class SchedTask {

    @Scheduled(cron = "10 * * * * ?")
    public void job1(){
        log.info("job1=================");
    }
}
