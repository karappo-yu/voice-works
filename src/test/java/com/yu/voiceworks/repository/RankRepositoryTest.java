package com.yu.voiceworks.repository;

import com.yu.voiceworks.entity.po.Rank;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RankRepositoryTest {

    @Autowired
    RankRepository rankRepository;

    @Test
    public void testSave(){
        Rank rank = new Rank();
        rank.setWorkId("123456");
        rank.setRank(3);
        rank.setRankDate("fsdfs");
        rank.setTerm("day");
        rank.setCategory("voice");
        rankRepository.save(rank);
    }
}