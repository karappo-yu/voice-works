package com.yu.voiceworks.utils;

import com.yu.voiceworks.entity.vo.ViewResult;
import org.springframework.http.HttpStatus;

public class ResultVoUtil {

  public static <T> ViewResult<T> getResultVo(int code,String msg,T data){
      return ViewResult.<T>builder()
              .code(code)
              .msg(msg)
              .data(data)
              .build();
  }
  public static <T> ViewResult<T> getResultVo(int code,String msg){
        return ViewResult.<T>builder()
                .code(code)
                .msg(msg)
                .data(null)
                .build();
    }

  public static <T> ViewResult<T> ok(T data){
      return ViewResult.<T>builder()
              .code(HttpStatus.OK.value())
              .msg(HttpStatus.OK.name())
              .data(data)
              .build();
    }
}
