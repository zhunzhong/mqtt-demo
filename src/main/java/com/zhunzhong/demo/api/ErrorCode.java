package com.zhunzhong.demo.api;

import lombok.Getter;

/**
 * @author: zhunzhong
 * @date: 2022-08-26 17:00
 * @description: 接口返回结果状态码统一定义.
 * 各设备服务自己的异常码范围定义
 * plc:10xxx
 * carmachine:11xxx
 * rbc:12xxx
 * dahua:13xxx
 * dcc:14xxx
 */
@Getter
public enum ErrorCode {

    /**
     *
     */
    SUCCESS(200, "成功"),

    ERROR(500,"服务异常"),

    NOT_IMPLEMENTED(501,"本功能暂未实现"),

    PARAMETER_VERIFICATION_FAILED(400,"参数校验失败"),
    ;

    private final Integer code;

    private final String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
