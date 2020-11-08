package com.yu.voiceworks.repository;

import com.yu.voiceworks.entity.po.Rank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankRepository extends JpaRepository<Rank,Integer> {
    void deleteByWorkId(String workId);
}
