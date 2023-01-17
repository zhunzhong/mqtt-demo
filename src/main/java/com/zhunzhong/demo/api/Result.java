package com.zhunzhong.demo.api;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author: zhunzhong
 * @date: 2022-08-26 16:57
 * @description: 接口统一返回对象定义
 */
@Getter
@Setter
public class Result<T> implements Serializable {


    /**
     * 返回状态码，默认success.
     */
    private Integer code = ErrorCode.SUCCESS.getCode();

    @JSONField(serialize = false)
    private ErrorCode errorCode=ErrorCode.NOT_IMPLEMENTED;

    /**
     * 描述.
     */
    private String message;


    /**
     * 数据.
     */
    private T data;

    public Result() {

    }

    public Result(T data) {
        this.data = data;
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 基于ResultCode传参，直接获取错误码描述信息.
     *
     * @param errorCode 错误码
     * @param data      错误消息类型
     */
    public Result(ErrorCode errorCode, T data) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.data = data;
    }

    public static <T> Result<T> success() {
        return new Result<>(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return 警告消息
     */
    public static <T> Result<T> error(int code, String msg) {
        return new Result<>(code, msg, null);
    }

    /**
     * 返回错误消息
     */
    public static Result<Void> error() {
        return Result.error(ErrorCode.ERROR.getMessage());
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static <T> Result<T> error(String msg) {
        return new Result<>(ErrorCode.ERROR.getCode(), msg);
    }


    public static <T> Result<T> error(ErrorCode errorCode) {
        return Result.error(errorCode.getCode(), errorCode.getMessage());
    }

    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static <T> Result<T> error(String msg, T data) {
        return new Result<>(ErrorCode.ERROR.getCode(), msg, data);
    }

    public static <T> Result<T> error(Integer code, String msg, T data) {
        return new Result<>(code, msg, data);
    }


    /**
     * 自定义参数错误信息
     */
    public static <T> Result<T> verificationFailed(String msg) {
        return new Result<>(ErrorCode.PARAMETER_VERIFICATION_FAILED.getCode(), msg, null);
    }

    /**
     * 通用参数返回类型
     **/
    public static <T> Result<T> verificationFailed(String msg, T data) {
        return new Result<>(ErrorCode.PARAMETER_VERIFICATION_FAILED.getCode(), msg, data);
    }

}
