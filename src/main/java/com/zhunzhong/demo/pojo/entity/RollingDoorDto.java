package com.zhunzhong.demo.pojo.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: zyxu
 * @date: 2022/8/26 8:57
 * @description: 卷帘门数据传输对象
 */
@Data
public class RollingDoorDto  implements Serializable {

    /**
     * 卷帘门操作类型
     * 1 前门开启 2 前门关闭 3 后门开启 4 后门关闭
     */
    @Range(min = 1, max = 4, message = "卷帘门操作命令只能是1,2,3,4")
    @NotNull(message = "操作类型不能为空")
    private Integer rollingDoorOpType;

}
