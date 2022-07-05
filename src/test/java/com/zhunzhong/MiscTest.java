package com.zhunzhong;

import java.text.MessageFormat;
import java.util.Date;
import org.junit.Test;

/**
 * @author zhunzhong
 * @datetime 2022/7/5 15:52
 * @description
 */
public class MiscTest {

    @Test
    public void test() {
        String date = MessageFormat.format("{0, date, yy-MM-dd} {0, time, kk:mm:ss.SSSS} ",
                new Object[]{new Date().getTime()});
        System.out.println(date);

        int a=1<<31;
        a=-1>>>1;
        System.out.println(a);
        //System.out.println(Integer.MAX_VALUE);

        System.out.println(Integer.toBinaryString(-10));
    }
}
