package com.zhunzhong;

import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.zhunzhong.demo.collection.DrawPowerStrategy;
import org.joda.time.DateTime;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;


/**
 * @author: zhunzhong
 * @date: 2022-09-20 15:11
 * @description: todo
 */
public class NumberTest {


    @Test
    public void test(){
        short a=0;
        for(int i=0;i<Integer.MAX_VALUE;i++){
            a++;
            System.out.println(a);
        }
        System.out.println(Short.MAX_VALUE);
        Date startDate=DateUtil.parse("2021-04-20 02:00:00");
        Date endDate=DateUtil.parse("2021-04-21 05:10:00");


        long millis=DateUtil.between(startDate,endDate, DateUnit.MS);
        System.out.println(DateUtil.formatBetween(millis, BetweenFormatter.Level.MINUTE));


        LocalTime time=LocalTime.parse("15:47");
        System.out.println(time.toSecondOfDay());
        System.out.println(time.toString());

        double d = 55.63784867;      float f = 25.657933f;
        System.out.println("Value of double: " + d);
        System.out.println("Value of float: " + f);

        DrawPowerStrategy drawPowerStrategy=new DrawPowerStrategy();
        drawPowerStrategy.setSatisfySoc(90f);
        drawPowerStrategy.setFailTimes(BigDecimal.valueOf(90));
        System.out.println(JSON.toJSON(drawPowerStrategy));

    }
}
