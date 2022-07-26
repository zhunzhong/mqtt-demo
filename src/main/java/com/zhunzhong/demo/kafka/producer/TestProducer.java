package com.zhunzhong.demo.kafka.producer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class TestProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private JSONObject data;

    @PostConstruct
    void init() {
        InputStream inputStream = this.getClass().getResourceAsStream("/test.json");
        try {
            byte[] data = new byte[inputStream.available()];
            IOUtils.readFully(inputStream, data);
            this.data = JSON.parseObject(new String(data));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void sendMessage(int count) {

        for (int i = 0; i < count; i++) {
            executorService.submit(() -> {
                kafkaTemplate.send("topic-test1", data.toJSONString());
            });

        }

    }


}
