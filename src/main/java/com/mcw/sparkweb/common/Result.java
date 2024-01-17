package com.mcw.sparkweb.common;

import com.alibaba.fastjson.JSON;
import com.mcw.sparkweb.common.enums.ErrorEnum;
import com.mcw.sparkweb.common.enums.SuccessEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author miaochangwei1
 * @Package : com.mcw.sparkweb.common
 * @Description : TODO
 * @Create on : 2024/1/15 15:47
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T>{

    private Integer code;

    private String message;

    private T data;

    private boolean success;

    public static Result buildSuccess() {
        return new Result(SuccessEnum.SUCCESS.getCode(), SuccessEnum.SUCCESS.getMessage(), null, true);
    }

    public static <T> Result<T> buildSuccess(T data) {
        return new Result(SuccessEnum.SUCCESS.getCode(), SuccessEnum.SUCCESS.getMessage(), data, true);
    }

    public static Result buildError() {
        return new Result(ErrorEnum.SERVER_ERROR.getCode(), ErrorEnum.SERVER_ERROR.getMessage(), null, false);
    }

    public static Result buildError(ErrorEnum errorEnum) {
        return new Result(errorEnum.getCode(), errorEnum.getMessage(), null, false);
    }

    public static Result buildError(String errorMsg) {
        return new Result(null, errorMsg, null, false);
    }

    public static Result buildError(Integer code, String errorMsg) {
        return new Result(code, errorMsg, null, false);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
