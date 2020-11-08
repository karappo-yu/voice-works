package com.yu.voiceworks.entity.vo;


import lombok.Data;

@Data
public class ViewResult<T> {
    private int code;

    private String msg;

    private T data;
}
