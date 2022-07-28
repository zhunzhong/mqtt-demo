package com.zhunzhong.demo.kafka.consumer;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
@ConditionalOnProperty(prefix = "customer.kafka.consumer.listener.enable", name = "topic-test1", havingValue = "true")
public class TestConsumer {

//    @KafkaListener(topics = {"topic-test1"}, groupId = "test-consumer-group",
//            containerFactory = "batchFactory", id = "myListener1")
//    public void test(List<ConsumerRecord<?, ?>> records, Acknowledgment ack) {
//
//        for (ConsumerRecord<?, ?> record : records) {
//            System.out.println(record.toString());
//            System.out.println(record.value().toString());
//        }
//        ack.acknowledge();
//    }

    private final ExecutorService executorService = Executors.newFixedThreadPool(2);


    @KafkaListener(topics = {"topic-test1"}, groupId = "test-consumer-group", id = "myListener1")
    public void test(List<String> records, Acknowledgment ack) {
        log.info(String.format("thread:%d,size:%d==========================================", Thread.currentThread().getId(), records.size()));
        for (String record : records) {

            //log.info(record);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        ack.acknowledge();
//        executorService.submit(() -> {
//            for (String record : records) {
//
//                log.info(record);
//                try {
//                    Thread.sleep(300);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//
//            }
//            ack.acknowledge();
//        });

    }
}
