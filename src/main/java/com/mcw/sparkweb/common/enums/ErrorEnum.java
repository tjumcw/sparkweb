package com.mcw.sparkweb.common.enums;

import lombok.Getter;

/**
 * @author miaochangwei1
 * @Package : com.mcw.sparkweb.common.enums
 * @Description : TODO
 * @Create on : 2024/1/15 16:07
 **/

@Getter
public enum ErrorEnum {

    SERVER_ERROR(500, "服务器异常");

    private int code;

    private String message;

    ErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
