package com.yu.voiceworks.repository;

import com.yu.voiceworks.entity.po.Rank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RankRepository extends JpaRepository<Rank,Integer> {
    void deleteByWorkId(String workId);

    List<Rank> findByWorkId(String rjCode);
}
