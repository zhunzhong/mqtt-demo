package com.zhunzhong;

import java.io.File;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ByteUtil;
import cn.hutool.core.util.HexUtil;
import com.zhunzhong.demo.pojo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author zhunzhong
 * @datetime 2022/7/5 15:52
 * @description
 */
@Slf4j
public class MiscTest {

    @Test
    public void testLombok() {
        User user = new User();
        user.setUserName("姓名");
        user.setPassWord("密码");
        user.setData(Arrays.asList("元素1", "元素2"));
        System.out.println(user.toString());
    }

    @Test
    public void test() {
        String date = MessageFormat.format("{0, date, yy-MM-dd} {0, time, kk:mm:ss.SSSS} ",
                new Object[]{new Date().getTime()});
        System.out.println(date);

        int a = 1 << 31;
        a = -1 >>> 1;
        System.out.println(a);
        //System.out.println(Integer.MAX_VALUE);

        System.out.println(Integer.toBinaryString(-10));
    }

    @Test
    public void testFile() {
        File f = new File("aa\\bb");
        System.out.println(f.getPath());
    }

    @Test
    public void test2() {
        String str1 = "12345";
        StringBuilder sb = new StringBuilder();
        sb.append(str1);
        sb.replace(2, 3, "a");
        System.out.println(sb.toString());
    }

    private final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(10);

    @Test
    public void testQueue() {
        log.info("in");
        log.info("out");
        for (int i = 0; i < 100; i++) {
            log.info("offer: {}", queue.offer(String.valueOf(i)));
        }
        while (!queue.isEmpty()) {
            try {
                log.info(queue.take());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    public void testByte() {
        String data = "-1中文0中文0中文0中文0中文0中文0中文0中文0中文0中文0中文0中文0中文0";
        byte[] byteData = data.getBytes();
        printHexStringEx(byteData, 0, byteData.length);
        log.info(HexUtil.format(HexUtil.encodeHexStr(byteData)));
    }

    private void printHexStringEx(byte[] b, int offset, int length) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < length; i++) {
            StringBuilder hex = new StringBuilder(Integer.toHexString(b[i + offset] & 0xFF));
            if (hex.length() == 1) {
                hex = hex.insert(0, "0");
            }
            output = output.append(hex.insert(0, " "));
            if (((i + 1) % 16 == 0)) {
                output = output.append("\n");
            }
        }
        log.info("节目数据16进制数组：" + output);
    }
}
