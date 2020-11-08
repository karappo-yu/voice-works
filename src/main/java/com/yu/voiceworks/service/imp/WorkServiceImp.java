package com.yu.voiceworks.service.imp;

import com.yu.voiceworks.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkServiceImp {

    private WorkDirRepository workDirRepository;

    private WorkInfoRepository workInfoRepository;

    private WorkCvRepository workCvRepository;

    private WorkDynRepository workDynRepository;

    private WorkTagRepository workTagRepository;

    private RateCountDetailRepository rateCountDetailRepository;

    private RankRepository rankRepository;

    @Transactional
    public void deleteRecordById(String id) {
        //删除WorkDir
        if (workDirRepository.existsById(id)) {
            workDirRepository.deleteById(id);
        }
        //workInfo
        if (workInfoRepository.existsById(id)){
            workInfoRepository.deleteById(id);
        }
        //workDyn
        if (workDynRepository.existsById(id)){
            workDynRepository.deleteById(id);
        }
        //workTag
        workCvRepository.deleteByWorkId(id);
        workTagRepository.deleteByWorkId(id);

        rateCountDetailRepository.deleteByWorkId(id);
        rankRepository.deleteByWorkId(id);
    }

    @Autowired
    public WorkServiceImp(WorkDirRepository workDirRepository,
                          WorkInfoRepository workInfoRepository,
                          WorkCvRepository workCvRepository,
                          WorkDynRepository workDynRepository,
                          WorkTagRepository workTagRepository,
                          RateCountDetailRepository rateCountDetailRepository,
                          RankRepository rankRepository) {
        this.workDirRepository = workDirRepository;
        this.workInfoRepository = workInfoRepository;
        this.workCvRepository = workCvRepository;
        this.workDynRepository = workDynRepository;
        this.workTagRepository = workTagRepository;
        this.rateCountDetailRepository = rateCountDetailRepository;
        this.rankRepository = rankRepository;
    }
}
