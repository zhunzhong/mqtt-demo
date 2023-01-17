package com.zhunzhong;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

/**
 * @author: zhunzhong
 * @date: 2022-10-08 10:02
 * @description: todo
 */
public class JsonTest {


    @Test
    public void testJSON() {
        JSONObject obj = new JSONObject();
        obj.put("mode", 1);
        obj.put("batteryNumberOfGroup", 1);
        obj.put("groupNumberMin", 1);
        System.out.println(obj.toJSONString());
    }
}
