package com.zhunzhong.demo.number;

import java.math.BigDecimal;

/**
 * @author: zhunzhong
 * @date: 2022-09-15 15:29
 * @description: todo
 */
public class BigDecimalEfficiency {

    public static int REPEAT_TIMES = 1000000;

    public static double computeByBigDecimal(double a, double b) {
        BigDecimal result = BigDecimal.valueOf(0);
        BigDecimal decimalA = BigDecimal.valueOf(a);
        BigDecimal decimalB = BigDecimal.valueOf(b);
        for (int i = 0; i < REPEAT_TIMES; i++) {
            result = result.add(decimalA.multiply(decimalB));
        }
        return result.doubleValue();
    }

    public static double computeByDouble(double a, double b) {
        double result = 0;
        for (int i = 0; i < REPEAT_TIMES; i++) {
            result += a * b;
        }
        return result;
    }

    public static void main(String[] args) {
        long test = System.nanoTime();
        long start1 = System.nanoTime();
        double result1 = computeByBigDecimal(0.120000000034, 11.22);
        long end1 = System.nanoTime();
        long start2 = System.nanoTime();
        double result2 = computeByDouble(0.120000000034, 11.22);
        long end2 = System.nanoTime();

        long timeUsed1 = (end1 - start1);
        long timeUsed2 = (end2 - start2);
        System.out.println("result by BigDecimal:" + result1);
        System.out.println("time used:" + timeUsed1);
        System.out.println("result by Double:" + result2);
        System.out.println("time used:" + timeUsed2);

        System.out.println("timeUsed1/timeUsed2=" + timeUsed1 / timeUsed2);
    }
}
