package com.zhunzhong.demo.controller;

import com.zhunzhong.demo.common.Constants;
import com.zhunzhong.demo.config.EmqxProperties;
import com.zhunzhong.demo.message.OnMessageCallback;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 中央仓库地址：https://mvnrepository.com/
 * Created by root on 2019/4/28.
 */
@Slf4j
@RestController
@RequestMapping("/emqx")
public class EmqxDemoController {


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
            log.info("Connecting to broker: {}", emqxProperties.getBroker());
            this.pubClient.connect(connOpts);
            log.info("Connected");

        } catch (MqttException e) {
            log.error("reason {}", e.getReasonCode());
            log.error("msg {}", e.getMessage());
            log.error("loc {}", e.getLocalizedMessage());
            log.error("cause " + e.getCause());
            log.error("excep " + e);
            log.error(e.getMessage(), e);
        }
        return Constants.RET_SUCCESS;
    }

    @PostMapping("/pub/message")
    public String pubMessage(@RequestParam("content") String content) {
        // 消息发布所需参数
        log.info("Publishing message: {}", content);
        MqttMessage message = new MqttMessage(content.getBytes());
        message.setQos(emqxProperties.getQos());
        try {
            this.pubClient.publish(emqxProperties.getPubTopic(), message);
            log.info("Message published");
        } catch (MqttException e) {
            log.error(e.getMessage(), e);
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
            log.info("SubClient Connecting to broker: {}", emqxProperties.getBroker());
            this.subClient.connect(connOpts);
            log.info("SubClient Connected");

        } catch (MqttException e) {
            log.error("reason {}", e.getReasonCode());
            log.error("msg {}", e.getMessage());
            log.error("loc {}", e.getLocalizedMessage());
            log.error("cause " + e.getCause());
            log.error("excep " + e);
            log.error(e.getMessage(), e);
        }
        return Constants.RET_SUCCESS;
    }

    @GetMapping("/sub/message")
    public String subMessage() {
        // 订阅消息
        try {
            this.subClient.subscribe(emqxProperties.getSubTopic());
        } catch (MqttException e) {
            log.error(e.getMessage(), e);
        }
        return Constants.RET_SUCCESS;
    }

    //用户身份认证
    @PostMapping("/v3/auth")
    @ResponseBody
    public ResponseEntity<String> authV3(String clientid, String username, String password) {
        log.info("clientid:{},username:{},password:{}", clientid, username, password);
        if ("test".equals(username) && "test".equals(password) ||
                "test1".equals(username) && "test1".equals(password)
        ) {
            //认证成功
            return ResponseEntity.status(200).body(Constants.RET_SUCCESS);
        } else if ("ignore".equals(username)) {
            //忽略认证
            return ResponseEntity.status(200).body("ignore");
        }
        //认证失败
        return ResponseEntity.status(401).body(null);
    }

    //是否是超级管理员
    @PostMapping("/v3/superuser")
    @ResponseBody
    public ResponseEntity<String> superuserV3(String clientid, String username) {
        log.info("clientid:{},username:{}", clientid, username);
        if ("admin".equals(username)) {
            //是超级用户
            return ResponseEntity.status(200).body("success");
        }
        //不是超级用户
        return ResponseEntity.status(401).body(null);
    }

    //access=%A,username=%u,clientid=%c,ipaddr=%a,topic=%t,mountpoint=%m
    //ACL授权查询
    @PostMapping("/v3/acl")
    @ResponseBody
    public ResponseEntity<String> aclV3(String access, String username, String clientid,
                                        String ipaddr, String topic, String mountpoint) {
        log.info("access={},username={},clientid={},ipaddr={},topic={},mountpoint={}",
                access, username, clientid, ipaddr, topic, mountpoint);
        if ("test".equals(username)) {
            //授权通过
            return ResponseEntity.status(200).body(Constants.RET_SUCCESS);
        } else if ("ignore".equals(username)) {
            //忽略授权
            return ResponseEntity.status(200).body("ignore");
        }
        //无权限
        return ResponseEntity.status(401).body(null);
    }


}
