package com.yu.voiceworks.repository;

import com.yu.voiceworks.entity.po.WorkDyn;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WorkDynRepositoryTest {
    @Autowired
    private WorkDynRepository workDynRepository;

    @Test
    public void testSave(){
        WorkDyn workDyn = new WorkDyn();
        workDyn.setWorkId("123456");
        workDyn.setDlCount(6);
        workDyn.setPrice(121);
        workDyn.setRateAverage(5);
        workDyn.setRateAverage2Dp(1.6);
        workDyn.setRateCount(3);
        workDyn.setReviewCount(20);
        workDynRepository.save(workDyn);
    }
}