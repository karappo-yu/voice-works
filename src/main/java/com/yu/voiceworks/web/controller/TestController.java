package com.yu.voiceworks.web.controller;

import com.yu.voiceworks.crawler.DsLite;
import com.yu.voiceworks.entity.VoiceWork;
import com.yu.voiceworks.entity.po.WorkDir;
import com.yu.voiceworks.filesystem.FileScanner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("list")
    public List<VoiceWork> list(){
        List<VoiceWork> voiceWorks = new LinkedList<>();
        Set<WorkDir> workDirs = FileScanner.scanBasePath();
        for (WorkDir workDir : workDirs) {
            VoiceWork voiceWork = DsLite.getStaticMetaData(workDir.getWorkId());
            voiceWorks.add(voiceWork);
        }
        return voiceWorks;
    }
}
