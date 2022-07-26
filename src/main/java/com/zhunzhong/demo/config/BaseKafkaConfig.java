package com.zhunzhong.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

//@Configuration
public class BaseKafkaConfig {

    /**
     * 监听器工厂
     */
    @Autowired
    private ConsumerFactory<String, Object> consumerFactory;

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> batchFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        //并发数量
        //factory.setConcurrency(1);
        //开启批量监听
        factory.setBatchListener(true);
        // 被过滤的消息将被丢弃
        factory.setAckDiscarded(true);
        // 设置记录筛选策略
//        factory.setRecordFilterStrategy(new RecordFilterStrategy() {
//            @Override
//            public boolean filter(ConsumerRecord consumerRecord) {
//                String msg = consumerRecord.value().toString();
//                if (Integer.parseInt(msg.substring(msg.length() - 1)) % 2 == 0) {
//                    return false;
//                }
//                // 返回true消息将会被丢弃
//                return true;
//            }
//        });
        // ack模式
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        // 消费者监听器自启动
        factory.setAutoStartup(true);
        return factory;
    }
}
