package com.yu.voiceworks.entity.vo;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ViewResult<T> {
    private int code;

    private String msg;

    private T data;
}
