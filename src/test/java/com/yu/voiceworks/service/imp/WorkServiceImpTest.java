package com.yu.voiceworks.service.imp;

import com.yu.voiceworks.entity.vo.PageForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkServiceImpTest {
    @Autowired
    private WorkServiceImp workServiceImp;

    @Test
    public void getWorksByCondition() {
        PageForm pageForm = new PageForm();
        pageForm.setPage(1);
        pageForm.setPageSize(2);
        pageForm.setOrder(Sort.Direction.DESC.name());
        pageForm.setSort("workRelease");
        workServiceImp.getWorksByCondition("", pageForm);
    }
}