package com.zhunzhong.demo.pojo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class User {

    private String userName;

    private String passWord;

    private int sex;

    private List<String> data;
}
