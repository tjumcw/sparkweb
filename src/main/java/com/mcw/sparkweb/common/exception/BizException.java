package com.mcw.sparkweb.common.exception;

import com.mcw.sparkweb.common.enums.ErrorEnum;

/**
 * @author miaochangwei1
 * @Package : com.mcw.sparkweb.common.exception
 * @Description : TODO
 * @Create on : 2024/1/15 16:02
 **/

public class BizException extends RuntimeException {

    private Integer code;

    public BizException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(ErrorEnum errorEnum) {
        super(errorEnum.getMessage());
        this.code = errorEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }
}
