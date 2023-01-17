package com.zhunzhong;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zhunzhong.demo.api.Result;
import org.junit.Test;

/**
 * @author: zhunzhong
 * @date: 2022-09-24 15:08
 * @description: todo
 */
public class TestEnum {


    @Test
    public void testEnum() {
        System.out.println(JSON.toJSONString(new Result<Void>()));
        String str = JSON.toJSONString(new Result<>());
        Result<Void> r = JSON.parseObject(str, new TypeReference<Result<Void>>() {
        });
        System.out.println(r.getErrorCode().getCode());
        System.out.println(JSON.toJSONString(r));
        System.out.println(r);
    }
}
