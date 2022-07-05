package com.zhunzhong.demo.controller;

import com.zhunzhong.demo.common.Constants;
import com.zhunzhong.demo.config.EmqxProperties;
import com.zhunzhong.demo.message.OnMessageCallback;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 中央仓库地址：https://mvnrepository.com/
 * Created by root on 2019/4/28.
 */
@RestController
@RequestMapping("/emqx")
public class EmqxDemoController {

    private static final Logger logger = LoggerFactory.getLogger(EmqxDemoController.class);

    @Autowired
    private EmqxProperties emqxProperties;

    /**
     * 发布消息client.
     */
    private MqttClient pubClient;

    /**
     * 订阅消息client.
     */
    private MqttClient subClient;

    @GetMapping("/pub/connect")
    public String pubConnect() {
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            this.pubClient = new MqttClient(emqxProperties.getBroker(), emqxProperties.getPubClientId(), persistence);

            // MQTT 连接选项
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setUserName(emqxProperties.getUserName());
            connOpts.setPassword(emqxProperties.getPassWord().toCharArray());
            // 保留会话
            connOpts.setCleanSession(true);

            // 设置回调
            //this.pubClient.setCallback(new OnMessageCallback());

            // 建立连接
            logger.info("Connecting to broker: {}", emqxProperties.getBroker());
            this.pubClient.connect(connOpts);
            logger.info("Connected");

        } catch (MqttException e) {
            logger.error("reason {}", e.getReasonCode());
            logger.error("msg {}", e.getMessage());
            logger.error("loc {}", e.getLocalizedMessage());
            logger.error("cause " + e.getCause());
            logger.error("excep " + e);
            logger.error(e.getMessage(), e);
        }
        return Constants.RET_SUCCESS;
    }

    @PostMapping("/pub/message")
    public String pubMessage(@RequestParam("content") String content) {
        // 消息发布所需参数
        logger.info("Publishing message: {}", content);
        MqttMessage message = new MqttMessage(content.getBytes());
        message.setQos(emqxProperties.getQos());
        try {
            this.pubClient.publish(emqxProperties.getPubTopic(), message);
            logger.info("Message published");
        } catch (MqttException e) {
            logger.error(e.getMessage(), e);
        }
        return Constants.RET_SUCCESS;
    }

    @GetMapping("/sub/connect")
    public String subConnect() {
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            this.subClient = new MqttClient(emqxProperties.getBroker(), emqxProperties.getSubClientId(), persistence);

            // MQTT 连接选项
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setUserName(emqxProperties.getUserName());
            connOpts.setPassword(emqxProperties.getPassWord().toCharArray());
            // 保留会话
            connOpts.setCleanSession(true);

            // 设置回调
            this.subClient.setCallback(new OnMessageCallback());

            // 建立连接
            logger.info("SubClient Connecting to broker: {}", emqxProperties.getBroker());
            this.subClient.connect(connOpts);
            logger.info("SubClient Connected");

        } catch (MqttException e) {
            logger.error("reason {}", e.getReasonCode());
            logger.error("msg {}", e.getMessage());
            logger.error("loc {}", e.getLocalizedMessage());
            logger.error("cause " + e.getCause());
            logger.error("excep " + e);
            logger.error(e.getMessage(), e);
        }
        return Constants.RET_SUCCESS;
    }

    @GetMapping("/sub/message")
    public String subMessage() {
        // 订阅消息
        try {
            this.subClient.subscribe(emqxProperties.getSubTopic());
        } catch (MqttException e) {
            logger.error(e.getMessage(), e);
        }
        return Constants.RET_SUCCESS;
    }


}
