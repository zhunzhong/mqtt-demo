package com.zhunzhong.demo.statemachine;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;

import java.util.EnumSet;

@Slf4j
@Configuration
@EnableStateMachine
public class TestStateMachineBuilder {

    public static final String MACHINE_ID = "testStateMachine";

    @Bean
    public StateMachine<TestStates, TestEvents> build(BeanFactory beanFactory) throws Exception {
        StateMachineBuilder.Builder<TestStates, TestEvents> builder = StateMachineBuilder.builder();

        builder.configureConfiguration()
                .withConfiguration()
                .machineId(MACHINE_ID)
                .beanFactory(beanFactory)
                .autoStartup(true);

        builder.configureStates()
                .withStates()
                .initial(TestStates.UNPAID)
                .states(EnumSet.allOf(TestStates.class));

        builder.configureTransitions()
                .withExternal()
                .source(TestStates.UNPAID).target(TestStates.WAITING_FOR_RECEIVE)
                .event(TestEvents.PAY)
                .and()
                .withExternal()
                .source(TestStates.WAITING_FOR_RECEIVE).target(TestStates.DONE)
                .event(TestEvents.RECEIVE);
        return builder.build();

    }
}
