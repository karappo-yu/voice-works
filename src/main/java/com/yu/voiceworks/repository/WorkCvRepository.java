package com.yu.voiceworks.repository;

import com.yu.voiceworks.entity.po.WorkCv;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkCvRepository extends JpaRepository<WorkCv,String> {

    void deleteByWorkId(String workId);
}
