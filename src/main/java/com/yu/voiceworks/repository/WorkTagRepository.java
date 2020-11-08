package com.yu.voiceworks.repository;

import com.yu.voiceworks.entity.po.WorkTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkTagRepository extends JpaRepository<WorkTag,String> {

    void deleteByWorkId(String workId);
}
