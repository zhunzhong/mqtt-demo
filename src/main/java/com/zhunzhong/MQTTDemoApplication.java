package com.zhunzhong;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@MapperScan
public class MQTTDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MQTTDemoApplication.class, args);
    }

}
