package com.zhunzhong;

import org.junit.Test;

/**
 * @author: zhunzhong
 * @date: 2022-08-29 22:58
 * @description: todo
 */
public class TestStatic {

    @Test
    public void test1() {
        ConnectionStateChangeCallBack.DisconnectCallBack disconnectCallBack = new ConnectionStateChangeCallBack.DisconnectCallBack();
        System.out.println(disconnectCallBack.toString());
        disconnectCallBack.invoke();
        disconnectCallBack = new ConnectionStateChangeCallBack.DisconnectCallBack();
        System.out.println(disconnectCallBack.toString());
        disconnectCallBack.invoke();

    }
}
