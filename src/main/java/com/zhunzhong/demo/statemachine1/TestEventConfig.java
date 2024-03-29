package com.zhunzhong.demo.statemachine1;


import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

@Slf4j
@WithStateMachine(id = TestStateMachineBuilder.MACHINE_ID)
public class TestEventConfig {


    @OnTransition(target = "UNPAID")
    public void create() {
        log.info("订单建立，待支付");
    }

    @OnTransition(target = "DONE")
    public void done() {
        log.info("done***************");
    }

    @OnTransition(source = "UNPAID", target = "WAITING_FOR_RECEIVE")
    public void pay() {
        log.info("用户完成支付，待收货");
    }

    @OnTransition(source = "WAITING_FOR_RECEIVE", target = "DONE")
    public void receive() {
        log.info("用户已收货，订单完成");
    }


}
