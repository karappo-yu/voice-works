package com.yu.voiceworks.entity.vo;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Data
public class PageForm {

    private int page;

    private int pageSize;

    private String order;

    private String sort;

    /**
     * 转换为pageRequest对象
     * @return pageRequest
     */
    public PageRequest toPageable(){
        PageRequest pageRequest = null;
        int page = this.page<1?0:this.page-1;
        int pageSize = this.pageSize<1?10:this.pageSize;
        if (StringUtils.isNotBlank(this.sort)){
            Sort.Direction d;
            if (StringUtils.isBlank(this.order)){
                d = Sort.Direction.DESC;
            }else {
                d = Sort.Direction.valueOf(this.order.toUpperCase());
            }
            Sort sort = Sort.by(d,this.sort);
            pageRequest = PageRequest.of(page, pageSize, sort);
        }else {
            pageRequest= PageRequest.of(page,pageSize);
        }
        return pageRequest;
    }
}
