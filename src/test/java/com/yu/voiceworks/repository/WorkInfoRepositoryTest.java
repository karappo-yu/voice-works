package com.yu.voiceworks.repository;

import com.yu.voiceworks.crawler.DsLite;
import com.yu.voiceworks.entity.VoiceWork;
import com.yu.voiceworks.entity.po.WorkDir;
import com.yu.voiceworks.filesystem.FileScanner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WorkInfoRepositoryTest {
    @Autowired
    WorkInfoRepository workInfoRepository;

    @Test
    public void test() {
        Set<WorkDir> workDirs = FileScanner.scanBasePath();
        for (WorkDir workDir : workDirs) {
            VoiceWork voiceWork = DsLite.getStaticMetaData(workDir.getWorkId());
            workInfoRepository.save(voiceWork.toWorkInfo());
        }
    }
}