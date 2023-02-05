package com.zhunzhong.demo.mysql.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Meter {

    @TableId(type = IdType.INPUT)
    private Integer id;

    private String deviceId;

    private Date ts;

    private Double current;

    private Double voltage;

    private Double phase;

    private String location;

    private String groupId;
}
