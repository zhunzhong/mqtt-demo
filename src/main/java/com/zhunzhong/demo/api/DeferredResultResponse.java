package com.zhunzhong.demo.api;

import lombok.Data;
import lombok.Getter;

/**
 * @author: zhunzhong
 * @date: 2023-01-29 11:48
 * @description: todo
 */

@Data
public class DeferredResultResponse {
    private Integer code;
    private String msg;

    public enum Msg {
        TIMEOUT("超时"),
        FAILED("失败"),
        SUCCESS("成功");

        @Getter
        private String desc;

        Msg(String desc) {
            this.desc = desc;
        }
    }
}
