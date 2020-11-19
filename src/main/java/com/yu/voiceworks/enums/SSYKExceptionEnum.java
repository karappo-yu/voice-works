package com.yu.voiceworks.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SSYKExceptionEnum {
    NO_SUCH_WORK(404,"没有找到该作品"),
    DIR_IS_EMPTY(204,"该作品文件夹为空" );

    private final Integer code;

    private final String msg;

    SSYKExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
