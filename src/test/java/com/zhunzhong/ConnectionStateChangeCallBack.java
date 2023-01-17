package com.zhunzhong;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: zhunzhong
 * @date: 2022-08-28 20:46
 * @description: 现场大华设备登陆注册回调函数.
 */
@Slf4j
public class ConnectionStateChangeCallBack {

    private String userName;

    public DisconnectCallBack instance() {
        return new DisconnectCallBack(userName);
    }

    /**
     * 设备断线回调
     */
    public static class DisconnectCallBack {
        String aa;

        public DisconnectCallBack(){

        }

        private DisconnectCallBack(String aa) {
            this.aa = aa;
        }

        private int a = 0;

        public void invoke() {
            a++;
            System.out.println(a);
        }

    }

    /**
     * 设备重连回调
     */
    public static class ReconnectCallBack {

        public void invoke() {

        }

    }
}
