package com.zhunzhong;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.compress.Gzip;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.extra.pinyin.PinyinUtil;
import com.alibaba.fastjson.JSON;
import com.zhunzhong.demo.pojo.entity.User;
import org.apache.commons.compress.compressors.gzip.GzipUtils;
import org.junit.Test;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author: zhunzhong
 * @date: 2022-08-30 10:32
 * @description: todo
 */
public class HutoolTest {

    @Test
    public void testRandom() {
        for (int i = 0; i < 100; i++) {
            System.out.println(RandomUtil.randomDouble(10));
        }
    }

    @Test
    public void testCleanBlank() {
        String str = "中文 测试 天下 文字 ";
        String str1 = null;
        System.out.println(StrUtil.cleanBlank(str1));
    }

    @Test
    public void testPinyin() {
        String aa = PinyinUtil.getPinyin("你好", "");
        System.out.println(aa);
        String dd = DateUtil.format(new Date(), "yyyyMMddHHmmss");
        System.out.println(dd);
    }


    @Test
    public void testStr() {
        String ss = CharSequenceUtil.format("测试:{} 1:{}", "aa", "bb");
        System.out.println(ss);
    }

    @Test
    public void testGzipSave() {
        byte[] data = FileUtil.readBytes(new File("D:\\codes\\open-source\\zhunzhong\\mqtt-demo\\car1.txt"));
        byte[] zipData = ZipUtil.gzip(data);
        String base64Data = Base64Encoder.encode(zipData);
        FileUtil.writeBytes(base64Data.getBytes(), new File("D:\\codes\\open-source\\zhunzhong\\mqtt-demo\\car-base64.txt"));
    }

    @Test
    public void testGzipRead() {
        byte[] data = FileUtil.readBytes(new File("D:\\codes\\open-source\\zhunzhong\\mqtt-demo\\car-base64.txt"));
        data = Base64Decoder.decode(data);
        String base64Data = new String(ZipUtil.unGzip(data), StandardCharsets.UTF_8);
        FileUtil.writeBytes(base64Data.getBytes(), new File("D:\\codes\\open-source\\zhunzhong\\mqtt-demo\\car-base64-in.txt"));

    }

    @Test
    public void objectCompare() {
        User user1 = new User();
        user1.setUserName("name1");

        User user2 = new User();
        user1.setUserName("name2");

        if (user1.equals(user2)) {
            System.out.println("equal");
        } else {
            System.out.println("not equal");
        }
    }

    @Test
    public void testConvert() {

        User user = new User();
        user.setUserName("zz");
        user.setPassWord("tt");
        List<String> list = new ArrayList<>();
        list.add("zz");
        list.add("tt");
        user.setData(list);

        String a = "sss";
        Object ao = JSON.toJSONString(a);
        String a1 = (String) ao;
        System.out.println(a1);

        for (int i = 0; i < 10; i++) {
            long startTime = System.currentTimeMillis();
            String data = JSON.toJSONString(user);
            Object oo = JSON.parseObject(data);

            User newUser = (User) oo;
            System.out.println(newUser.getData().get(0));
            System.out.println("耗时：" + (System.currentTimeMillis() - startTime) + "ms");
        }


    }


}
