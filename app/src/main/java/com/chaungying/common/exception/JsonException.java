package com.chaungying.common.exception;

/**
 * @author 王晓赛 or 2016/8/3
 *
 * json解析异常类
 */
public class JsonException extends Exception {

    public JsonException(){

    }

    public JsonException(String detailMessage) {
        super(detailMessage);
    }
}
