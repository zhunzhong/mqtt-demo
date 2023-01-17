package com.zhunzhong;

import org.junit.Test;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;


public class Test1 {


    @Test
    public void testFileChannel() throws FileNotFoundException {
        for(int k=0;k<100;k++) {
            String filename = "test.txt";
            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            FileChannel fileChannel = fileOutputStream.getChannel();
            String data = "中国是世界上历史最悠久的国家之一，有着光辉灿烂的文化和光荣的革命传统 [3]  ，世界遗产数量全球领先 [9]  。1949年新中国成立后，进入社会主义革命和建设时期，1956年实现向社会主义过渡，此后社会主义建设在探索中曲折发展 [10]  。“文化大革命”结束后实行改革开放，沿着中国特色社会主义道路，集中力量进行社会主义现代化建设 [3]  。经过长期努力，中国特色社会主义进入了新时代。\r\n";
            long start = System.currentTimeMillis();
            for (int i = 0; i < 10000; i++) {
                try {
                    fileChannel.write(ByteBuffer.wrap(data.getBytes(StandardCharsets.UTF_8)));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            long end = System.currentTimeMillis();
            System.out.println(String.format("time:%d", (end - start)));
        }

    }

    @Test
    public void test() throws FileNotFoundException {
        for(int k=0;k<100;k++) {
            String filename = "test2.txt";
            String data = "中国是世界上历史最悠久的国家之一，有着光辉灿烂的文化和光荣的革命传统 [3]  ，世界遗产数量全球领先 [9]  。1949年新中国成立后，进入社会主义革命和建设时期，1956年实现向社会主义过渡，此后社会主义建设在探索中曲折发展 [10]  。“文化大革命”结束后实行改革开放，沿着中国特色社会主义道路，集中力量进行社会主义现代化建设 [3]  。经过长期努力，中国特色社会主义进入了新时代。\r\n";
            long start = System.currentTimeMillis();
            BufferedOutputStream bufferedOutput = new BufferedOutputStream(new FileOutputStream(filename));
            for (int i = 0; i < 10000; i++) {
                try {
                    bufferedOutput.write(data.getBytes(StandardCharsets.UTF_8));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                bufferedOutput.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            long end = System.currentTimeMillis();
            System.out.println(String.format("time:%d", (end - start)));
        }

    }
}
