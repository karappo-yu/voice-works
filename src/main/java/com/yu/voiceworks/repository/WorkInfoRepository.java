package com.yu.voiceworks.repository;

import com.yu.voiceworks.entity.po.WorkInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WorkInfoRepository extends JpaRepository<WorkInfo,String>,
        JpaSpecificationExecutor<WorkInfo>
{
}
