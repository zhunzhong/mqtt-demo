package com.zhunzhong.demo.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Order(2)
@Slf4j
@Component
public class TestCommandLineRunner1 implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {
        log.info("TestCommandLineRunner1");
    }
}
