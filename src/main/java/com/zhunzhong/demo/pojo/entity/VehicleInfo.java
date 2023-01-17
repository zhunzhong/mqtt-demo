package com.zhunzhong.demo.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;


/**
 * @ClassName vehicleInfo
 * @Date 2022/9/6 13:30
 * @Author huweibin
 * @Description 车辆管理信息
 */
@Data
public class VehicleInfo {


    @TableId(type = IdType.ASSIGN_ID)
    private Long id;


    private String vehicleNo;//车牌号


    private String vin;//车辆VIN码


    private String mac;//车机蓝牙mac地址


    private String vehicleTypeCode;//车辆型号编码


    private String vehicleTypeName;//车辆型号名词


}
