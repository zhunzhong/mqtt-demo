package com.zhunzhong.demo.statemachine1;

public enum TestStates {
    UNPAID,                 // 待支付
    WAITING_FOR_RECEIVE,    // 待收货
    DONE                    // 结束
}