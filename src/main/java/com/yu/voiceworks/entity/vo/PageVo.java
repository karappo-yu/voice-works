package com.yu.voiceworks.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class PageVo<T> {

    private int page;

    private int pageSize;

    private long total;

    private List<T> content;
}

