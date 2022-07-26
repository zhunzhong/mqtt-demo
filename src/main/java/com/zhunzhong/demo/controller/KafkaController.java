package com.zhunzhong.demo.controller;

import com.zhunzhong.demo.kafka.producer.TestProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    private TestProducer testProducer;


    @GetMapping("/send")
    @ResponseBody
    public String sendData(@RequestParam("count") int count) {
        testProducer.sendMessage(count);
        return "success";
    }
}
