package com.yu.voiceworks.exception;

import com.yu.voiceworks.enums.SSYKExceptionEnum;
import lombok.Data;

@Data
public class SSYKException extends RuntimeException{

    private Integer code;

    public SSYKException(Integer code){
        this.code = code;
    }

    public SSYKException(SSYKExceptionEnum exceptionEnum){
        super(exceptionEnum.getMsg());
        this.code = exceptionEnum.getCode();
    }
}
