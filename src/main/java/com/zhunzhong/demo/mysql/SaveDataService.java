package com.zhunzhong.demo.mysql;


import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhunzhong.demo.mysql.dao.MeterDao;
import com.zhunzhong.demo.mysql.entity.Meter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class SaveDataService extends ServiceImpl<MeterDao, Meter> {

    String[] location = {"California.LosAngeles", "California.SanDiego", "California.SanJose", "California.Campbell", "California.SanFrancisco"};
    float[] current = {8.8f, 10.7f, 9.9f, 8.9f, 9.4f};
    int[] voltage = {119, 116, 111, 113, 118};
    float[] phase = {0.32f, 0.34f, 0.33f, 0.329f, 0.141f};

    @Autowired
    private MeterDao meterDao;

    public void writeData() {
        int max = 117264000;
        int count = 0;
        int perSize = 1000;
        while (count < max) {
            List<Meter> meterList = mockData(perSize);
            log.info(JSON.toJSONString(meterList));
            this.saveBatch(meterList);
            count += perSize;
        }
    }


    public List<Meter> mockData(int count) {
        List<Meter> meterList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Meter meter = new Meter();
            int groupId = RandomUtil.randomInt(5);

            meter.setTs(new Date());
            meter.setDeviceId(String.valueOf(RandomUtil.randomInt(1000)));
            meter.setGroupId(String.valueOf(groupId));
            meter.setCurrent(current[groupId] + RandomUtil.randomDouble(10)); // current
            meter.setVoltage(voltage[groupId] + RandomUtil.randomDouble(10)); // voltage
            meter.setPhase(phase[groupId] + RandomUtil.randomDouble(10)); // phase
            meter.setLocation(location[groupId]); // location

            meterList.add(meter);
        }
        return meterList;
    }
}
