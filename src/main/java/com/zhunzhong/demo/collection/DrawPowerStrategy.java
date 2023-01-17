package com.zhunzhong.demo.collection;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author huweibin
 * @description 取电策略vo
 * @since 2022/9/15 16:54
 **/
@Data
public class DrawPowerStrategy {


    private Float satisfySoc;

    private BigDecimal failTimes;

}
