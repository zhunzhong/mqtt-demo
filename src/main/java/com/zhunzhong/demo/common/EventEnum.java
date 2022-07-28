package com.zhunzhong.demo.common;


import lombok.Getter;

@Getter
public enum EventEnum {

    EVENT1("event1"),

    EVENT2("events");


    private String value;

    EventEnum(String value) {
        this.value = value;
    }
}
