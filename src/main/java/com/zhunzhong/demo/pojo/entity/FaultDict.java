package com.zhunzhong.demo.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author huweibin
 * @description 故障码管理
 * @since 2022/9/16 21:41
 **/
@Data
public class FaultDict {


    private String deviceName;

    private String deviceCode;

    private String faultCode;

    private String faultNumber;

    private String faultContent;

    private String faultLevelCode;

    private String occurCondition;

    private String handleMethod;

    private String remark;

}
