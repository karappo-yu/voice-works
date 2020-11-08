package com.yu.voiceworks.repository;

import com.yu.voiceworks.entity.po.RateCountDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateCountDetailRepository extends JpaRepository<RateCountDetail,Integer> {
    void deleteByWorkId(String workId);
}
