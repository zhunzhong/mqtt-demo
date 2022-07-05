package com.zhunzhong.demo.config;



import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import com.zhunzhong.demo.common.Constants;

/**
 * @author zhunzhong
 * @datetime 2022/7/5 10:36
 * @description
 */
@Data
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = Constants.EMQX)
public class EmqxProperties {

    private String broker;

    private String pubClientId;

    //订阅者clientId.
    private String subClientId;

    //发布topic
    private String pubTopic;

    //订阅topic
    private String subTopic;

    //用户名
    private String userName;

    //密码.
    private String passWord;

    //MQTT 服务质量等级
    private Integer qos = 2;


}
