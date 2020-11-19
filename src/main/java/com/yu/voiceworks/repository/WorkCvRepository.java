package com.yu.voiceworks.repository;

import com.yu.voiceworks.entity.po.WorkCv;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkCvRepository extends JpaRepository<WorkCv,String> {

    void deleteByWorkId(String workId);

    List<WorkCv> findByWorkId(String rjCode);
}
