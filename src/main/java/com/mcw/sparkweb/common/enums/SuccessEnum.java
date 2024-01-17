package com.mcw.sparkweb.common.enums;

import lombok.Getter;

/**
 * @author miaochangwei1
 * @Package : com.mcw.sparkweb.common.enums
 * @Description : TODO
 * @Create on : 2024/1/15 15:46
 **/

@Getter
public enum SuccessEnum {

    SUCCESS(200, "成功");

    private int code;

    private String message;

    SuccessEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
