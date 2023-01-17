package com.zhunzhong;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.junit.Test;

/**
 * @author: zhunzhong
 * @date: 2022-09-08 10:24
 * @description: todo
 */
public class TestSnow {


    @Test
    public void testId() {
        for (int i = 0; i < 10; i++) {
            System.out.println(IdWorker.getId());
        }
    }
}
